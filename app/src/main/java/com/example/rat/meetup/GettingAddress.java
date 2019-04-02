package com.example.rat.meetup;

public class GettingAddress  {

    double usrLng;
    double usrLat;

    public GettingAddress(double usrLng, double usrLat) {
        this.usrLng = usrLng;
        this.usrLat = usrLat;
    }
    public GettingAddress(){

    }

    public void setUsrLng(double usrLng) {
        this.usrLng = usrLng;
    }

    public void setUsrLat(double usrLat) {
        this.usrLat = usrLat;
    }

    public double getUsrLng() {
        return usrLng;
    }

    public double getUsrLat() {
        return usrLat;
    }
}
