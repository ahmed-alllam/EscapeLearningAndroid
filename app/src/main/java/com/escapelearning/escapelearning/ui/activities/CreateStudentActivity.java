package com.escapelearning.escapelearning.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.escapelearning.escapelearning.R;
import com.escapelearning.escapelearning.adapters.ClassroomsArrayAdapter;
import com.escapelearning.escapelearning.adapters.SchoolsArrayAdapter;
import com.escapelearning.escapelearning.data.models.AuthToken;
import com.escapelearning.escapelearning.data.models.Student;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.ui.viewmodels.AuthTokenViewModel;
import com.escapelearning.escapelearning.ui.viewmodels.ClassroomViewModel;
import com.escapelearning.escapelearning.ui.viewmodels.SchoolViewModel;
import com.escapelearning.escapelearning.ui.viewmodels.StudentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import retrofit2.HttpException;
import retrofit2.Response;

public class CreateStudentActivity extends AppCompatActivity {
    private AuthTokenViewModel authTokenViewModel;
    private StudentViewModel studentViewModel;
    private TextView errorLabel;
    private ProgressBar progressBar;

    private String username = "";
    private String password = "";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        errorLabel = findViewById(R.id.errorLabel);
        progressBar = findViewById(R.id.progressBar);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        authTokenViewModel = new ViewModelProvider(this).get(AuthTokenViewModel.class);

        studentViewModel.getStudent().observe(this, (this::processSignupResponse));
        authTokenViewModel.getToken().observe(this, (this::processLoginResponse));

        SchoolViewModel schoolViewModel = new ViewModelProvider(this).get(SchoolViewModel.class);
        ClassroomViewModel classroomViewModel = new ViewModelProvider(this).get(ClassroomViewModel.class);

        AutoCompleteTextView schoolNameEditText = findViewById(R.id.schoolNameEditText);
        AutoCompleteTextView classEditText = findViewById(R.id.classEditText);
        classEditText.setKeyListener(null);

        schoolViewModel.getSchoolsLiveData().observe(this, response -> {
            if (response.getStatus() == APIResponse.Status.SUCCESFUL && response.getitem() != null &&
                    response.getitem().size() > 0) {
                SchoolsArrayAdapter arrayAdapter = new SchoolsArrayAdapter(this, response.getitem().toArray());
                schoolNameEditText.setAdapter(arrayAdapter);
                schoolNameEditText.showDropDown();
            }
        });

        classroomViewModel.getClassroomsLiveData().observe(this, response -> {
            if (response.getStatus() == APIResponse.Status.SUCCESFUL && response.getitem() != null &&
                    response.getitem().size() > 0) {
                ClassroomsArrayAdapter arrayAdapter = new ClassroomsArrayAdapter(this, response.getitem().toArray());
                classEditText.setAdapter(arrayAdapter);
            }
        });

        schoolNameEditText.setOnItemClickListener((parent, view, position, id) -> classroomViewModel.searchClassrooms(getApplicationContext(),
                schoolNameEditText.getText().toString()));

        addTextWatcherObservable(schoolNameEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(schoolName -> schoolViewModel.searchSchools(getApplicationContext(), schoolName));
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
            String schoolName = getSchoolName();
            String schoolCode = getSchoolCode();
            String className = getClassName();
            String username = getUsername();
            String password = getPassword();

            boolean nameValid = isNameValid(name);
            boolean schoolNameValid = isNameValid(schoolName);
            boolean schoolCodeValid = isSchoolCodeValid(schoolCode);
            boolean classNameValid = isNameValid(className);
            boolean usernameValid = isUsernameValid(username);
            boolean passwordValid = isPasswordValid(password);

            if (nameValid && schoolNameValid && schoolCodeValid &&
                    classNameValid && usernameValid && passwordValid) {
                this.username = username;
                this.password = password;

                errorLabel.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                studentViewModel.signup(getApplicationContext(), name, schoolName,
                        schoolCode, className, username, password);
            } else {
                if (!nameValid)
                    setErrorMessage(R.string.invalid_name);
                else if (!schoolNameValid || !schoolCodeValid)
                    setErrorMessage(R.string.invalid_school_name);
                else if (!classNameValid)
                    setErrorMessage(R.string.invalid_class);
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

    private String getSchoolName() {
        TextView schoolNameEditText = findViewById(R.id.schoolNameEditText);
        return schoolNameEditText.getText().toString();
    }

    private String getClassName() {
        TextView classEditText = findViewById(R.id.classEditText);
        return classEditText.getText().toString();
    }

    private String getSchoolCode() {
        TextView schoolCodeEditText = findViewById(R.id.schoolCodeEditText);
        return schoolCodeEditText.getText().toString();
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

    private boolean isSchoolCodeValid(String code) {
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
        authTokenViewModel.login(getApplicationContext(), username, password, "student");
    }

    private void processSignupResponse(APIResponse<Student> response) {
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
                Intent intent = new Intent(this, StudentHomeActivity.class);
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
                else if (jsonError.has("non_field_errors"))
                    setErrorMessage(R.string.invalid_school_name);
                else
                    setErrorMessage(R.string.network_error);
            } else
                setErrorMessage(R.string.network_error);
        } catch (IOException | JSONException e) {
            setErrorMessage(R.string.network_error);
            e.printStackTrace();
        }
    }

    private Observable<String> addTextWatcherObservable(AutoCompleteTextView editText) {
        return Observable.create(emitter -> editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editText.isPerformingCompletion() && !s.toString().trim().isEmpty())
                    emitter.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
    }
}