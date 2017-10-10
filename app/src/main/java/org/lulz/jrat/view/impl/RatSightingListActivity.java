package org.lulz.jrat.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.*;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import org.lulz.jrat.R;

import org.lulz.jrat.model.impl.RatSighting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * An activity representing a list of RatSightings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RatSightingDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RatSightingListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratsighting_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.ratsighting_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.ratsighting_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
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
                        Bundle arguments = new Bundle();
                        arguments.putString(RatSightingDetailFragment.ARG_ITEM_ID, id);
                        arguments.putParcelable(RatSightingDetailFragment.ARG_SIGHTING, model);
                        if (mTwoPane) {
                            RatSightingDetailFragment fragment = new RatSightingDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.ratsighting_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, RatSightingDetailActivity.class);
                            intent.putExtra(RatSightingDetailActivity.ARG_BUNDLE, arguments);

                            context.startActivity(intent);
                        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
