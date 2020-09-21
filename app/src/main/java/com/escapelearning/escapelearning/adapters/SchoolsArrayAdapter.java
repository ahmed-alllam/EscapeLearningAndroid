package com.escapelearning.escapelearning.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.escapelearning.escapelearning.data.models.School;

public class SchoolsArrayAdapter extends ArrayAdapter<Object> {
    private LayoutInflater layoutInflater;

    public SchoolsArrayAdapter(@NonNull Context context, Object[] schools) {
        super(context, android.R.layout.simple_list_item_1, schools);

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        System.out.println("ahmed in get view");

        if (convertView == null)
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        School school = (School) getItem(position);

        System.out.println("ahmed + " + school);
        TextView textView = (TextView) convertView;

        if (school != null)
            textView.setText(school.getName());

        return convertView;
    }
}
