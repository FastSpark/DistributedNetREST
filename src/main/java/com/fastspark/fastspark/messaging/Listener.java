package com.fastspark.fastspark.messaging;

import com.fastspark.fastspark.model.Client;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Gimhani Uthpala on 11/8/2017.
 */
public class Listener implements Runnable {

    Client client;

    @Autowired
    MessagingInterface messagingInterface;

    public Listener(Client client) {
        this.client=client;
    }

    @Override
    public void run() {
        try {
            listen(this.client); //To change body of generated methods, choose Tools | Templates.
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listen(Client clientFrame) throws IOException, ClassNotFoundException {
        // Port number to bind server to.
        int portNum = clientFrame.getPort();

        // Socket for server to listen at.
        DatagramSocket datagramSocket = clientFrame.getDatagramSocket();
        System.out.println("Now listening to port: " + portNum);
        byte[] buffer;
        DatagramPacket packet;
        // Simply making Server run continously.
        while (true) {
            buffer = new byte[125536];
            packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Message Recieved : " + message);

            client.handleMessage(message);

        }
    }
}
