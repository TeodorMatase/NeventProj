package com.example.rofor.NeventProj;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.MalformedInputException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void ListItemGetterTest(){
        ListItem li = new ListItem("Meeting", "Office", "Monday");
        String fields[] = {"Meeting", "Office", "Monday"};
        assertTrue(fields[0].equals(li.getTitle()));
        assertTrue(fields[1].equals(li.getLocation()));
        assertTrue(fields[2].equals(li.getDate()));
    }

    @Test public void ListItemSetterTest(){
        ListItem li = new ListItem();
        String fields[] = {"A", "B", "C"};
        li.setTitle("A");
        li.setLocation("B");
        li.setDate("C");
        assertTrue(fields[0].equals(li.getTitle()));
        assertTrue(fields[1].equals(li.getLocation()));
        assertTrue(fields[2].equals(li.getDate()));
    }

    @Test public void EventDataInviteTest(){
        EventDataObj e = new EventDataObj();
        assertTrue(e.invitePerson("goodemail@email.com"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void EventDataInviteTest2(){
        EventDataObj e = new EventDataObj();
        String email = "bademail";
        e.invitePerson(email);
    }

    @Test
    public void testEvent() throws Exception{
        String creator = "junit@email.com";
        String description = "Junit Event description";
        String name = "Junit Event name";
        LatLng test = new LatLng(35.0,35.0);
        String time = "01:10";
        String date = "5/02/18";
        EventDataObj testEve = new EventDataObj(creator,description,name,test,time,date);
        EventListObj.getInstance().addEvent(testEve);
        assertTrue(creator.equals(EventListObj.getInstance().getEvent(0).creatorEmail));
        assertTrue(description.equals(EventListObj.getInstance().getEvent(0).Description));
        assertTrue(name.equals(EventListObj.getInstance().getEvent(0).EventName));
        assertTrue(time.equals(EventListObj.getInstance().getEvent(0).time));
        assertTrue(date.equals(EventListObj.getInstance().getEvent(0).date));
        assertTrue(test.latitude == EventListObj.getInstance().getEvent(0).EventLocation.latitude);
        assertTrue(test.longitude == EventListObj.getInstance().getEvent(0).EventLocation.longitude);
    }






}


