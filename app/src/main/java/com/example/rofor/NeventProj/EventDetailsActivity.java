package com.example.rofor.NeventProj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {
    int position;
    EventDataObj currentEvent;
    ListView invitedList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Bundle extras = getIntent().getExtras();
        invitedList = findViewById(R.id.attendeeListDetails);
        Button invbtn = findViewById(R.id.inviteAttendeeBtn);
        invbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inviteAttendee(view);
            }
        });
        if( extras != null){
            position = extras.getInt("Position");
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,EventListObj.getInstance().getEvent(position).attendeeEmails);

        /*for(int i = 0; i < adapter.getCount(); i++){
            System.out.println("\n" + "Create" + position + "Adapter " + currentEvent.EventName + ": " + i + adapter.getItem(i));
        }
        for(int i = 0 ; i < currentEvent.attendeeEmails.size(); i++){
            System.out.println("\n" + "Create ArrayList" + i + currentEvent.attendeeEmails.get(i));
        }

        */
        invitedList.setAdapter(adapter);
        filldata();
        fillList();
    }


    private void fillList() {
        adapter.notifyDataSetChanged();
        /*for(int i = 0; i < adapter.getCount(); i++){
            System.out.println("\n" + "Fill Adapter " + currentEvent.EventName + ": " + i + adapter.getItem(i));
        }
        for(int i = 0 ; i < currentEvent.attendeeEmails.size(); i++){
            System.out.println("\n" + "Fill ArrayList" + i + currentEvent.attendeeEmails.get(i));

        }
        */
    }

    private void filldata() {
        TextView eventName = findViewById(R.id.eventDetails_eventName);
        TextView eventDesc = findViewById(R.id.eventDetails_eventDescription);
        TextView eventDate = findViewById(R.id.eventDetails_eventDate);
        TextView eventTime = findViewById(R.id.eventDetails_eventTime);
        TextView eventLati = findViewById(R.id.eventDetails_latitudeTEMP);
        TextView eventLong = findViewById(R.id.eventDetails_LongitudeTEMP);
        eventName.setText(EventListObj.getInstance().getEvent(position).EventName);
        eventDesc.setText(EventListObj.getInstance().getEvent(position).Description);
        eventDate.setText(EventListObj.getInstance().getEvent(position).date);
        eventTime.setText(EventListObj.getInstance().getEvent(position).time);
        eventLati.setText(String.valueOf(EventListObj.getInstance().getEvent(position).EventLocation.latitude));
        eventLong.setText(String.valueOf(EventListObj.getInstance().getEvent(position).EventLocation.longitude));
    }

    public void inviteAttendee(View v){
        //TODO: get currently logged in user and assign their email to the "currentemail" variable
        EditText invited = findViewById(R.id.attendeeEmail);
        String currentemail = "creator@email.com";
        if(currentemail.equals(EventListObj.getInstance().getEvent(position).creatorEmail)){
            EventListObj.getInstance().getEvent(position).invitePerson(invited.getText().toString());
            EventListObj.getInstance().getEvent(position).attendeeEmails.add(invited.getText().toString());
            fillList();

        }
        else{
            Toast.makeText(getBaseContext(),"Only Event Creator can invite other people to the event.", Toast.LENGTH_LONG).show();
        }
        invited.setText("");
    }
}
