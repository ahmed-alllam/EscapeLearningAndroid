package com.escapelearning.escapelearning.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.escapelearning.escapelearning.R;
import com.escapelearning.escapelearning.data.models.AuthToken;
import com.escapelearning.escapelearning.data.models.Parent;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.ui.viewmodels.AuthTokenViewModel;
import com.escapelearning.escapelearning.ui.viewmodels.ParentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

public class CreateParentActivity extends AppCompatActivity {
    private AuthTokenViewModel authTokenViewModel;
    private ParentViewModel parentViewModel;
    private TextView errorLabel;
    private ProgressBar progressBar;

    private String username = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parent);

        errorLabel = findViewById(R.id.errorLabel);
        progressBar = findViewById(R.id.progressBar);

        parentViewModel = new ViewModelProvider(this).get(ParentViewModel.class);
        authTokenViewModel = new ViewModelProvider(this).get(AuthTokenViewModel.class);

        parentViewModel.getParent().observe(this, (this::processSignupResponse));
        authTokenViewModel.getToken().observe(this, (this::processLoginResponse));
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("username", username);
        outState.putString("password", password);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        username = savedInstanceState.getString("username");
        password = savedInstanceState.getString("password");
    }

    public void onSignupClicked(View view) {
        errorLabel.setVisibility(View.INVISIBLE);

        if (username.isEmpty() && password.isEmpty()) {
            String name = getName();
            String childCode = getChildCode();
            String username = getUsername();
            String password = getPassword();

            boolean nameValid = isNameValid(name);
            boolean childCodeValid = isChildCodeValid(childCode);
            boolean usernameValid = isUsernameValid(username);
            boolean passwordValid = isPasswordValid(password);

            if (nameValid && childCodeValid &&
                    usernameValid && passwordValid) {
                this.username = username;
                this.password = password;

                errorLabel.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                parentViewModel.signup(getApplicationContext(), name, childCode,
                        username, password);
            } else {
                if (!nameValid)
                    setErrorMessage(R.string.invalid_name);
                else if (!childCodeValid)
                    setErrorMessage(R.string.invalid_child_code);
                else if (!usernameValid)
                    setErrorMessage(R.string.invalid_username);
                else
                    setErrorMessage(R.string.invalid_password);
            }
        } else {
            login();
        }
    }

    public void setErrorMessage(int messageID) {
        errorLabel.setVisibility(View.VISIBLE);
        errorLabel.setText(messageID);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private String getName() {
        TextView nameEditText = findViewById(R.id.nameEditText);
        return nameEditText.getText().toString();
    }

    private String getChildCode() {
        TextView childCodeEditText = findViewById(R.id.childCodeEditText);
        return childCodeEditText.getText().toString();
    }

    private String getUsername() {
        TextView usernameEditText = findViewById(R.id.usernameEditText);
        return usernameEditText.getText().toString();
    }

    private String getPassword() {
        TextView passwordEditText = findViewById(R.id.passwordEditText);
        return passwordEditText.getText().toString();
    }

    private boolean isNameValid(String name) {
        return name.length() >= 3;
    }

    private boolean isChildCodeValid(String code) {
        return !code.isEmpty();
    }

    private boolean isUsernameValid(String username) {
        String regexPattern = "[\\w.@+-]{8,}\\Z";
        return username.matches(regexPattern);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private void login() {
        authTokenViewModel.login(getApplicationContext(), username, password, "parent");
    }

    private void processSignupResponse(APIResponse<Parent> response) {
        switch (response.getStatus()) {
            case SUCCESFUL:
                login();
                break;
            case BAD_REQUEST:
                parseErrorBody(((HttpException) response.getError()).response());
                break;
            case NETWORK_FAILED:
                setErrorMessage(R.string.network_error);
        }

        if (response.getStatus() != APIResponse.Status.SUCCESFUL) {
            username = "";
            password = "";
        }
    }

    private void processLoginResponse(APIResponse<AuthToken> response) {
        switch (response.getStatus()) {
            case SUCCESFUL:
                Intent intent = new Intent(this, ParentHomeActivity.class);
                startActivity(intent);
                finishAffinity();
                break;
            case NETWORK_FAILED:
            default:
                setErrorMessage(R.string.network_error);
        }
    }

    private void parseErrorBody(@Nullable Response<?> errorResponse) {
        try {
            if (errorResponse != null && errorResponse.errorBody() != null) {
                JSONObject jsonError = new JSONObject(errorResponse.errorBody().string());
                if (jsonError.has("username"))
                    setErrorMessage(R.string.username_taken);
                else if (jsonError.has("child_code"))
                    setErrorMessage(R.string.invalid_child_code);
                else
                    setErrorMessage(R.string.network_error);
            } else
                setErrorMessage(R.string.network_error);
        } catch (IOException | JSONException e) {
            setErrorMessage(R.string.network_error);
            e.printStackTrace();
        }
    }
}