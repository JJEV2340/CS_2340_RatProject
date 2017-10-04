package org.lulz.jrat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import org.lulz.jrat.R;
import org.lulz.jrat.model.impl.RatSighting;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RatSighting data = (RatSighting) getIntent().getSerializableExtra("data");
        TextView id = findViewById(R.id.textView2);
        id.setText("Unique Key: " + data.getId());
        TextView date = findViewById(R.id.textView3);
        date.setText("Created Date: " + data.getDate());
        TextView type = findViewById(R.id.textView4);
        type.setText("Location Type: " + data.getLocType());
        TextView zip = findViewById(R.id.textView5);
        zip.setText("Incident Zip: " + data.getZip());
        TextView addr = findViewById(R.id.textView6);
        addr.setText("Incident Address: " + data.getAddr());
        TextView city = findViewById(R.id.textView7);
        city.setText("City: "+ data.getCity());
        TextView borough = findViewById(R.id.textView8);
        borough.setText("Borough: " +data.getBorough());
        TextView lat = findViewById(R.id.textView9);
        lat.setText("Latitude: "+data.getLat());
        TextView lon = findViewById(R.id.textView10);
        lon.setText("Longitude: "+data.getLon());
/*//    Unique Key
        Created Date
        Location Type
        Incident Zip
        Incident Address
        City
                Borough
        Latitude
                Longitude*/
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
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
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
