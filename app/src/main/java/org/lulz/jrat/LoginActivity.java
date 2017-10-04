package org.lulz.jrat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.CursorLoader;
import android.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import org.lulz.jrat.jrat.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements Authentication{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Start up my activity login view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = null;

        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Login Attempt");
                if(!validate()) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.login_form), "Login Unsucessful", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), AppHome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        Button cancelButton = null;
        cancelButton = (Button) findViewById(R.id.email_sign_in_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                navigateUpTo(regIntent);
                finish();
            }
        });

    }


    public boolean validate() {
        String username = ((AutoCompleteTextView) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();


        // Eventually we can implement more sophisticated ideas here for
        // authentication and security
        // use HashMap in Authentication interface to check
        // if user is registered 09/27JIN
        if(authMap.containsKey(username) && authMap.get(username).getUserPW().equals(password)) {
            return true;
        }
        return false;
    }

}