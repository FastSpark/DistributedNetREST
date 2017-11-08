package com.fastspark.fastspark.controller;

import com.fastspark.fastspark.messaging.MessagingInterface;
import com.fastspark.fastspark.model.Client;
import com.fastspark.fastspark.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Buddhi on 11/1/2017.
 */

@RestController
@EnableAutoConfiguration
public class RequestController {


    Client client;
    @Autowired
    Environment environment;

    @RequestMapping(value = "/client/{message}", method = RequestMethod.GET)
    public String getRequest(@PathParam("message") String message) {
        MessagingInterface messagingInterface = new MessagingInterface();
        try {
            return client.handleMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST,headers = "content-type=application/json")
    public Message doTask(@RequestBody Message message)
    {
        try {
            String reply = client.handleMessage(message.getMessage());

            return new Message(InetAddress.getLocalHost().getHostAddress(),Integer.valueOf(environment.getProperty("SERVER_PORT")),reply);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/client/add/{username}", method = RequestMethod.GET)
    public void addNode(@PathParam("username") String username) {
        //add new node
    }

    @RequestMapping(value = "/client/add/{username}", method = RequestMethod.POST)
    public void sendMessage(String message) {
        //add new node
    }

}
