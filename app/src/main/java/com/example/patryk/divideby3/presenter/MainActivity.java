package com.example.patryk.divideby3.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.divideby3.R;
import com.example.patryk.divideby3.model.CustomTimer;
import com.example.patryk.divideby3.util.Utils;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PREFERENCES = "Prefs";
    public static final String HIGH_SCORE = "HIGH_SCORE_KEY";
    //regular variables
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;
    private int highScore;
    private int scoreCount = 0;
    private int i =1;
    private CustomTimer loop;
    private int progressStatus;
    private int progressScope = 10;
    private  Vibrator vibe;
    private int randomNumber;
    private Random random = new Random();
    private int scopeMin;
    private int scopeMax;
    //Views
    private ConstraintLayout layout;
    private TextSwitcher textSwitcher;
    private TextView scoreView;
    private TextView highScoreView;
    private ProgressBar regresBar;
    private ProgressBar progressBar;
    private TextView nextLevel;
    //Game controls
    private int time = 2500;
    private int timeDecreaseValue = 500;
    private int timeDecreaseLevel = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //Views setup
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        layout = findViewById(R.id.mainActivityLayoutID);
        textSwitcher = findViewById(R.id.numberTextSwitcherID);
        scoreView = findViewById(R.id.scoreID);
        highScoreView = findViewById(R.id.highScoreTextViewID);
        regresBar = findViewById(R.id.regresBar);
        progressBar = findViewById(R.id.progressBarID);
        nextLevel = findViewById(R.id.nextLevelID);
        //read High Score from Shared Preferences
        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt(HIGH_SCORE, 0);
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));
        //Set up main game info & controls
        progressBar.setMax(progressScope);
        Utils.textSwitcherConfiguration(textSwitcher, MainActivity.this);
        layout.setOnClickListener(MainActivity.this);
        loop = gameLoop(time).start();

    }

    public CustomTimer gameLoop(int speedValue){

        return new CustomTimer(speedValue, speedValue) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("onTick: ","ok");
                if(i <= 10) {
                    scopeMin = 3;
                    scopeMax = 100;
                } else if (i > 10 && i <=25){
                    scopeMin = 49;
                    scopeMax = 200;
                    progressScope = 15;
                } else if (i > 25 && i <=45){
                    scopeMin = 120;
                    scopeMax = 300;
                    progressScope = 20;
                } else if (i > 45 && i <=80){
                    scopeMin = 390;
                    scopeMax = 620;
                    progressScope = 35;
                } else {
                    progressBar.setVisibility(View.GONE);
                    nextLevel.setVisibility(View.GONE);
                }

                randomNumber = scopeMin + random.nextInt(scopeMax - scopeMin +1);
                textSwitcher.setText(String.valueOf(randomNumber));

                if (progressBar.getProgress() == progressBar.getMax()) {
                    Toast.makeText(MainActivity.this,"Level Up!", Toast.LENGTH_SHORT).show();
                    progressStatus = 0;
                    progressBar.setProgress(progressStatus);
                    progressBar.setMax(progressScope);
                }

                ObjectAnimator animation = ObjectAnimator.ofInt(regresBar, "progress", 500, 0);
                animation.setDuration(time).start();

                scoreView.setText(MainActivity.this.getText(R.string.score) +" "+ String.valueOf(scoreCount));
                if (scoreCount == highScore && scoreCount != 0) {
                    Toast.makeText(MainActivity.this, "New Record!", Toast.LENGTH_SHORT).show();
                }
                if (scoreCount > highScore) {
                    editor.putInt(HIGH_SCORE, scoreCount);
                    editor.commit();
                    highScoreView.setText(MainActivity.this.getText(R.string.high_score) +" "+ String.valueOf(scoreCount));
                }
            }

            @Override
            public void onFinish() {
                Log.d("onFinish: ","ok");
                if (!Utils.succesCondition(randomNumber)) {
                    scoreCount++;
                    regresBar.clearAnimation();
                    success();
                } else {
                   // Utils.succesCondition(randomNumber) = false;
                    backToStart();
                }
            }
        };

    }
    @Override
    public void onClick (View v){
        if (Utils.succesCondition(randomNumber)) {
            scoreCount += 2;
            regresBar.clearAnimation();
            loop.cancel();
            vibe.vibrate(50);
            success();
        } else {
            loop.cancel();
            backToStart();
        }
    }

    public void success() {
        // if (i % 10 == 0 && i <= timeDecreaseLevel) time -= timeDecreaseValue;
        vibe.vibrate(25);
        i++;
        progressBar.setProgress(progressStatus +=1);
        loop = gameLoop(time).start();
    }

    //returns to startAcvivity, contains info about last shown number and earned score
    public void backToStart(){
        Intent i = new Intent(MainActivity.this, StartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("scoreKey", String.valueOf(scoreCount));
        i.putExtra("numberKey", randomNumber);
        startActivity(i);
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, StartActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        loop.cancel();
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utils.ifHasFocus(hasFocus, this);
    }


}