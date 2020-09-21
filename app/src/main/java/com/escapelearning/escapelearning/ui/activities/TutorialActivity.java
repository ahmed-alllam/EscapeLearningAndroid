package com.escapelearning.escapelearning.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.escapelearning.escapelearning.R;
import com.escapelearning.escapelearning.adapters.TutorialViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TutorialActivity extends AppCompatActivity {
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        role = getIntent().getStringExtra("role");

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new TutorialViewPagerAdapter(this, role));
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
        }).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 10, 0);
            tab.requestLayout();
        }
    }

    public void onSignupClicked(View view) {
        Intent intent;

        switch (role) {
            case "student":
            default:
                intent = new Intent(this, CreateStudentActivity.class);
                break;
            case "teacher":
                intent = new Intent(this, CreateTeacherActivity.class);
                break;
            case "parent":
                intent = new Intent(this, CreateParentActivity.class);
                break;
        }

        startActivity(intent);
    }

    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("role", role);
        startActivity(intent);
        finish();
    }
}