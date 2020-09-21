package com.escapelearning.escapelearning.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class PreferencesManager {
    public static final String ROLE_PEREFERENCE = "ROLE";

    public static String getRole(Context context) {
        return getPreference(context, "ROLE", "student");
    }

    public static void setRole(Context context, String role) {
        setPreference(context, ROLE_PEREFERENCE, role);
    }

    private static String getPreference(Context context, String preference, String defaultValue) {
        SharedPreferences prefs = context.getSharedPreferences("appPreferences", Activity.MODE_PRIVATE);
        return prefs.getString(preference, defaultValue);
    }

    private static void setPreference(Context context, String preference, String value) {
        SharedPreferences prefs = context.getSharedPreferences("appPreferences", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(preference, value);
        editor.apply();
    }
}
