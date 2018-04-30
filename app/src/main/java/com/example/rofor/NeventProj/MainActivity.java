package com.example.rofor.NeventProj;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.EventLog;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;

        import com.google.android.gms.maps.model.LatLng;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;
        import java.util.Timer;
        import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button mapbtn = findViewById(R.id.MapsBtn);
            mapbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LaunchMaps(view);
                }
            });
            sampledatafill();
        }

    private void sampledatafill() {
        LatLng temp = new LatLng(30,30);
        EventDataObj sample1 = new EventDataObj("creator@email.com","d1","n1",temp,"1:00","5/05/2018");
        EventDataObj sample2 = new EventDataObj("creator@email.com","d2","n2",temp,"1:00","5/05/2018");
        EventListObj.getInstance().addEvent(sample1);
        EventListObj.getInstance().getEvent(0).attendeeEmails.add("sample1@gmail.com");
        EventListObj.getInstance().addEvent(sample2);
        EventListObj.getInstance().getEvent(1).attendeeEmails.add("sample2@gmail.com");

        }

    //TODO: Integrate google auth, update EventList on login and on opening of app
        public void LaunchLogin(View view){
            //Intent intent = new Intent(this, Login.class);
            //startActivity(intent);
        }

        public void LaunchCalendar(View view){
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
        }

        public void LaunchMaps(View view){
            Intent intent = new Intent(this, Mapactivityview.class);
            startActivity(intent);
        }

        public void LaunchEventList(View view){
            Intent intent = new Intent(this, EventListActivity.class);
            startActivity(intent);
        }

        public void LaunchEventCreation(View view){
            Intent intent = new Intent(this, EventCreation.class);
            startActivity(intent);
        }

    }








