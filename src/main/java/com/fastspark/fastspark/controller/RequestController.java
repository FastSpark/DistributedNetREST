package com.fastspark.fastspark.controller;

import com.fastspark.fastspark.messaging.MessagingInterface;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * Created by Buddhi on 11/1/2017.
 */

@RestController
@EnableAutoConfiguration
public class RequestController {
    @RequestMapping(value = "/client/{message}", method = RequestMethod.GET)
    public String getRequest(@PathParam("message") String message) {
        MessagingInterface messagingInterface = new MessagingInterface();
        return messagingInterface.HandleMessage(message);
    }

    @RequestMapping(value = "/client/search/{filename}", method = RequestMethod.GET)
    public void searchFile(@PathParam("filename") String filename) {
        //search for file
    }

    @RequestMapping(value = "/client/add/{username}", method = RequestMethod.GET)
    public void addNode(@PathParam("username") String username) {
        //add new node
    }

}
