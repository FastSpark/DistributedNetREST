package com.fastspark.fastspark.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Buddhi on 11/1/2017.
 */

public class Client {

    private int k;
    private String ip;
    private int myBucketId;
    private int port;
    private String username;
    private Map<Integer, Node> bucketTable;
    private Map<String, ArrayList<String>> fileDictionary;
    private Timestamp timestamp;
    private ArrayList<String> myFileList;
    private ArrayList<Node> myNodeList;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    private DatagramSocket datagramSocket;

    @Autowired
    public Client(int k, int myBucketId, String ip, int port, Map<String, ArrayList<String>> fileDictionary, ArrayList<String> myFileList, DatagramSocket datagramSocket) throws SocketException {
        this.k = k; // get from main
        this.myBucketId = myBucketId;
        //this.status = "0";
        this.ip = ip;
        this.port = port;
       // this.userName = username;
        this.bucketTable = new HashMap<>();
        this.fileDictionary = fileDictionary;
        this.myFileList = myFileList;
        this.myNodeList = new ArrayList<>();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.datagramSocket = datagramSocket;
    }


    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getMyBucketId() {
        return myBucketId;
    }

    public void setMyBucketId(int myBucketId) {
        this.myBucketId = myBucketId;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Integer, Node> getBucketTable() {
        return bucketTable;
    }

    public void setBucketTable(Map<Integer, Node> bucketTable) {
        this.bucketTable = bucketTable;
    }

    public Map<String, ArrayList<String>> getFileDictionary() {
        return fileDictionary;
    }

    public void setFileDictionary(Map<String, ArrayList<String>> fileDictionary) {
        this.fileDictionary = fileDictionary;
    }

    public ArrayList<String> getMyFileList() {
        return myFileList;
    }

    public void setMyFileList(ArrayList<String> myFileList) {
        this.myFileList = myFileList;
    }

    public ArrayList<Node> getMyNodeList() {
        return myNodeList;
    }

    public void setMyNodeList(ArrayList<Node> myNodeList) {
        this.myNodeList = myNodeList;
    }

    public void sendMessage(String msg) {
        try {
            System.out.println("Sending message: " + msg);

            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(this.ip), 55555);
            datagramSocket.send(dp);

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initialize() {
        // Register With Bootstrap Server
        String msg = " REG " + this.ip + " " + this.port;
        msg = "00" + Integer.toString(msg.length()) + msg;

        sendMessage(msg);

    }

    // handles REGOK responses from BS
    // length REGOK no_nodes IP_1 port_1 IP_2 port_2
    public void handleRegisterResponse(String msg) throws IOException {
        String[] arr = msg.split(" ");

        // validate msg
        if (!arr[1].equals("REGOK")) {
            return;
        }

        switch (arr[2]) {
            case "0":
                System.out.println("You are the first node, registered successfully with BS!");
                // change up the "status" to ready (1) ????
                break;
            case "1":
                storeNode(arr[3], arr[4]);
                // change up the "status" to ready (1) ????
                break;
            case "2":
                storeNode(arr[3], arr[4]);
                storeNode(arr[5], arr[6]);
                // change up the "status" to ready (1) ????
                break;
            case "9999":
                System.out.println("failed, there is some error in the command");
                break;
            case "9998":
                System.out.println("failed, already registered! attempting unregister first");
                break;
            case "9997":
                System.out.println("failed, registered to another user, try a different IP and port");
                // TODO
                break;
            case "9996":
                System.out.println("failed, can't register. BS full.");
            default:
                // store FIRST 2 nodes' details
                storeNode(arr[3], arr[4]);
                storeNode(arr[5], arr[6]);

                // complete bucketTable
                for (int i = 0; i < k; i++) {

                    if (!bucketTable.containsKey(i)) {
//                        findNodeFromBucket(i);   //handle exceptions
                    }
                }

                // complete myNodeList
                if (myNodeList.isEmpty()) {
                    // findNodeFromBucket(myBucketId);
                    // send message to that returned node to get it's myNodeList and then store
                } else {
                    // send message to that node to get it's myNodeList and then store
                }

                // change up the "status" to ready (1)
                break;
        }
//        while (true) {
//            System.out.println("");
//            System.out.print("Input Next Command : ");
//
//            msg = scanner.nextLine();
//            switch (msg) {
//                case "DISPLAY FILES":
//                    displayFiles();
//                    break;
//                case "DISPLAY TABLE":
//                    displayRoutingTable();
//                    break;
//                case "SEARCH FILES":
//                    searchFiles(msg);
//                    break;
//                default:
//                    break;
//            }
//        }

        connectWithNodes();

    }

    private void storeNode(String ip, String port) {
        Node newNode = new Node(ip, Integer.parseInt(port));
        int bucketId = (ip + ":" + port).hashCode() % k;
        if (bucketId == this.myBucketId) {
            myNodeList.add(newNode);
        } else {
            bucketTable.put(bucketId, newNode);
        }
    }

    private void connectWithNodes() {
        // indicate that I'm new to net
        // send file list with this
        // that node response with its myNodeList
    }

    public void connectWithNodesResponse(){

    }

    public void displayFiles() {
        System.out.println("Files in ");
        this.myFileList.forEach((file) -> {
            System.out.println(file);
        });
    }

    public void displayRoutingTable() {
        if (myNodeList.isEmpty() && bucketTable.isEmpty()) {
            System.out.println("Tables are empty");
        } else {
            System.out.println("Nodes list in the Bucket:");
            for (Node node : myNodeList) {
                System.out.println("\t" + node.getIp() + ":" + node.getPort());
            }

            System.out.println("Nodes list from other Buckets:");
            Iterator entries = bucketTable.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                Integer key = (Integer) entry.getKey();
                Node node = (Node) entry.getValue();
                System.out.println("Bucket " + key + " : " + node.getIp() + ":" + node.getPort());
            }
        }
    }

    public void initializeSearch(String msg) throws IOException{
        //SEARCH_FILES file_name
        String file_name= msg.split(" ")[1];
        String result_string="";

        //length SEROK no_files IP port hops filename1 filename2 ... ...
        ArrayList<String> results = new ArrayList<String>();
        Pattern p = Pattern.compile(".*\\\\b"+file_name+"\\\\b.*");
        Set<String> keys = fileDictionary.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            String candidate = iterator.next();
            Matcher m = p.matcher(candidate);
            if (m.matches()) {
                results.add(candidate);
                result_string.concat(candidate+" ");
            }
        }
        System.out.println(result_string);

        /////////
        String net_message="SER "+this.getIp()+" "+this.getPort()+" "+msg.split(" ")[1]+" 1";
        net_message = String.format("%04d", net_message.length() + 5) + " " + net_message;
        searchFiles(net_message);
    }

