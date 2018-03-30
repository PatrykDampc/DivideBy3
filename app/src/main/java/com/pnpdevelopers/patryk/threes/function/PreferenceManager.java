package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.content.SharedPreferences;

import com.pnpdevelopers.patryk.threes.util.MyApplication;

public class PreferenceManager {
    public static final String PREFERENCES_KEY = "Prefs";
    public static final String HIGH_SCORE_KEY = "HIGH_SCORE_KEY_BALANCED";
    public static final String MUSIC_KEY = "MUSIC_KEY";
    public static final String IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH_KEY";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context = MyApplication.getAppContext();

    public PreferenceManager() {
        prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public boolean isMusicOn(){
        return prefs.getBoolean(MUSIC_KEY, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }
    public SharedPreferences.Editor getEditor() {
        return editor;
    }
    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
}