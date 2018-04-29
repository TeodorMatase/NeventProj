
package com.example.rofor.NeventProj;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class Mapactivityview extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    String MarkerTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivityview);

        Button refreshbtn = findViewById(R.id.refreshBtn);
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng destination = new LatLng (10,10);
                TextView temp = findViewById(R.id.txtEventTimedetails);
                temp.setText(estimateTimeToLeave(destination));
            }
        });

        /*if(GMSAvailable()){
            setContentView(R.layout.activity_mapactivityview);
            createMap();
            final EditText userAddress = (EditText)findViewById(R.id.location_txtview);
            Button findBtn = (Button)findViewById(R.id.location_btn);
            findBtn.setOnClickListener(new View.OnClickListener() {
               // @Override
                public void onClick(View v) {
                    //update mLatLng based on data in txt field
                    //update map based on new LatLng
                    new GetNewCoordinates().execute(userAddress.getText().toString().replace(" ","+"));
                }
            });
        }
        else{
            //setContentView(R.layout.nogooglemaps);
        }*/

    }

    public String estimateTimeToLeave(LatLng dest){
        //TODO
        return "default_time";
    }

    private void createMap() {
        MapFragment mapFrag = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFrag.getMapAsync(this);
    }

    public boolean GMSAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int available = api.isGooglePlayServicesAvailable(this);
        if(available == ConnectionResult.SUCCESS){
            return true;
        }
        else if(api.isUserResolvableError(available)){
            Dialog dialog = api.getErrorDialog(this,available,0);
            dialog.show();
        }
        else {
            Toast.makeText(this,"Play Services not found", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        updateLocation(10,10,12);
    }

    private void updateLocation(double LAT, double LNG, float ZOOM) {
        LatLng tempLL = new LatLng(LAT,LNG);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(tempLL, ZOOM);
        mGoogleMap.moveCamera(update);
        mGoogleMap.addMarker(new MarkerOptions().position(tempLL).title(MarkerTxt));
    }

    private class GetNewCoordinates extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                String key = getString(R.string.geokey);
                HttpReqHandler reqHand = new HttpReqHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",address,key);
                response = reqHand.getHTTPData(url);
                return response;
            }
            catch(Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            try{
                JSONObject jsonObject = new JSONObject(s);
                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
                MarkerTxt = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getString("formatted_address");
                updateLocation(Double.parseDouble(lat),Double.parseDouble(lng),12);
            }
            catch(Exception e){

            }
        }
    }

}


