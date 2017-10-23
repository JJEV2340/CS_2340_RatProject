package org.lulz.jrat.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * A fragment representing a list of RatSightings.
 */
public class RatSightingListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ratsighting_list, container, false);
        ButterKnife.bind(this, rootView);

        View recyclerView = rootView.findViewById(R.id.ratsighting_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        return rootView;
    }

    @OnClick(R.id.fab)
    void onAddClicked() {
        Intent regIntent = new Intent(getContext(), RatSightingAddActivity.class);
        startActivity(regIntent);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        // We limit the query to 500 so it doesn't crash.
        // FirebaseUI should have support for pagination soon:
        // https://github.com/firebase/FirebaseUI-Android/issues/17
        Query query = FirebaseFirestore.getInstance()
                .collection("sightings")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(500);

        FirestoreRecyclerOptions<RatSighting> options = new FirestoreRecyclerOptions.Builder<RatSighting>()
                .setQuery(query, RatSighting.class)
                .setLifecycleOwner(this)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<RatSighting, RatSightingHolder>(options) {
            @Override
            public void onBindViewHolder(final RatSightingHolder holder, int position, final RatSighting model) {
                final String id = getSnapshots().getSnapshot(position).getId();
                DateFormat df = SimpleDateFormat.getDateInstance();
                holder.dateView.setText(df.format(model.getDate()));
                holder.addressView.setText(model.getAddress());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // start rat sighting detail activity
                        Bundle arguments = new Bundle();
                        arguments.putString(RatSightingDetailFragment.ARG_ITEM_ID, id);
                        arguments.putParcelable(RatSightingDetailFragment.ARG_SIGHTING, model);

                        Context context = v.getContext();
                        Intent intent = new Intent(context, RatSightingDetailActivity.class);
                        intent.putExtra(RatSightingDetailActivity.ARG_BUNDLE, arguments);

                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public RatSightingHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.ratsighting_list_content, group, false);

                return new RatSightingHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
