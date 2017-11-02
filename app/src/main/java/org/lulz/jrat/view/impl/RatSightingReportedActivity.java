package org.lulz.jrat.view.impl;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

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
 * RatSightingReportedActivity
 * Draws a graph of the number of sightings in months
 */

public class RatSightingReportedActivity extends AppCompatActivity {
    private static final String TAG = "Report";
    private Date startDate;
    private Date endDate;

    private int jan = 0;
    private int feb = 0;
    private int mar =0;
    private int apr = 0;
    private int may = 0;
    private int june = 0;
    private int july = 0;
    private int aug = 0;
    private int sep = 0;
    private int oct=0;
    private int nov=0;
    private int dec=0;

    public static boolean hitReport = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratsighting_graph);

        String startMonth = "";
        final String endMonth = "";

        startDate = new Date(getIntent().getLongExtra("startDate", 0));
        endDate = new Date(getIntent().getLongExtra("endDate", 0));

        System.out.println("IN CREADY!#jKLDSFIJ");
        Query query = FirebaseFirestore.getInstance()
                .collection("sightings")
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1000);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        RatSighting sighting = document.toObject(RatSighting.class);
                        int month = sighting.getDate().getMonth();
                        if(month == 0)
                            jan++;
                        else if(month == 1)
                            feb++;
                        else if(month==2)
                            mar++;
                        else if(month==3)
                            apr++;
                        else if(month==4)
                            may++;
                        else if(month==5)
                            june++;
                        else if(month==6)
                            july++;
                        else if(month==7)
                            aug++;
                        else if(month==8)
                            sep++;
                        else if(month==9)
                            oct++;
                        else if(month==10)
                            nov++;
                        else if(month==11)
                            dec++;
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
                boolean found = false;
                GraphView graph = (GraphView) findViewById(R.id.graph);
                DataPoint[] values = new DataPoint[endDate.getMonth() - startDate.getMonth()];
                int j=0;
                for(int i=1; i<=12; i++)
                {
                    if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 1)
                    {
                        values[j] = new DataPoint(i, jan);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 2)
                    {
                        values[j] = new DataPoint(i, feb);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 3)
                    {
                        values[j] = new DataPoint(i, mar);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 4)
                    {
                        values[j] = new DataPoint(i, apr);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 5)
                    {
                        values[j] = new DataPoint(i, may);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 6)
                    {
                        values[j] = new DataPoint(i, june);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 7)
                    {
                        values[j] = new DataPoint(i, july);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 8)
                    {
                        values[j] = new DataPoint(i, aug);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 9)
                    {
                        values[j] = new DataPoint(i, sep);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 10)
                    {
                        values[j] = new DataPoint(i, oct);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 11)
                    {
                        values[j] = new DataPoint(i, nov);
                        j++;
                    }
                    else if(i > startDate.getMonth() && i <=endDate.getMonth() && i == 12)
                    {
                        values[j] = new DataPoint(i, dec);
                        j++;
                    }

                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);
                graph.addSeries(series);

                graph.setTitle("Number of Sightings Each Month");
                graph.getGridLabelRenderer().setNumHorizontalLabels(endDate.getMonth() - startDate.getMonth());
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            if(value == 1)
                                return "Jan";
                            else if(value == 2)
                                return "Feb";
                            else if (value ==3)
                                return "Mar";
                            else if(value==4)
                                return "Apr";
                            else if(value==5 )
                                return "May";
                            else if(value==6)
                                return "June";
                            else if(value==7 )
                                return "July";
                            else if(value==8)
                                return "Aug";
                            else if(value==9)
                                return "Sep";
                            else if(value==10)
                                return "Oct";
                            else if(value==11)
                                return "Nov";
                            else if(value==12)
                                return "Dec";

                            return null;
                        } else {
                            // show currency for y values
                            return super.formatLabel(value, isValueX);
                        }
                    }
                });

            }
        });


        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
        Button fab = (Button) findViewById(R.id.filteroption);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, RatSightingReportFilterActivity.class);
                startActivity(intent);
            }
        });
    }
}
