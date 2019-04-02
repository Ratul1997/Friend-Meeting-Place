package com.example.rat.meetup;

public class SendingMassage {
    String sender;
    String receiver;
    String massage;

    public SendingMassage(String sender, String receiver, String massage) {
        this.sender = sender;
        this.receiver = receiver;
        this.massage = massage;
    }
    public SendingMassage(){

    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMassage() {
        return massage;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
