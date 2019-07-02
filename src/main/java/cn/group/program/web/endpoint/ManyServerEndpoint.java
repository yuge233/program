package cn.group.program.web.endpoint;

import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/many/{room}/{username}",encoders = {MessageEncoder.class})
public class ManyServerEndpoint {

    private static Map<String, Map<String,Session>> rooms=new ConcurrentHashMap<>(20);

    @OnOpen
    public void openSession(@PathParam("room") String room,@PathParam("username") String username, Session session){
        if (!rooms.containsKey(room)){
            rooms.put(room,new ConcurrentHashMap<>(20));
        }
        Map<String,Session> sessions=rooms.get(room);
        sessions.put(session.getId(),session);
        Message message=new Message("欢迎用户["+username+"]",1);

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
    public void message(@PathParam("room") String room, String message){
        sendTextAll(room,new Message(message));
    }

    @OnClose
    public void close(@PathParam("room") String room, @PathParam("username") String username, Session session){
        Map<String,Session> sessions=rooms.get(room);
        sessions.remove(session.getId());
        if (sessions.isEmpty()){
            rooms.remove(room);
        }
        Message message=new Message("["+username+"]离开了聊天室!",1);

        sendTextAll(room,message);
    }
}