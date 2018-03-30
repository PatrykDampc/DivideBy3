package com.pnpdevelopers.patryk.threes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pnpdevelopers.patryk.threes.function.PreferenceManager;

/**
 * Created by patryk on 19.02.2018.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PreferenceManager preferenceManager = new PreferenceManager();
        //MobileAds.initialize(this,"ca-app-pub-9589942427963055~6195596040");
        if (!preferenceManager.isFirstTimeLaunch()) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, TutorialActivity.class));
            finish();
        }
    }
}