package com.fastspark.fastspark.model;

/**
 * Created by Buddhi on 11/1/2017.
 */
public class Message {
    private String fromIp;
    private String fromPort;
    private String messageType;
    private String messageLength;

    public Message(String fromIp, String fromPort, String messageType, String messageLength) {
        this.fromIp = fromIp;
        this.fromPort = fromPort;
        this.messageType = messageType;
        this.messageLength = messageLength;
    }

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(String messageLength) {
        this.messageLength = messageLength;
    }
}
