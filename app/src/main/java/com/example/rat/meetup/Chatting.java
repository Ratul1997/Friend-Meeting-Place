package com.example.rat.meetup;

public class Chatting {
    String usId,fname,lname;


    public Chatting(String usId, String fname, String lname) {
        this.usId = usId;
        this.fname = fname;
        this.lname = lname;
    }

    public String getUsId() {
        return usId;
    }

    public void setUsId(String usId) {
        this.usId = usId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Chatting(){

    }
}