package com.example.rat.meetup;

public class Group {
    String sender;
    String receiver;
    String massage;

    public Group(String sender,  String massage) {
        this.sender = sender;
        this.massage = massage;
    }
    public Group(){

    }

    public String getSender() {
        return sender;
    }

    public String getMassage() {
        return massage;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
