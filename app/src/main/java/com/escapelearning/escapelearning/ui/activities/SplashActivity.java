package com.escapelearning.escapelearning.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.escapelearning.escapelearning.R;

public class SplashActivity extends AppCompatActivity {
    private static final int TIME_TO_WAIT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, ChooseRoleActivity.class);
            startActivity(intent);
            finish();
        }, TIME_TO_WAIT);
    }
}