    public void searchFiles(String message) throws UnknownHostException, IOException {
        //length SER IP port file_name hops
        String[] split = message.split(" ");
        String file_name= split[4];
        String result_string="";

        int hop_count=0;
        if(split.length==6)
            hop_count=Integer.valueOf(split[5]);

        //length SEROK no_files IP port hops filename1 filename2 ... ...
        ArrayList<String> results = new ArrayList<String>();
        Pattern p = Pattern.compile("[a-zA-Z\\s]*"+file_name+"[a-zA-Z\\s]*");

        Set<String> keys = new HashSet<>(myFileList);
        Iterator<String> iterator = keys.iterator();

        ArrayList<String> nodes=new ArrayList<>();
        ArrayList<Node> nodelist = new ArrayList<>();

        //search in my files list
        while (iterator.hasNext()) {
            String candidate = iterator.next();
            Matcher m = p.matcher(candidate);
            if (m.matches()) {
                results.add(candidate);
                System.out.println(candidate);
                result_string  = result_string.concat(candidate+" ");
            }
        }
        if(results.size()>0){
            String ret_message= "SEROK "+results.size()+" "+this.getIp()+" "+this.getPort()+" "+(hop_count++)+" "+result_string;
            ret_message = String.format("%04d", ret_message.length() + 5) + " " + ret_message;
            System.out.println(ret_message);
        }else{

            keys = fileDictionary.keySet();
            iterator = keys.iterator();

            boolean found=false;

            while (iterator.hasNext()) {
                String candidate = iterator.next();
                Matcher m = p.matcher(candidate);
                if (m.matches())
                {
                    nodes=fileDictionary.get(candidate);
                    for(String node: nodes){
                        nodelist.add(new Node(node.split(":")[0], Integer.parseInt(node.split(":")[1])));
                    }
                    multicast(message,nodelist );
                }
                found= true;
            }

            if(!found){
                multicast(message, new ArrayList<Node>(bucketTable.values()));
            }
        }
    }

    public void findNodeFromBucket(int bucketId) throws UnknownHostException, IOException {
        //FBM: Find Bucket Member 0011 FBM 01
        String message = "FBM " + bucketId;
        message = String.format("%04d", message.length() + 5) + " " + message;
        multicast(message, myNodeList);
    }

