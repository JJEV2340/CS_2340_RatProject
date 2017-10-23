package org.lulz.jrat.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.lulz.jrat.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.OnClick;

/**
 * RatSightingMapFragment
 * Fragment class that controls the google map
 */
public class RatSightingMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private long startLong;
    private long endLong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RatSightingMapFilterActivity.hitSearch) {
            startLong = getArguments().getLong("startDate");
            endLong = getArguments().getLong("endDate");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ratsighting_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button fab = (Button) view.findViewById(R.id.filteroption);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, RatSightingMapFilterActivity.class);
                startActivity(intent);
            }
        });
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

        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        // Center the map on NYC
        LatLng newyork = new LatLng(40.7831, -73.9712);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newyork, 10.0f));

        // Places a marker for every data in the database
        // TODO: implement a way to restrict the marker placement using date.after()
        // As of now, each marker only displays each data's key
        if (RatSightingMapFilterActivity.hitSearch) {
            FirebaseFirestore.getInstance()
                    .collection("sightings")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    if (document.get("location") != null
                                            && document.get("date") != null) {
                                        long docDateLong = ((Date) document.get("date")).getTime();
                                        if (docDateLong >= startLong && docDateLong <= endLong) {
                                            LatLng singleMaker = new LatLng(document.getGeoPoint("location").getLatitude(),
                                                    document.getGeoPoint("location").getLongitude());
                                            mMap.addMarker(new MarkerOptions().position(singleMaker).title(document.getId()));
                                        }
                                    }
                                }
                            } else {
                                // Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }
}
