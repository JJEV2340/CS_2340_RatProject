package org.lulz.jrat.view.impl;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

import java.io.Serializable;
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
 * RatSightingReportFilterActivity
 * Filter markers by date function
 */

public class RatSightingReportFilterActivity extends AppCompatActivity {
    @BindView(R.id.filterStartDate)
    TextView startDate;
    @BindView(R.id.filterStartTime)
    TextView startTime;
    @BindView(R.id.filterEndDate)
    TextView endDate;
    @BindView(R.id.filterEndTime)
    TextView endTime;

    private Calendar startCalendar;
    private Calendar endCalendar;
    public static boolean hitReport = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_filter);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize dates
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        updateDate();


    }

    /**
     * Updates the date and time text to match the
     * backing Calendar object
     */
    private void updateDate() {
        Date datetimeStart = startCalendar.getTime();
        String dateStart = DateFormat.getDateInstance().format(datetimeStart);
        String timeStart = DateFormat.getTimeInstance().format(datetimeStart);
        startDate.setText(dateStart);
        startTime.setText(timeStart);
        Date datetimeEnd = endCalendar.getTime();
        String dateEnd = DateFormat.getDateInstance().format(datetimeEnd);
        String timeEnd = DateFormat.getTimeInstance().format(datetimeEnd);
        endDate.setText(dateEnd);
        endTime.setText(timeEnd);
    }

    // Start time
    /**
     * When the user clicks on the edit text of date, a datepicker
     * calendar will popup for the user to enter the date
     */
    @OnClick(R.id.filterStartDate)
    void onStartDateClicked() {
        DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker picker, int year, int month, int day) {
                startCalendar.set(year, month, day);
                updateDate();
            }
        }, startCalendar.get(YEAR), startCalendar.get(MONTH), startCalendar.get(DAY_OF_MONTH));
        mDatePicker.setTitle(R.string.title_pick_date);
        mDatePicker.show();
    }

    /**
     * When the user clicks on the edit text of time, a timepicker
     * clock will popup for the user to enter the time
     */
    @OnClick(R.id.filterStartTime)
    void onStartTimeClicked() {
        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker picker, int hour, int minute) {
                startCalendar.set(HOUR_OF_DAY, hour);
                startCalendar.set(MINUTE, minute);
                startCalendar.set(SECOND, 0);
                updateDate();
            }
        }, startCalendar.get(HOUR_OF_DAY), startCalendar.get(MINUTE), false);
        mTimePicker.setTitle(R.string.title_pick_time);
        mTimePicker.show();
    }

    //End Time
    /**
     * When the user clicks on the edit text of date, a datepicker
     * calendar will popup for the user to enter the date
     */
    @OnClick(R.id.filterEndDate)
    void onEndDateClicked() {
        DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker picker, int year, int month, int day) {
                endCalendar.set(year, month, day);
                updateDate();
            }
        }, endCalendar.get(YEAR), endCalendar.get(MONTH), endCalendar.get(DAY_OF_MONTH));
        mDatePicker.setTitle(R.string.title_pick_date);
        mDatePicker.show();
    }

    /**
     * When the user clicks on the edit text of time, a timepicker
     * clock will popup for the user to enter the time
     */
    @OnClick(R.id.filterEndTime)
    void onEndTimeClicked() {
        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker picker, int hour, int minute) {
                endCalendar.set(HOUR_OF_DAY, hour);
                endCalendar.set(MINUTE, minute);
                endCalendar.set(SECOND, 0);
                updateDate();
            }
        }, endCalendar.get(HOUR_OF_DAY), endCalendar.get(MINUTE), false);
        mTimePicker.setTitle(R.string.title_pick_time);
        mTimePicker.show();
    }

    /**
     * On click of search button, pass on user inputted date to MainAciticty,
     * which then will pass the date to RatSightingMapFragment as arguments
     */
    @OnClick(R.id.searchButton)
    public void onClick(View view) {
        hitReport = true;
        Bundle argument = new Bundle();
        argument.putLong("startDate", startCalendar.getTimeInMillis());
        argument.putLong("endDate", endCalendar.getTimeInMillis());

        Intent searchIntent = new Intent(this, RatSightingReportedActivity.class);
        searchIntent.putExtra("startDate", startCalendar.getTimeInMillis());
        searchIntent.putExtra("endDate", endCalendar.getTimeInMillis());
        startActivity(searchIntent);
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
