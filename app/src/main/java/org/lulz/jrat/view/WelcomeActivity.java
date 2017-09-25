package org.lulz.jrat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import org.lulz.jrat.R;

import java.util.Arrays;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {
                    // user logged in
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                    mAuth.removeAuthStateListener(this);
                }
            }
        });
    }

    /**
     * Handler for the login button
     *
     * @param view the button
     */
    public void onLoginPressed(View view) {
        startActivity(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        //.setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .build());
    }

    /**
     * Handler for the anonymous login button
     *
     * @param view the button
     */
    public void onLoginGuestPressed(final View view) {
        mAuth.signInAnonymously().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(view, "Unable to sign in anonymously.", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
