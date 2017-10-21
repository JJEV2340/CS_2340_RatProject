package org.lulz.jrat.view.impl;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

import java.text.DateFormat;
import java.util.Date;

import static java.util.Calendar.*;

/**
 * An activity representing adding of Rat Sighting. This activity
 * allows the user to report a rat sighting and add it to the
 * database.
 */
public class RatSightingAddActivity extends AppCompatActivity {
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.editTextLocationType)
    EditText locationType;
    @BindView(R.id.editTextAddress)
    EditText address;
    @BindView(R.id.editTextCity)
    EditText city;
    @BindView(R.id.editTextBorough)
    EditText borough;
    @BindView(R.id.editTextZip)
    EditText zip;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratsighting_add);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
        textViewDate.setText(date);
        textViewTime.setText(time);
    }

    /**
     * When the user clicks on the edit text of date, a datepicker
     * calendar will popup for the user to enter the date
     */
    @OnClick(R.id.textViewDate)
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
    @OnClick(R.id.textViewTime)
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

    /**
     * Takes the filled in edit text values and convert the datetime into
     * Date and stores all of it ito the database
     */
    @OnClick(R.id.report_button)
    void onReportClicked() {
        RatSighting rat = new RatSighting();
        rat.setDate(calendar.getTime());
        rat.setLocationType(locationType.getText().toString());
        rat.setAddress(address.getText().toString());
        rat.setCity(city.getText().toString());
        rat.setBorough(borough.getText().toString());
        rat.setZip(zip.getText().toString());

        // give rat a test GeoPoint
        // TODO: once google map is implemented, implement real GeoPoint
        double lat = Math.random() * 90;
        double lng = Math.random() * 90;
        GeoPoint testPoint = new GeoPoint(lat, lng);
        rat.setLocation(testPoint);

        // add the RatSighting to the database
        FirebaseFirestore.getInstance().collection("sightings").add(rat);
        finish();
    }

    /**
     * Cancels the rat report
     */
    @OnClick(R.id.cancel_button)
    void onCancelPressed() {
        finish();
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
