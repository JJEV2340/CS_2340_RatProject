package org.lulz.jrat.view.impl;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.lulz.jrat.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

/**
 * RatSightingMapFilterActivity
 * Filter markers by date function
 */

public class RatSightingMapFilterActivity extends AppCompatActivity {
    @BindView(R.id.filterDate)
    TextView filterDate;
    @BindView(R.id.filterTime)
    TextView filterTime;

    private Calendar calendar;
    public static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        active = true;
        setContentView(R.layout.activity_map_filter);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize dates
        calendar = Calendar.getInstance();
        updateDate();
    }

    /**
     * Updates the date and time text to match the
     * backing Calendar object
     */
    private void updateDate() {
        Date datetime = calendar.getTime();
        String date = DateFormat.getDateInstance().format(datetime);
        String time = DateFormat.getTimeInstance().format(datetime);
        filterDate.setText(date);
        filterTime.setText(time);
    }

    /**
     * When the user clicks on the edit text of date, a datepicker
     * calendar will popup for the user to enter the date
     */
    @OnClick(R.id.filterDate)
    void onDateClicked() {
        DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker picker, int year, int month, int day) {
                calendar.set(year, month, day);
                updateDate();
            }
        }, calendar.get(YEAR), calendar.get(MONTH), calendar.get(DAY_OF_MONTH));
        mDatePicker.setTitle(R.string.title_pick_date);
        mDatePicker.show();
    }

    /**
     * When the user clicks on the edit text of time, a timepicker
     * clock will popup for the user to enter the time
     */
    @OnClick(R.id.filterTime)
    void onTimeClicked() {
        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker picker, int hour, int minute) {
                calendar.set(HOUR_OF_DAY, hour);
                calendar.set(MINUTE, minute);
                calendar.set(SECOND, 0);
                updateDate();
            }
        }, calendar.get(HOUR_OF_DAY), calendar.get(MINUTE), false);
        mTimePicker.setTitle(R.string.title_pick_time);
        mTimePicker.show();
    }

    // TODO: On click of search button, pass on user inputted date to MainAciticty,
    // TODO: which then will pass the date to RatSightingMapFragment as arguments
    @OnClick(R.id.searchButton)
    public void onClick(View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
