package com.fastspark.fastspark.messaging;

import com.fastspark.fastspark.model.Client;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Gimhani Uthpala on 11/8/2017.
 */
public class HeartBeatHandler implements Runnable {

    private Client clientFrame;

    public HeartBeatHandler(Client clientFrame) {
        this.clientFrame = clientFrame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sendHeartBeat();
                Thread.sleep(100);
                this.clientFrame.updateRountingTable();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(HeartBeatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //send Heartbeat to other nodes
    public void sendHeartBeat() throws IOException {
        String message = "HEARTBEAT " + this.clientFrame.getIp() + " " + this.clientFrame.getPort();
        message = String.format("%04d", message.length() + 5) + " " + message;
        this.clientFrame.multicast(message, this.clientFrame.getMyNodeList());
        Set<Integer> keySet = this.clientFrame.getBucketTable().keySet();
        for (int key : keySet) {
            this.clientFrame.unicast(message, this.clientFrame.getBucketTable().get(key));
        }
    }
}
