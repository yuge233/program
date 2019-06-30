package cn.group.program.web.endpoint;

import java.io.Serializable;

class Message implements Serializable {
    public String message;

    public Message(){
    }

    public Message(String message) {
        this.message = message;
    }
}
