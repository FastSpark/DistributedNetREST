package com.fastspark.fastspark.model;

import java.sql.Timestamp;

/**
 * Created by Buddhi on 11/1/2017.
 */
public class Node {
    private final String ip;
    private final int port;
    private long timeStamp;

    public Node(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.timeStamp = new Timestamp(System.currentTimeMillis()).getTime();
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
