package com.example.rofor.NeventProj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.CalendarlistView);

        //Create the ListItem objects
        ListItem sample1 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample2 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample3 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample4 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample5 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample6 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample7 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample8 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample9 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");
        ListItem sample10 = new ListItem("Sample Event","2401 Nueces St, Austin, Texas, 78705","04-01-2018");

        //Add the ListItem objects to an ArrayList
        ArrayList<ListItem> CalendarList = new ArrayList<>();
        CalendarList.add(sample1);
        CalendarList.add(sample2);
        CalendarList.add(sample3);
        CalendarList.add(sample4);
        CalendarList.add(sample5);
        CalendarList.add(sample6);
        CalendarList.add(sample7);
        CalendarList.add(sample8);
        CalendarList.add(sample9);
        CalendarList.add(sample10);
        ListItemAdapter adapter = new ListItemAdapter(this, R.layout.listitem, CalendarList);
        mListView.setAdapter(adapter);
    }
}


