package cn.group.program.web.endpoint;

import java.io.Serializable;
import java.util.Vector;

class Message implements Serializable {
    private String message;
    private int mode;
    //普通消息为0,修改房间列表为1,修改成绩为2,清空成绩为3

    private Vector<String> users;   //用于mode:1

    private String username;//用于mode:2
    private Integer useTime;//用于mode:2

    public Message(String message) {
        this.message = message;
        mode=0;
    }

    public Message(String message, int mode) {
        this.message = message;
        this.mode = mode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Vector<String> getUsers() {
        return users;
    }

    public void setUsers(Vector<String> users) {
        this.users = users;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
