package com.pnpdevelopers.patryk.threes.util;

/**
 * Created by patryk on 17.02.2018.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.pnpdevelopers.patryk.threes.Activities.MainActivity;

public class PreferenceManager {
    public static final String IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH_KEY";

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