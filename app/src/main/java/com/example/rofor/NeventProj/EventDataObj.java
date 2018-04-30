package com.example.rofor.NeventProj;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class EventDataObj {
//ALL GAE INTERACTION IS DONE THROUGH THE EVENT CLASS AND ITS METHODS
    String EventName;
    String Description;
    //TODO MAKE DATE AND TIME A GREGORIAN CALENDAR
    String datetime;
    String date;
    String time;
    Date eventDate;
    GregorianCalendar lastWeatherUpdate;
    String weatherCondition;
    String weatherTemperature;
    String weatherPOP;
    String creatorEmail;
    String eventKey;
    ArrayList<String> attendeeEmails;
    LatLng EventLocation;


    public EventDataObj(String creator, String Desc, String name, LatLng location, String time, String date){
        this.EventLocation = location;
        this.time = time;
        this.date = date;
        eventDate = dateTimeFormatter(time,date);
        lastWeatherUpdate = new GregorianCalendar();
        lastWeatherUpdate.add(Calendar.HOUR, -20);
        this.EventName = name;
        this.creatorEmail = creator;
        this.Description = Desc;
        attendeeEmails = new ArrayList<>();
        attendeeEmails.add(creatorEmail);
        updateWeather();
        //Create Event JSON Object
        //Send event JSON Object
        //Verify event created successfully
        //Update eventKey with response from GAE
    }

    private Date dateTimeFormatter(String t, String d) {
        String dateString = d + " " + t;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public void eventDeletion(){
        //Will call deletion method on GAE with eventKey and user email details
        //call delete method using eventKey;
        String tempCreator = "creator@email.com";
        if(tempCreator.equals(creatorEmail)){
            //delete event form GAE datstore
            EventListObj.getInstance().deleteEvent(this);
        }
        else{
            String tempInvited = "invited@email.com";
            //delete user from invited list
            if(attendeeEmails.contains(tempInvited)){
                attendeeEmails.remove(tempInvited);
            }
        }
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
        GregorianCalendar tempGreg = new GregorianCalendar();
        tempGreg = lastWeatherUpdate;
        tempGreg.add(Calendar.HOUR,6);
        GregorianCalendar tempCurr = new GregorianCalendar();
        //TODO MAKE SURE EVENT IS WITHIN 10 DAYS OF CALLING FOR WEATHER UPDATE
        //TODO MOVE THIS FROM INSIDE OF EVENTDATAOBJ TO AN ACTIVITY
        if((tempCurr.getTimeInMillis() >= tempGreg.getTimeInMillis())){
            String result = "";
            HttpReqHandler calHandler = new HttpReqHandler();
            result = calHandler.getHTTPData("http://api.wunderground.com/api/f964f5e53aa6a94e/hourly10day/q/"+EventLocation.latitude+","+EventLocation.longitude+".json");
            GregorianCalendar eventCal = new GregorianCalendar();
            eventCal.setTime(eventDate);
            //subtract currentmillis from eventmillis, convert to hours, check JSON value at that hour to get weather information
            Long currentMill = tempCurr.getTimeInMillis();
            Long eventMill = eventCal.getTimeInMillis();
            Long res = eventMill - currentMill;
            int hours   = (int) ((res / (1000*60*60)));
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray hrs = jsonObj.getJSONArray("hourly_forecast");
                JSONArray weatherData = jsonObj.getJSONArray(String.valueOf(hours));
                JSONArray conditions = jsonObj.getJSONArray("wx");//This is the conditions
                JSONArray popData = jsonObj.getJSONArray("pop");//This is the precipitation percentage data
                JSONArray tempData = jsonObj.getJSONArray("english");//This is the temperature
                weatherCondition = conditions.toString();
                weatherPOP = popData.toString();
                weatherTemperature = tempData.toString();
                lastWeatherUpdate = tempCurr;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else{
            //Toast.makeText(this,"Weather can only be refreshed once every 6 hours", Toast.LENGTH_LONG);
        }

    }

    public boolean updateEvent(EventDataObj newDetails){
        //Similar to constructor - different method on GAE to signify overwriting old event data with new event data, DO NOT MAKE A NEW EVENT OBJECT)
        //
        return true;
    }
}
