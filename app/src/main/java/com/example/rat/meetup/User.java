package com.example.rat.meetup;

public class User {
    String Usr_Id;
    String FName;
    String LName;

    public User(String usr_Id, String FName, String LName) {
        this.Usr_Id = usr_Id;
        this.FName = FName;
        this.LName = LName;
    }

    public String getUsr_Id() {
        return Usr_Id;
    }

    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }


}
