package com.example.equakeacitvity.model;

public class Earthquake {
    private double magnitude;
    private String place;
    private String date;

    private String url;
    private long timeinmiliseconds;


    public Earthquake() {
    }

    public Earthquake( String place,double magnitude,long timeinmiliseconds,String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.timeinmiliseconds=timeinmiliseconds;
        this.url=url;
    }

    public double getMagnitude() {
        return magnitude;
    }


    public String getPlace() {
        return place;
    }


    public String getUrl() {
        return url;
    }


    public void setTime(long timeinmiliseconds) {
        this.timeinmiliseconds = timeinmiliseconds;
    }
    public long getTimeinmiliseconds(){
        return  timeinmiliseconds;
    }
}
