package com.example.rat.meetup;

public class FriendList {
    String Fname;
    String Lname;
    String UserName;
    String Id;

    public FriendList(String fname, String lname, String userName, String id) {
        Fname = fname;
        Lname = lname;
        UserName = userName;
        Id = id;
    }

    public String getFname() {
        return Fname;
    }

    public String getLname() {
        return Lname;
    }

    public String getUserName() {
        return UserName;
    }

    public String getId() {
        return Id;
    }
}
