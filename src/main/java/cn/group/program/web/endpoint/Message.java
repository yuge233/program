package cn.group.program.web.endpoint;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

class Message implements Serializable {
    private String message;
    private int mode;
    //默认为0,改变为1

    private List<String> users;

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

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
