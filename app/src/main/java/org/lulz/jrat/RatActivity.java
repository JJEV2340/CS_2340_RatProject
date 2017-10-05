package org.lulz.jrat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.lulz.jrat.jrat.R;

import static org.lulz.jrat.SystemInformation.ratTitles;
import static org.lulz.jrat.SystemInformation.sightingList;

/**
 * Created by Vyas on 10/4/2017.
 */

public class RatActivity extends AppCompatActivity implements Authentication{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratcaseslist);

        // This is such garbage, I didn't want to waste more time trying to figure out why
        // getting the text from the listview wasn't working as I wanted it to - If we
        // go with this implementation, someone should definitely fix this.
        final String[] IDList = populateListView();
        final DataBaseHelper myDBHelper = new DataBaseHelper(this);

        ListView list_view = (ListView) findViewById(R.id.listViewMain);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String keyVal = IDList[position].replace("Sighting ID: ", "");

                RatSighting newSighting = generateSighting(keyVal);

                AlertDialog alertDialog = new AlertDialog.Builder(RatActivity.this).create();
                alertDialog.setTitle("More Information");
                alertDialog.setMessage(newSighting.toString());
                alertDialog.show();
            }
        });


    }

    private String[] populateListView() {

        DataBaseHelper myDBHelper = new DataBaseHelper(this);
        myDBHelper.openDataBase();
        String[] IDList = myDBHelper.getUniqueIDs();
        myDBHelper.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.items, IDList);
        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setAdapter(adapter);
        return IDList;
    }

    private RatSighting generateSighting(String keyVal) {
        DataBaseHelper myDBHelper = new DataBaseHelper(this);
        myDBHelper.openDataBase();
        RatSighting newSighting = myDBHelper.generateSighting(keyVal);
        myDBHelper.close();
        return newSighting;
    }

}
