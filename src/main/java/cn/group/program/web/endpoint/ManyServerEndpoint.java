package cn.group.program.web.endpoint;

import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/many/{room}/{username}")
public class ManyServerEndpoint {

    private static Map<String, Map<String,Session>> rooms=new ConcurrentHashMap<>(20);

    @OnOpen
    public void openSession(@PathParam("room") String room,@PathParam("username") String username, Session session){
        if (!rooms.containsKey(room)){
            rooms.put(room,new ConcurrentHashMap<>(20));
        }
        Map<String,Session> sessions=rooms.get(room);
        sessions.put(session.getId(),session);
        sendTextAll(room,"欢迎用户["+username+"]");
    }

    public void sendText(Session session, String message){
        RemoteEndpoint.Basic basic=session.getBasicRemote();
        try {
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTextAll(String room,String message){
        Map<String,Session> sessions=rooms.get(room);
        sessions.forEach((session_id,session)->{
            sendText(session,message);
        });
    }

    @OnMessage
    public void message(@PathParam("room") String room, String message){
        sendTextAll(room,message);
    }

    @OnClose
    public void close(@PathParam("room") String room, @PathParam("username") String username, Session session){
        Map<String,Session> sessions=rooms.get(room);
        sessions.remove(session.getId());
        if (sessions.isEmpty()){
            rooms.remove(room);
        }
        sendTextAll(room,"["+username+"]离开了聊天室!");
    }
}
