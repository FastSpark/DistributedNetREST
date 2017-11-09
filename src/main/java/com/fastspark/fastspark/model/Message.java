package com.fastspark.fastspark.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Buddhi on 11/1/2017.
 */

@XmlRootElement
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

    @JsonProperty
    public String getFromIp() {
        return fromIp;
    }


    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }


    @JsonProperty
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty
    public int getFromPort() {
        return fromPort;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }
}
