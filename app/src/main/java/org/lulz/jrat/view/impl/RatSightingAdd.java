package org.lulz.jrat.view.impl;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An activity representing a list of RatSightings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RatSightingDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RatSightingAdd extends AppCompatActivity {
    private  EditText dateText = null, timeText = null;
    private int year, month, day, hour, minute;
    private String locationType, address, city, borough, zipcode;
    private DatePicker datePicker;
    private Calendar calendar;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratsighting_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        Button reportBUtton = null;

        reportBUtton = (Button) findViewById(R.id.report_button);
        reportBUtton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                reportSighting();

            }
        });
        Button cancelButton = null;

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent regIntent = new Intent(getApplicationContext(), RatSightingListActivity.class);
                startActivity(regIntent);

            }
        });


        dateText = (EditText) findViewById(R.id.date);
        dateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker();
            }
        });
        timeText = (EditText) findViewById(R.id.time);
        timeText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePicker();
            }
        });
    }
    public void datePicker() {
        // TODO Auto-generated method stub
        //To show current date in the datepicker
        Calendar mcurrentDate = Calendar.getInstance();
        year = mcurrentDate.get(Calendar.YEAR);
        month = mcurrentDate.get(Calendar.MONTH);
        day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                month = selectedmonth + 1;
                day = selectedday;
                year = selectedyear;
                Calendar newDate = Calendar.getInstance();
                newDate.set(datepicker.getYear(), datepicker.getMonth()-1, datepicker.getDayOfMonth());
                dateText.setText("" + month + "/" + day + "/" + year + "");
            }
        }, year, month, day);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }
    public void timePicker() {
        // TODO Auto-generated method stub
        //To show current date in the datepicker
        Calendar mcurrentTime = Calendar.getInstance();
        int chour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int cminute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeText.setText( "" + selectedHour + ":" + selectedMinute);
            }
        }, chour, cminute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    private void reportSighting(){
        locationType = ((EditText) findViewById(R.id.locationType)).getText().toString();
        address = ((EditText) findViewById(R.id.address)).getText().toString();
        city = ((EditText) findViewById(R.id.city)).getText().toString();
        borough = ((EditText) findViewById(R.id.borough)).getText().toString();
        zipcode = ((EditText) findViewById(R.id.zipcode)).getText().toString();
        String s = "" + month + "/" + day + "/" + year + " "+  hour + ":" + minute;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = null;
        try
        {
            date = simpleDateFormat.parse(s);

            System.out.println("date : "+simpleDateFormat.format(date));
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ ex);
        }
        RatSighting rat = new RatSighting(date, locationType, zipcode, address, city, borough);
       

        Intent regIntent = new Intent(getApplicationContext(), RatSightingListActivity.class);
        startActivity(regIntent);

    }
}