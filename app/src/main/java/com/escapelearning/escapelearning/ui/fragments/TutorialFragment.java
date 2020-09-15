package com.escapelearning.escapelearning.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.escapelearning.escapelearning.R;


public class TutorialFragment extends Fragment {
    private int imageDrawableId;
    private int titleStringId;

    public TutorialFragment() {
    }

    public TutorialFragment(int fragmentPosition, String role) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", fragmentPosition);
        bundle.putString("role", role);
        setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int fragmentPosition = 0;
        String role = "student";

        if (savedInstanceState != null) {
            fragmentPosition = savedInstanceState.getInt("position");
            role = savedInstanceState.getString("role", "student");
        }

        switch (role) {
            case "student":
                switch (fragmentPosition) {
                    case 0:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                    case 1:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                    case 2:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                }
                break;
            case "teacher":
                switch (fragmentPosition) {
                    case 0:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                    case 1:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                    case 2:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                }
                break;
            case "parent":
                switch (fragmentPosition) {
                    case 0:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                    case 1:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                    case 2:
                        imageDrawableId = R.drawable.ic_launcher_background;
                        titleStringId = R.string.example_text;
                        break;
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(imageDrawableId);

        TextView title = view.findViewById(R.id.title);
        title.setText(titleStringId);
    }
}