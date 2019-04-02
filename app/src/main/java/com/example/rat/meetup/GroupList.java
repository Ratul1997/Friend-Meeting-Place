package com.example.rat.meetup;

public class GroupList {
    String gropid;
    String gropname;

    public GroupList(String gropid, String gropname) {
        this.gropid = gropid;
        this.gropname = gropname;
    }

    public String getGropid() {
        return gropid;
    }

    public void setGropid(String gropid) {
        this.gropid = gropid;
    }

    public String getGropname() {
        return gropname;
    }

    public void setGropname(String gropname) {
        this.gropname = gropname;
    }
}
