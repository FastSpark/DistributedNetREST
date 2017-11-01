package com.fastspark.fastspark.model;

import java.util.ArrayList;
import java.util.Map;

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
    private ArrayList<String> myFileList;
    private ArrayList<Node> myNodeList;

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
}
