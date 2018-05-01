package com.example.rofor.NeventProj;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.EventLog;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.android.gms.auth.api.signin.GoogleSignIn;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInClient;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.common.api.ApiException;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.tasks.Task;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;
        import java.util.Timer;
        import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private Button signInButton;
    private TextView userName;

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

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.server_client_id))
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            userName = (TextView) findViewById(R.id.user_name);


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
            Log.w("success", "LaunchLogin");
            signInButton = (Button) view.findViewById(R.id.sign_in_button);
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent, 77);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 77) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.w("success", "it worked");
            updateUI(account.getDisplayName());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void updateUI(String name){
        signInButton.setVisibility(View.GONE);
        userName.setVisibility(View.VISIBLE);
        userName.setText("Welcome, " + name + "!");
    }

    }








