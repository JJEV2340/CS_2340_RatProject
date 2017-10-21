package org.lulz.jrat.view.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * A fragment representing a single RatSighting detail screen.
 */
public class RatSightingDetailFragment extends Fragment {
    private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateTimeInstance();

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The fragment argument representing the RatSighting model that this fragment
     * represents.
     */
    public static final String ARG_SIGHTING = "rats, rats, we are the rats";

    /**
     * The id of the rat sighting this fragment is presenting.
     */
    private String itemId;

    /**
     * The RatSighting this fragment is presenting.
     */
    private RatSighting mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RatSightingDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the content specified by the fragment
        // arguments.
        itemId = getArguments().getString(ARG_ITEM_ID);
        mItem = getArguments().getParcelable(ARG_SIGHTING);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(itemId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ratsighting_detail, container, false);

        // Show the content as text in a TextView.
        if (mItem != null) {
            String date = DATE_FORMAT.format(mItem.getDate());
            ((TextView) rootView.findViewById(R.id.textViewDate)).setText(date);

            String location = String.format("%s, %s",
                    mItem.getLocation().getLatitude(), mItem.getLocation().getLongitude());
            ((TextView) rootView.findViewById(R.id.textViewLocation)).setText(location);

            ((TextView) rootView.findViewById(R.id.textViewLocationType)).setText(mItem.getLocationType());
            ((TextView) rootView.findViewById(R.id.textViewZip)).setText(mItem.getZip());
            ((TextView) rootView.findViewById(R.id.textViewAddress)).setText(mItem.getAddress());
            ((TextView) rootView.findViewById(R.id.textViewCity)).setText(mItem.getCity());
            ((TextView) rootView.findViewById(R.id.textViewBorough)).setText(mItem.getBorough());
        }

        return rootView;
    }
}
