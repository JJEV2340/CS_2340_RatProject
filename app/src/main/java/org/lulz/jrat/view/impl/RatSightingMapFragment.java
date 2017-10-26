package org.lulz.jrat.view.impl;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.*;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

import java.util.Calendar;
import java.util.Date;

/**
 * RatSightingMapFragment
 * Fragment class that controls the google map
 */
public class RatSightingMapFragment extends Fragment implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "Map Fragment";
    private GoogleMap mMap;
    private boolean filtered;
    private Calendar startDate;
    private Calendar endDate;
    private ListenerRegistration registration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ratsighting_map, container, false);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Center the map on NYC
        LatLng newYork = new LatLng(40.7831, -73.9712);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newYork, 10.0f));

        // Places a marker for filtered data in the database
        // As of now, each marker only displays each data's key
        Query query = FirebaseFirestore.getInstance()
                .collection("sightings")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(100);
        if (filtered) {
            query = query.whereGreaterThanOrEqualTo("date", startDate.getTime())
                    .whereLessThanOrEqualTo("date", endDate.getTime());
        }
        registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
            }
        });
        registration.remove();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        RatSighting sighting = document.toObject(RatSighting.class);
                        GeoPoint geoPoint = sighting.getLocation();
                        if (geoPoint != null) {
                            LatLng singleMaker = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(singleMaker).title(document.getId()));
                        }
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void updateMap() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    this,
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DAY_OF_MONTH),
                    endDate.get(Calendar.YEAR),
                    endDate.get(Calendar.MONTH),
                    endDate.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getFragmentManager(), "Date Range Picker");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        filtered = true;
        startDate.set(year, monthOfYear, dayOfMonth);
        endDate.set(yearEnd, monthOfYearEnd, dayOfMonthEnd);
    }
}
