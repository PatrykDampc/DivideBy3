package com.example.patryk.divideby3.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.patryk.divideby3.R;
import com.example.patryk.divideby3.util.PreferenceManager;

/**
 * Created by patryk on 19.02.2018.
 */

public class SplashActivity extends AppCompatActivity {
    private PreferenceManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PreferenceManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            startActivity(new Intent(this, StartActivity.class));
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            finish();
        }
        startActivity(new Intent(this, TutorialActivity.class));
        finish();
    }

}