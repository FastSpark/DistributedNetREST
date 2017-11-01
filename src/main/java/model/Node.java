package model;

/**
 * Created by Buddhi on 11/1/2017.
 */
public class Node {
    private final String ip;
    private final int port;
    private long timeStamp;

    public Node(String ip, int port, long timeStamp) {
        this.ip = ip;
        this.port = port;
        this.timeStamp = timeStamp;
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
