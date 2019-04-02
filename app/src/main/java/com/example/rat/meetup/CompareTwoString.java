package com.example.rat.meetup;

import java.math.BigInteger;

public class CompareTwoString {
    String send;
    String rec;
    String id;
    public CompareTwoString(String send, String rec) {
        this.send = send;
        this.rec = rec;
    }

    public String getId() {
        return id;
    }

    public String compare(){
        BigInteger snd = new BigInteger(send);
        BigInteger rc = new BigInteger(rec);
        int res = snd.compareTo(rc);

        if(res == -1){
            id = send+rec;
        }

        if(res == 1){
            id = rec+send;
        }
        return  id;
    }
}
