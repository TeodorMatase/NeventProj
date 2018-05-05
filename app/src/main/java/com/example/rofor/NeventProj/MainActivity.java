package com.example.rofor.NeventProj;
        import android.Manifest;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentSender;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.preference.PreferenceManager;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.EventLog;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.android.gms.auth.api.signin.GoogleSignIn;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInClient;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.common.api.ApiException;
        import com.google.android.gms.common.api.ResolvableApiException;
       // import com.google.android.gms.location.LocationListener;
        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationCallback;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationResult;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.location.LocationSettingsRequest;
        import com.google.android.gms.location.LocationSettingsResponse;
        import com.google.android.gms.location.SettingsClient;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import org.json.JSONObject;

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
    private LatLng userLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    public static LatLng currentLocation;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    public static String userEmail;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            currentLocation = new LatLng(0,0);



            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.server_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            userName = (TextView) findViewById(R.id.user_name);
            createLocationRequest();

            sampledatafill();

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        2);
            }

            else {

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

                try {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                                    }

                                }
                            });
                } catch (SecurityException e) {

                }

            }

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if(MainActivity.userEmail != null) {
                     //       EventListObj.getInstance().setList(getInvitedEvents());
                        }

                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.d("list", Double.toString(currentLocation.latitude));
                        updateDirections(currentLocation);
                    }
                };
            };

            startLocationUpdates();
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
            updateUI(account.getEmail());
            userEmail = account.getEmail();
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

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("list", "Success");
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                1);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    private void startLocationUpdates() {
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null /* Looper */);
        }
        catch(SecurityException e){

        }
    }


    private void updateDirections(LatLng currentGPS){

    try{

        for(int x=0; x<EventListObj.getInstance().size(); x++){

            EventDataObj userEvent = EventListObj.getInstance().getEvent(x);

            LatLng eventLocation = userEvent.EventLocation;
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");

            java.util.Date eventDate = f.parse(EventListObj.getInstance().getEvent(x).date + " " + EventListObj.getInstance()
            .getEvent(x).time);
            long eventTimeSecs = eventDate.getTime()/1000;
            //long eventTimeSecs = userEvent.getTime()/1000;


                JSONObject baseCall = new JSONObject(apiCall("https://maps.googleapis.com/maps/api/directions/json?origin=" + currentGPS.latitude + "," + currentGPS.longitude +
                        "&destination=" + eventLocation.latitude + "," + eventLocation.longitude + "&arrival_time=" + eventTimeSecs +
                        "&key=AIzaSyAwE2E4VAPjKP_vr-LM7_x0qP6n1q3-7fs"));

             //   int baseTimeSecs = baseCall.parse("routes":"legs":"duration":"value"); //30
                int baseTimeSecs = baseCall.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).
                        getJSONObject("duration").getInt("value");
                Log.d("api", Integer.toString(baseTimeSecs));

                long estDeparture = eventTimeSecs - baseTimeSecs; //3:30

                JSONObject estimatedTrafficCall = new JSONObject(apiCall("https://maps.googleapis.com/maps/api/directions/json?origin=" + currentGPS.latitude + "," + currentGPS.longitude +
                        "&destination=" + eventLocation.latitude + "," + eventLocation.longitude + "&traffic_model=pessimistic&departure_time=" + estDeparture
                        + "&key=AIzaSyAwE2E4VAPjKP_vr-LM7_x0qP6n1q3-7fs"));

              //  int trafficTimeSecs = estimatedTrafficCall.parse("routes":"legs":"duration_in_traffic":"value"); //45
                int trafficTimeSecs = estimatedTrafficCall.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).
                        getJSONObject("duration_in_traffic").getInt("value");

                long estTrafficDeparture = eventTimeSecs - trafficTimeSecs; //3:15

                boolean goodTime = false;
                JSONObject estimatedTrafficCall2;
                int trafficTimeSecs2;

                while (!goodTime) {
                    estimatedTrafficCall2 = new JSONObject(apiCall("https://maps.googleapis.com/maps/api/directions/json?origin=" + currentGPS.latitude + "," + currentGPS.longitude +
                            "&destination=" + eventLocation.latitude + "," + eventLocation.longitude + "&traffic_model=pessimistic&departure_time=" + estTrafficDeparture
                            + "&key=AIzaSyAwE2E4VAPjKP_vr-LM7_x0qP6n1q3-7fs"));

                  //  trafficTimeSecs2 = estimatedTrafficCall2.parse("routes":"legs":"duration_in_traffic":"value");

                    trafficTimeSecs2 = estimatedTrafficCall2.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).
                            getJSONObject("duration_in_traffic").getInt("value");


                    if (trafficTimeSecs2 + estTrafficDeparture < eventTimeSecs) {
                        goodTime = true;
                    } else {
                        estTrafficDeparture -= 15 * 60;
                    }
                }

                userEvent.eventKey = String.valueOf(estTrafficDeparture);
            }

        }
    catch(Exception e){}
    }

    private String apiCall(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        String result = "";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            sharedResponse(response);
                        }
                        catch(Exception e){
                            Log.d("error", "JSONerror");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "Error!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        result = m.getString("Response", "");

        return result;
    }

    private void sharedResponse(String response) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }

    public String fromServerMethod(String useremail) {
        return "https://nevent-1521508691987.appspot.com/hello?user="+useremail;
    }

    public ArrayList<EventDataObj> getInvitedEvents(){
        Gson gson = new Gson();
        Log.d("err", MainActivity.userEmail);

        ArrayList<EventDataObj> ListOfEventsUserIsInvitedTo = gson.fromJson(apiPostCall(fromServerMethod(MainActivity.userEmail)), new TypeToken<ArrayList<EventDataObj>>() {}.getType());
        return ListOfEventsUserIsInvitedTo;
    }

    private String apiPostCall(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        String result = "";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            sharedResponse(response);
                        }
                        catch(Exception e){
                            Log.d("error", "JSONerror");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "Error!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        result = m.getString("Response", "");

        return result;
    }

}








