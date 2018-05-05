package com.example.rofor.NeventProj;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView eventName, eventTime, eventWeather, eventTimeLeave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        eventName = (TextView) findViewById(R.id.txtEventNamedetails);
        eventTime = (TextView) findViewById(R.id.txtEventTimedetails);
        eventWeather = (TextView) findViewById(R.id.txtEventWeather);
        eventTimeLeave = (TextView) findViewById(R.id.txtTimeToLeaveEventDetail);

        eventName.setText(EventListObj.getInstance().getEvent(EventDetailsActivity.position2).EventName);
        eventTime.setText(EventListObj.getInstance().getEvent(EventDetailsActivity.position2).time);
        eventTimeLeave.setText(EventListObj.getInstance().getEvent(EventDetailsActivity.position2).eventKey);
        eventWeather.setText(EventListObj.getInstance().getEvent(EventDetailsActivity.position2).weatherTemperature);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

      //  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(MainActivity.currentLocation).title("Marker in current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MainActivity.currentLocation));
    }
}


