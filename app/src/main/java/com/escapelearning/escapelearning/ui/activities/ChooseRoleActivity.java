package com.escapelearning.escapelearning.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.escapelearning.escapelearning.R;

public class ChooseRoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
    }

    public void onRoleButtonClicked(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        String role;

        switch (view.getId()) {
            case R.id.studentButton:
                role = "student";
                break;
            case R.id.teacherButton:
                role = "teacher";
                break;
            default:
                role = "parent";
        }

        intent.putExtra("role", role);
        startActivity(intent);
        finish();
    }
}