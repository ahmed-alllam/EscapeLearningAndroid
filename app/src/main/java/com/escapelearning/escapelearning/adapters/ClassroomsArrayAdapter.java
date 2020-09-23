package com.escapelearning.escapelearning.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.escapelearning.escapelearning.data.models.Classroom;

public class ClassroomsArrayAdapter extends ArrayAdapter<Object> {
    private LayoutInflater layoutInflater;

    public ClassroomsArrayAdapter(@NonNull Context context, Object[] classrooms) {
        super(context, android.R.layout.simple_list_item_1, classrooms);

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        Classroom classroom = (Classroom) getItem(position);

        TextView textView = (TextView) convertView;

        if (classroom != null)
            textView.setText(classroom.getName());

        return convertView;
    }
}
