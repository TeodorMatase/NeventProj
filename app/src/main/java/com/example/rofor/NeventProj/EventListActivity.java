package com.example.rofor.NeventProj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    EventListObj MasterList;
    ListView eventListView;
    ArrayList<ListItem> ListEventsToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        MasterList = EventListObj.getInstance();
        eventListView = findViewById(R.id.EventListView);
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
                Intent detailsView = new Intent(EventListActivity.this, EventDetailsActivity.class);
                detailsView.putExtra("Position", position);
                startActivity(detailsView);
            }
        });
    }
}

