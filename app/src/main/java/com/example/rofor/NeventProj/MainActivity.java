package com.example.rofor.NeventProj;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;

        import java.util.ArrayList;
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
        }
        //TODO: Integrate google auth
        public void LaunchLogin(View view){
            //Intent intent = new Intent(this, Login.class);
            //startActivity(intent);
        }

        public void LaunchCalendar(View view){
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
        }

        public void LaunchMaps(View view){
            //Intent intent = new Intent(this, MapsActivity.class);
            //startActivity(intent);
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








