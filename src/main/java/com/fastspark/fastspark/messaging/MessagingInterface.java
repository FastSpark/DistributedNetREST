package com.fastspark.fastspark.messaging;

import com.fastspark.fastspark.model.Client;
import com.fastspark.fastspark.model.Node;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Buddhi on 11/1/2017.
 */
@Component
public class MessagingInterface {
    Client client;


    public String handleMessage(String message) throws IOException {
        String[] messagePart = message.split(" ");
        switch (messagePart[1]) {
            case "REGOK":
                //handle  response from bootstrap
                System.out.println(message);
                this.client.handleRegisterResponse(message);
                break;
            case "UNROK": // handle unregister response
                break;
            case "JOINOK": // join response message
                break;
            case "LEAVEOK": // leave response message
                break;
            case "SEROK": // search response message
                break;
            case "HEARTBEATOK": //haddle hearbeat ok
                System.out.println(message);
                this.client.handleHeartBeatResponse(message);
                break;
            case "HEARTBEAT":
                System.out.println(message);
                this.client.sendHeartBeatReply(message);
                break;
            //this.client.
            case "FBM": //multicast message to find a node from a bucket
                System.out.println(message);
                this.client.findNodeFromBucketReply(Integer.parseInt(messagePart[2]),new Node(client.getIp(), client.getPort()));
                break;
            case "FBMOK": //reply to FBM
                this.client.receiveReplyFindNodeFromBucket(message);
                break;

        }

        return message;
    }


}
