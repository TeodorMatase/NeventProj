package com.example.rofor.NeventProj;

/**
 * Created by rofor on 3/23/2018.
 */

/**
 * Created by User on 3/14/2017.
 */

public class ListItem {
    private String Title;
    private String Location;
    private String Date;

    public ListItem(String t, String b,  String d) {
        this.Title = t;
        this.Location = b;
        this.Date = d;
    }

    public ListItem(){

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}