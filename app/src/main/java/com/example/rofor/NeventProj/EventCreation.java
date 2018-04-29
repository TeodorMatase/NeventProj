package com.example.rofor.NeventProj;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import static java.sql.DriverManager.println;

public class EventCreation extends FragmentActivity {
    static TextView timeText;
    static TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        timeText = findViewById(R.id.eventCreation_eventTime);
        dateText = findViewById(R.id.eventCreation_eventDate);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
           //Create Date object and add to eventObject
            String tempOUT = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            timeText.setText(tempOUT);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String tempOUT = String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);
            dateText.setText(tempOUT);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void createEvent(View v){
        LatLng temp = new LatLng(30,30);
        //TODO: Get LatLNG from Google Maps, get current user to substitute for "creator@email.com"
        EditText desctxt = findViewById(R.id.eventCreation_eventDescription);
        EditText nametxt = findViewById(R.id.eventCreation_eventName);
        EventDataObj newEv = new EventDataObj("creator@email.com",desctxt.getText().toString(),nametxt.getText().toString(),temp,timeText.getText().toString(),dateText.getText().toString());
        EventListObj.getInstance().addEvent(newEv);
        Toast.makeText(getBaseContext(),"Event Created Successfully", Toast.LENGTH_SHORT).show();
    }
}
