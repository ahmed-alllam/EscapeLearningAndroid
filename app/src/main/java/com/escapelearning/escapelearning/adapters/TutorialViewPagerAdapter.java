package com.escapelearning.escapelearning.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.escapelearning.escapelearning.ui.fragments.TutorialFragment;

public class TutorialViewPagerAdapter extends FragmentStateAdapter {
    private static final int PAGES_NUM = 3;
    private String role;

    public TutorialViewPagerAdapter(FragmentActivity fa, String role) {
        super(fa);
        this.role = role;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TutorialFragment(position, role);
    }

    @Override
    public int getItemCount() {
        return PAGES_NUM;
    }
}
