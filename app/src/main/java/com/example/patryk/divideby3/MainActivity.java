package com.example.patryk.divideby3;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PREFERENCES = "Prefs";
    public static final String HIGH_SCORE = "HIGH_SCORE_KEY";
    //regular variables
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;
    private int highScore;
    private int scoreCount = 0;
    private int i =1;
    private RandomNumber randomNumber;
    private CountDownTimer loop;
    private int progressStatus;
    private int progressScope = 10;
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

        layout = (ConstraintLayout) findViewById(R.id.mainActivityLayoutID);
        textSwitcher = findViewById(R.id.numberTextSwitcherID);
        scoreView = findViewById(R.id.scoreID);
        highScoreView = findViewById(R.id.highScoreTextViewID);
        regresBar = findViewById(R.id.regresBar);
        progressBar = findViewById(R.id.progressBarID);
        nextLevel = findViewById(R.id.nextLevelID);

        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt(HIGH_SCORE, 0);
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));

        Utils.textSwitcherConfiguration(textSwitcher, MainActivity.this);
        layout.setOnClickListener(MainActivity.this);
        loop = gameLoop(time).start();
        progressBar.setMax(progressScope);
    }

    public CountDownTimer gameLoop(int speedValue){

        return new CountDownTimer(speedValue, speedValue) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(i <= 10) {
                    randomNumber = new RandomNumber();
                } else if (i > 10 && i <=25){
                    randomNumber = new RandomNumber(40,180); //40,180
                    progressScope = 15;
                } else if (i > 25 && i <=45){
                    randomNumber = new RandomNumber(70,300); //70,300
                    progressScope = 20;
                } else if (i > 45 && i <=80){
                    randomNumber = new RandomNumber(200,550); //200,550
                    progressScope = 35;
                } else {
                    progressBar.setVisibility(View.GONE);
                    nextLevel.setVisibility(View.GONE);
                }
                if (progressBar.getProgress() == progressBar.getMax()) {
                    Toast.makeText(MainActivity.this,"Level Up!", Toast.LENGTH_SHORT).show();
                    progressStatus = 0;
                    progressBar.setProgress(progressStatus);
                    progressBar.setMax(progressScope);
                }




                textSwitcher.setText(randomNumber.getRanNumString());
                if(i%2 == 0){

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
                if (!randomNumber.getWinCondition()) {
                    scoreCount++;
                    regresBar.clearAnimation();
                    success();
                } else {
                    randomNumber.setWinCondition(false);
                    backToStart();
                }
            }
        };

    }
    @Override
    public void onClick (View v){
        if (randomNumber.getWinCondition()) {
            scoreCount += 2;
            regresBar.clearAnimation();
            loop.cancel();
            success();
        } else {
            loop.cancel();
            backToStart();
        }
    }

    public void backToStart(){
        Intent i = new Intent(MainActivity.this, StartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("scoreKey", String.valueOf(scoreCount));
        i.putExtra("numberKey", randomNumber.getRanNumInt());
        startActivity(i);
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loop.cancel();
        backToStart();
    }

    public void success() {
       // if (i % 10 == 0 && i <= timeDecreaseLevel) time -= timeDecreaseValue;
        i++;
        progressBar.setProgress(progressStatus +=1);
        loop = gameLoop(time).start();
    }


}