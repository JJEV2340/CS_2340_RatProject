package org.lulz.jrat.view.impl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.felipecsl.gifimageview.library.GifImageView;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.lulz.jrat.BuildConfig;
import org.lulz.jrat.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.grpc.internal.IoUtils;

public class WelcomeActivity extends AppCompatActivity{
    private static final int RC_SIGN_IN = 420; // wake and bake
    private FirebaseAuth mAuth;
    private GifImageView gif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        gif = (GifImageView) findViewById(R.id.gifImage);

        try {
            InputStream inputStream = getAssets().open("hamster.gif");
            byte[] bytes = IoUtils.toByteArray(inputStream);
            gif.setBytes(bytes);
            gif.startAnimation();
        } catch(IOException e) {
            e.printStackTrace();
        }

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // already logged in
            loginSuccess();
        }
    }
    /*class RetrieveByteArray extends AsyncTask<String, Void, byte[]> {
        @Override
        protected byte[] doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200) {
                int nRead;
                byte[] data = new byte[10240];
                while((nRead==in.read(data, 0, data.length))!= -1)
                {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                return buffer.toByteArray();
                }
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            gif.setBytes(bytes);
        }
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                loginSuccess();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(R.string.unknown_error);
                    return;
                }
            }

            showSnackbar(R.string.unknown_sign_in_response);
        }
    }

    /**
     * Handler for the login button
     *
     * @param view the button
     */
    public void onLoginPressed(View view) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .build(),
                RC_SIGN_IN);
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
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                loginSuccess();
            }
        });
    }

    /**
     * Displays the main activity after logging in.
     */
    private void loginSuccess() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    @MainThread
    private void showSnackbar(@StringRes int stringRes) {
        Snackbar.make(findViewById(android.R.id.content), stringRes, Snackbar.LENGTH_SHORT).show();
    }
}
