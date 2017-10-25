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
import com.google.firebase.firestore.*;
import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

import java.util.Date;

/**
 * RatSightingMapFragment
 * Fragment class that controls the google map
 */
public class RatSightingMapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "Map Fragment";
    private GoogleMap mMap;
    private Date startDate;
    private Date endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RatSightingMapFilterActivity.hitSearch) {
            startDate = new Date(getArguments().getLong("startDate"));
            endDate = new Date(getArguments().getLong("endDate"));
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

        // Center the map on NYC
        LatLng newyork = new LatLng(40.7831, -73.9712);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newyork, 10.0f));

        // Places a marker for filtered data in the database
        // As of now, each marker only displays each data's key
        if (RatSightingMapFilterActivity.hitSearch) {
            Query query = FirebaseFirestore.getInstance()
                    .collection("sightings")
                    .whereGreaterThanOrEqualTo("date", startDate)
                    .whereLessThanOrEqualTo("date", endDate)
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(500);
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
    }
}
