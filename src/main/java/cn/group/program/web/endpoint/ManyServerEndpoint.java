package cn.group.program.web.endpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/many/{room}/{username}",encoders = {MessageEncoder.class})
public class ManyServerEndpoint {

    private static Map<String, Map<String,Session>> rooms=new ConcurrentHashMap<>(20);
    private static Map<String, Vector<String>> room_users=new ConcurrentHashMap<>(20);

    @OnOpen
    public void openSession(@PathParam("room") String room,@PathParam("username") String username, Session session){
        if (!rooms.containsKey(room)){
            rooms.put(room,new ConcurrentHashMap<>(20));
            room_users.put(room,new Vector<>(20));
        }
        Map<String,Session> sessions=rooms.get(room);
        Vector<String> user=room_users.get(room);
        sessions.put(session.getId(),session);
        user.add(username);
        Message message=new Message("欢迎用户["+username+"]",1);
        message.setUsers(user);
        sendTextAll(room,message);
    }

    public void sendText(Session session, Message message){
        RemoteEndpoint.Basic basic=session.getBasicRemote();
        try {
//            basic.sendText(message,false);
            basic.sendObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTextAll(String room,Message message){
        Map<String,Session> sessions=rooms.get(room);
        sessions.forEach((session_id,session)->{
            if (session!=null&&session.isOpen())
                sendText(session,message);
        });
    }

    @OnMessage
    public void message(@PathParam("room") String room,@PathParam("username") String username, String message){
        Map<String,Object> map= (Map) JSON.parse(message);
        Integer mode=(Integer) map.get("mode");
        Message message_obj=new Message((String) map.get("message"), mode);
        if (mode==2){
            message_obj.setUseTime((Integer) map.get("useTime"));
            message_obj.setUsername(username);
        }
        sendTextAll(room,message_obj);
    }

    @OnClose
    public void close(@PathParam("room") String room, @PathParam("username") String username, Session session){
        Map<String,Session> sessions=rooms.get(room);
        Vector<String> users=room_users.get(room);
        sessions.remove(session.getId());
        users.remove(username);
        if (sessions.isEmpty()){
            rooms.remove(room);
            room_users.remove(room);
        }
        Message message=new Message("["+username+"]离开了聊天室!",1);
        message.setUsers(users);
        sendTextAll(room,message);
    }
}