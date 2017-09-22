package org.lulz.jrat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.lulz.jrat.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            TextView textView = findViewById(R.id.textview_name);
            if (user.isAnonymous()) {
                textView.setText(R.string.anonymous_user);
            } else {
                textView.setText(user.getDisplayName());
            }

            TextView textView2 = findViewById(R.id.textview_email);
            textView2.setText(user.getEmail());

            TextView textView3 = findViewById(R.id.textview_phone);
            textView3.setText(user.getPhoneNumber());
        }
    }

    public void onLogoutPressed(View view) {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }
}
