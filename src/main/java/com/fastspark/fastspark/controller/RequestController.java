package com.fastspark.fastspark.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Buddhi on 11/1/2017.
 */

@RestController
@EnableAutoConfiguration
public class RequestController {
    @RequestMapping("/client")
    public String getRequest() {
        return "hello world";
    }

    public void sendResponse() {
        //send the response
    }
}
