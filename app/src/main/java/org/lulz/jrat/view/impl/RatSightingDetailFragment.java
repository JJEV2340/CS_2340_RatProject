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

/**
 * A fragment representing a single RatSighting detail screen.
 * This fragment is either contained in a {@link RatSightingListActivity}
 * in two-pane mode (on tablets) or a {@link RatSightingDetailActivity}
 * on handsets.
 */
public class RatSightingDetailFragment extends Fragment {
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

        if (getArguments().containsKey(ARG_SIGHTING)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = getArguments().getParcelable(ARG_SIGHTING);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getAddress());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ratsighting_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.ratsighting_detail)).setText(mItem.getLocation().toString());
        }

        return rootView;
    }
}
