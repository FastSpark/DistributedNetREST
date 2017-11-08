package com.fastspark.fastspark.model;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Buddhi on 11/1/2017.
 */

public class Message implements Serializable{
    private String fromIp;
    private int fromPort;
    private String message;

    public Message(String fromIp, int fromPort, String message) {
        this.fromIp = fromIp;
        this.fromPort = fromPort;
        this.message = message;

    }

    public Message(){}

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFromPort() {
        return fromPort;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }
}
