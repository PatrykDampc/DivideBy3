package com.example.patryk.divideby3.util;

/**
 * Created by patryk on 17.02.2018.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.example.patryk.divideby3.presenter.MainActivity;

public class PreferenceManager {
    private static final String IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH_KEY";

   private SharedPreferences prefs;
   private SharedPreferences.Editor editor;
   private Context context;

    public PreferenceManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}