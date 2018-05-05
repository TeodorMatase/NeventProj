package com.example.rofor.NeventProj;

import java.util.ArrayList;

public class EventListObj {
    private static EventListObj INSTANCE = null;

    private ArrayList<EventDataObj> allEvents;

    // other instance variables can be here

    private EventListObj(){
        allEvents = new ArrayList<>();
    };

    public synchronized static EventListObj getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventListObj();
        }
        return(INSTANCE);
    }
        // other instance methods can follow

    public void addEvent(EventDataObj t){
        allEvents.add(t);
    }

    public void deleteEvent(EventDataObj t ){
        t.eventDeletion();
    }

    public EventDataObj getEvent(int index){
        return allEvents.get(index);
    }

    public void updateEvent(int index, EventDataObj t){
        allEvents.get(index).updateEvent(t);
    }

    public int size(){
        return allEvents.size();
    }

    public void setList(ArrayList<EventDataObj> newList){
        this.allEvents = newList;
    }
}
