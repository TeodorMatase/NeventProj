package com.example.rofor.NeventProj;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class EventDataObj {

    String EventName;
    String Description;
    Date date;

    String creatorEmail;
    String eventKey;
    ArrayList<String> attendeeEmails;
    LatLng EventLocation;


    public EventDataObj(String creator, String Desc, String name, LatLng location){
        this.EventLocation = location;
        this.EventName = name;
        this.creatorEmail = creator;
        this.Description = Desc;
        //Create Event JSON Object
        //Send event JSON Object
        //Verify event created successfully
        //Update eventKey with response from GAE
    }

    public boolean eventDeletion(){
        //Ensure current google auth id matches event creator email(android)
        //call delete method using eventKey;
        return true;

    }

    public boolean invitePerson(String email){
        //Update event object with new invitation list
        //use updateEvent() method to send event object to GAE
        //GAE searches through the invitation list to see if any emails have not yet been invited and then invites them
        return true;
    }

    public void updateWeather(){
        //Call wunderground API with key: f964f5e53aa6a94e
        //IF event date is more than 10days away do not call
        //Only able to call once per 6 hours
        //http://api.wunderground.com/api/f964f5e53aa6a94e/hourly10day/q/TX/Austin.json sample call
        //Step 1: determine difference in time from current device time to event time
        //Step 2: make the 10dayhourly call and retrieve JSON
        //Step 3: open the Hourly object corresponding to the difference in time
        //Step 4: Get temperature(TEMP->ENGLISH), Icon(use whatever iconpack you want and put the icons in drawable folder), and POP, description(WX)
        //
    }

    public boolean updateEvent(){
        //Similar to constructor - different method on GAE to signify overwriting old event data with new event data, DO NOT MAKE A NEW EVENT OBJECT)
        //
        return true;
    }
}
