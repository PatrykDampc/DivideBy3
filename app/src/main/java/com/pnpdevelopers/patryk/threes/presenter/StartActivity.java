package com.pnpdevelopers.patryk.threes.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.util.PreferenceManager;
import com.pnpdevelopers.patryk.threes.util.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button startButton;
    private Button tutorialButton;
    private TextView highScoreViewStart;
    private TextView scoreViewStart;
    private TextView numberViewStart;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //setup views
        highScoreViewStart = findViewById(R.id.highScoreTextViewStartActivityID);
        scoreViewStart = findViewById(R.id.startAcvityScoreViewID);
        numberViewStart = findViewById(R.id.startActivityNumberViewID);
        startButton = findViewById(R.id.playButtonID);
        tutorialButton = findViewById(R.id.tutorialButtonID);
       // MobileAds.initialize(this,"ca-app-pub-9589942427963055~6195596040");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("4EE1CC4EF1B48EE44234994B3C43D55").build();
        adRequest.isTestDevice(this);
        adView.loadAd(adRequest);


        //reading saved high score
        prefs = getSharedPreferences(MainActivity.PREFERENCES, MODE_PRIVATE);
        editor = prefs.edit();
        highScoreViewStart.setText(this.getString(R.string.high_score) +" "+ String.valueOf(prefs.getInt(MainActivity.HIGH_SCORE, 0)));
        //receiving scores from lost game session
        Intent intent = getIntent();
        int number = intent.getIntExtra("numberKey", 0);
        String score = intent.getStringExtra("scoreKey");
        Utils.printLostMessage(number, score, numberViewStart, scoreViewStart, startButton, this);
        scoreViewStart.setText(this.getString(R.string.your_score) +" "+ score);

        startButton.setOnClickListener(this);
        tutorialButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.playButtonID:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.tutorialButtonID:
                editor.putBoolean(PreferenceManager.IS_FIRST_TIME_LAUNCH, true);
                editor.apply();
                startActivity(new Intent(this, TutorialActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utils.ifHasFocus(hasFocus, this);
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