    public void findNodeFromBucketReply(int bucketId, Node fromNode) throws UnknownHostException, IOException {
        //FBMOK: Find Bucket Member OK
        Node nodeFromBucket = null;
        String message = null;
        if (bucketTable.get(bucketId) != null) {
            nodeFromBucket = bucketTable.get(bucketId);
            message = "FBMOK " + bucketId + "" + nodeFromBucket.getIp() + " " + nodeFromBucket.getPort();
        } else {
            message = "FBMOK " + bucketId + " null null";
        }
        message = String.format("%04d", message.length() + 5) + " " + message;
        unicast(message, fromNode);
    }

    public void receiveReplyFindNodeFromBucket(String message) throws UnknownHostException, IOException {

        String[] split_msg = message.split(" ");
        Node bucket_node = new Node(split_msg[3], Integer.valueOf(split_msg[4]));
        if(this.getBucketTable().get(split_msg[2])!=null){
            this.bucketTable.put(Integer.valueOf(split_msg[2]), bucket_node);
        }
    }

    public void multicast(String message, ArrayList<Node> nodesList) throws SocketException, UnknownHostException, IOException {

        RestTemplate restTemplate = new RestTemplate();
        for (Node node : nodesList) {
            String uri = "http://"+node.getIp()+":"+node.getPort()+"/request";
            Message sendMessage = new Message(node.getIp(), node.getPort(), message);
            Message response = restTemplate.postForObject( uri,sendMessage ,Message.class);
            this.handleMessage(response.getMessage());
        }
    }

    public void unicast(String message, Node node) throws SocketException, UnknownHostException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://"+node.getIp()+":"+node.getPort()+"/request";
        Message sendMessage = new Message(node.getIp(), node.getPort(), message);
        Message response = restTemplate.postForObject( uri,sendMessage ,Message.class);
        this.handleMessage(response.getMessage());
    }

    //gracefull leave
    public void leave(int bucketId) throws IOException {
        String message = "LEAVING BUCKET " + bucketId;
        multicast(message, myNodeList);
    }

    public void updateRountingTable() throws IOException {
        ArrayList<Node> temNodeList = new ArrayList<Node>();
        for (Node node : myNodeList) {
            if (timestamp.getTime() - node.getTimeStamp() < 5000) {
                temNodeList.add(node);
            }
        }
        this.myNodeList = temNodeList;

        for (int key : bucketTable.keySet()) {

            Node neighbour = bucketTable.get(key);
            if (timestamp.getTime() - neighbour.getTimeStamp() > 5000) {
                bucketTable.remove(key);
                this.findNodeFromBucket(key);
            }
        }

    }

    public void handleHeartBeatResponse(String message) {
        //length HEARTBEATOK IP_address port_no
        boolean is_Change = false;
        ArrayList<Node> temNodeList = new ArrayList<Node>();
        String[] splitMessage = message.split(" ");
        String ip = splitMessage[2];
        int port = Integer.parseInt(splitMessage[3]);
        for (Node node : myNodeList) {
            if (node.getIp().equals(ip) && node.getPort() == port) {
                node.setTimeStamp(timestamp.getTime());
                is_Change = true;
            }
            temNodeList.add(node);
        }
        this.myNodeList = temNodeList;

        if (!is_Change) {
            for (int key : bucketTable.keySet()) {
                Node node = bucketTable.get(key);
                if (node.getIp().equals(ip) && node.getPort() == port) {
                    node.setTimeStamp(timestamp.getTime());
                    bucketTable.replace(key, node);
                }
            }
        }
    }

    public void sendHeartBeatReply(String message) throws IOException {
        String newMessage="HEARTBEATOK "+this.getIp()+" "+this.getPort();
        newMessage = String.format("%04d", newMessage.length() + 5) + " " + newMessage;
        String[] splitMessage = message.split(" ");
        Node node = new Node(splitMessage[2], Integer.parseInt(splitMessage[3]));
        unicast(newMessage, node);
    }

    void sendMessageToAPI(String message) {
        //call to REST api calls
    }



    public String handleMessage(String message) throws IOException {
        String[] messagePart = message.split(" ");
        switch (messagePart[1]) {
            case "REGOK":
                //handle  response from bootstrap
                System.out.println(message);
                this.handleRegisterResponse(message);
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
                this.handleHeartBeatResponse(message);
                break;
            case "HEARTBEAT":
                System.out.println(message);
                this.sendHeartBeatReply(message);
                break;
            //this.client.
            case "FBM": //multicast message to find a node from a bucket
                System.out.println(message);
                this.findNodeFromBucketReply(Integer.parseInt(messagePart[2]),new Node(this.getIp(), this.getPort()));
                break;
            case "FBMOK": //reply to FBM
                this.receiveReplyFindNodeFromBucket(message);
                break;

        }

        return message;
    }

}
