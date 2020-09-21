package com.escapelearning.escapelearning.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.escapelearning.escapelearning.R;
import com.escapelearning.escapelearning.ui.viewmodels.AuthTokenViewModel;
import com.escapelearning.escapelearning.utils.PreferencesManager;

public class LoginActivity extends AppCompatActivity {
    private AuthTokenViewModel authTokenViewModel;
    private String role;
    private TextView errorLabel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        role = getIntent().getStringExtra("role");
        errorLabel = findViewById(R.id.errorLabel);
        progressBar = findViewById(R.id.progressBar);

        authTokenViewModel = new ViewModelProvider(this).get(AuthTokenViewModel.class);
        authTokenViewModel.getToken().observe(this, (response -> {
            switch (response.getStatus()) {
                case SUCCESFUL:
                    Intent intent;
                    switch (role) {
                        case "student":
                        default:
                            intent = new Intent(this, StudentHomeActivity.class);
                            break;
                        case "teacher":
                            intent = new Intent(this, TeacherHomeActivity.class);
                            break;
                        case "parent":
                            intent = new Intent(this, ParentHomeActivity.class);
                    }

                    PreferencesManager.setRole(getApplicationContext(), role);
                    startActivity(intent);
                    finishAffinity();
                    break;
                case BAD_REQUEST:
                    setErrorMessage(R.string.wrong_username_or_password);
                    break;
                case NETWORK_FAILED:
                    setErrorMessage(R.string.network_error);
            }
        }));
    }

    public void onLoginClicked(View view) {
        errorLabel.setVisibility(View.INVISIBLE);

        String username = getUsername();
        String password = getPassword();

        if (isUsernameValid(username) && isPasswordValid(password)) {
            authTokenViewModel.login(getApplicationContext(), username, password, role);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            setErrorMessage(R.string.wrong_username_or_password);
        }
    }

    public void setErrorMessage(int messageID) {
        errorLabel.setVisibility(View.VISIBLE);
        errorLabel.setText(messageID);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private String getUsername() {
        TextView usernameTextView = findViewById(R.id.usernameEditText);
        return usernameTextView.getText().toString();
    }

    private String getPassword() {
        TextView passwordTextView = findViewById(R.id.passwordEditText);
        return passwordTextView.getText().toString();
    }

    private boolean isUsernameValid(String username) {
        String regexPattern = "[\\w.@+-]{8,}\\Z";
        return username.matches(regexPattern);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }
}