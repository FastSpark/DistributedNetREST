package com.fastspark.fastspark;

import com.fastspark.fastspark.messaging.HeartBeatHandler;
import com.fastspark.fastspark.messaging.Listener;
import com.fastspark.fastspark.model.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class FastsparkApplication {
    private static final int k = 3;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FastsparkApplication.class);

        try {
            System.out.println("Main Thread Started");

            //start of final variables
            final String[] fileList = {
                    "Adventures of Tintin",
                    "Jack and Jill",
                    "Glee",
                    "The Vampire Diarie",
                    "King Arthur",
                    "Windows XP",
                    "Harry Potter",
                    "Kung Fu Panda",
                    "Lady Gaga",
                    "Twilight",
                    "Windows 8",
                    "Mission Impossible",
                    "Turn Up The Music",
                    "Super Mario",
                    "American Pickers",
                    "Microsoft Office 2010",
                    "Happy Feet",
                    "Modern Family",
                    "American Idol",
                    "Hacking for Dummies"
            };
            //end of final variables

            //start of the input

//            String ip = Inet4Address.getLocalHost().getHostAddress();
            String ip = null;
            try {
                ip = Inet4Address.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
//            String ip = "192.168.43.252";
//            int port = 80;

            Scanner scanner = new Scanner(System.in);
            System.out.println("IP Address : " + ip);
            System.out.print("Input Port : ");
            String input = scanner.nextLine();
            int port = Integer.parseInt(input);
            //end of the input


            Map<String, Object> map = new HashMap<>();
            map.put("SERVER_PORT", input);
            application.setDefaultProperties(map);
            application.run(args);

            //start of creating connection details
            String address = ip + ":" + port;
            int myBucketId = address.hashCode();
            myBucketId = myBucketId % k;
            //end of getting connection details

            //start of initializing files (3 to 5)
            Map<String, ArrayList<String>> fileDictionary = new HashMap<>();

            int randomFileCount = new Random().nextInt(3) + 3;
            System.out.println("Initializing node with " + randomFileCount + " files...");
            ArrayList<String> myFileList = new ArrayList<>();

            for (int i = 0; i < randomFileCount; i++) {
                int randomIndex = new Random().nextInt(fileList.length);
                String selectedFile = fileList[randomIndex];
                myFileList.add(selectedFile);

                ArrayList<String> nodesContainingFile = fileDictionary.get(selectedFile);
                if (nodesContainingFile == null) {
                    nodesContainingFile = new ArrayList<>();
                }
                nodesContainingFile.add(address);
                fileDictionary.put(selectedFile, nodesContainingFile);
            }
            //end of initializing files

            DatagramSocket datagramSocket = new DatagramSocket(port);

            Client client = new Client(k, myBucketId, ip, port, fileDictionary, myFileList, datagramSocket);
            client.initialize();

            Thread thread = new Thread(new Listener(client));
            thread.start();
            Thread heartBeatThread = new Thread(new HeartBeatHandler(client));
            heartBeatThread.start();
             } catch (SocketException ex) {
            Logger.getLogger(FastsparkApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        //interface to enter ip and port

        //using parameters create a DatagramSocket

        //if response REGOK
        //initialize

        //if response NOTOK
        //error message



    }
}
