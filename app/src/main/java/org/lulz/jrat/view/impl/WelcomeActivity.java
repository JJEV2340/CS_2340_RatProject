package org.lulz.jrat.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.lulz.jrat.BuildConfig;
import org.lulz.jrat.R;

public class WelcomeActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 420; // wake and bake
    private FirebaseAuth mAuth;

    @BindView(android.R.id.content)
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // already logged in
            loginSuccess();
        }
    }

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
    @OnClick(R.id.button_sign_in)
    void onLoginPressed(View view) {
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
    @OnClick(R.id.button_sign_in_guest)
    void onLoginGuestPressed(final View view) {
        mAuth.signInAnonymously().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showSnackbar(R.string.anonymous_sign_in_failed);
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
        Snackbar.make(rootView, stringRes, Snackbar.LENGTH_SHORT).show();
    }
}
