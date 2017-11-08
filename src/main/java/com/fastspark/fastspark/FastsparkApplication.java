package com.fastspark.fastspark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FastsparkApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FastsparkApplication.class);
        Map<String, Object> map = new HashMap<>();
        map.put("SERVER_PORT", args[0]);
        application.setDefaultProperties(map);
        application.run(args);

        //interface to enter ip and port

        //using parameters create a DatagramSocket

        //if response REGOK
        //initialize

        //if response NOTOK
        //error message

    }
}
