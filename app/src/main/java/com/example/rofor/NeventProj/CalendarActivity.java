package com.example.rofor.NeventProj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    EventListObj MasterList;
    ListView eventListView;
    ArrayList<ListItem> ListEventsToDisplay;

    private static final String TAG = "CalendarActivity";
    //TODO Make events show up on calendar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG, "onCreate: Started.");

        //Create the ListItem objects
        MasterList = EventListObj.getInstance();
        eventListView = findViewById(R.id.CalendarlistView);
        ListEventsToDisplay = new ArrayList<>();
        fillList();

    }

    private void fillList() {
        for(int i = 0; i < MasterList.size(); i++){
            EventDataObj tempEv = MasterList.getEvent(i);
            ListEventsToDisplay.add(new ListItem(tempEv.EventName.toString(),tempEv.EventLocation.toString(),tempEv.date.toString() + " at " + tempEv.time.toString()));
        }
        ListItemAdapter adapter = new ListItemAdapter(this,R.layout.listitem,ListEventsToDisplay);
        eventListView.setAdapter(adapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),"Position: "+position, Toast.LENGTH_SHORT).show();
                Intent detailsView = new Intent(CalendarActivity.this, EventDetailsActivity.class);
                detailsView.putExtra("Position", position);
                startActivity(detailsView);
            }
        });
    }
}


