package com.example.patryk.divideby3;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

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
    //Views
    private Button button;
    private TextSwitcher textSwitcher;
    private TextView scoreView;
    private TextView highScoreView;
    private ProgressBar regresBar;
    //Game controls
    private int time = 2500;
    private int timeDecreaseValue = 500;
    private int timeDecreaseLevel = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.bumButtonID);
        textSwitcher = findViewById(R.id.numberTextSwitcherID);
        scoreView = findViewById(R.id.scoreID);
        highScoreView = findViewById(R.id.highScoreTextViewID);
        regresBar = findViewById(R.id.regresBar);

        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt(HIGH_SCORE, 0);
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));

        textSwitcherConfiguration();
        button.setOnClickListener(this);
        loop = gameLoop(time).start();

    }

    public CountDownTimer gameLoop(int speedValue){

        return new CountDownTimer(speedValue, speedValue+100) {
            @Override
            public void onTick(long millisUntilFinished) {
                randomNumber = new RandomNumber();
                textSwitcher.setText(randomNumber.getRanNumString());

                ObjectAnimator animation = ObjectAnimator.ofInt (regresBar, "progress", 500, 0);
                animation.setDuration (time);

                // animation.setInterpolator (new DecelerateInterpolator());
                animation.start ();
                scoreView.setText(MainActivity.this.getText(R.string.score) +" "+ String.valueOf(scoreCount));
                if (scoreCount == highScore) {
                    Toast.makeText(MainActivity.this, "New record!", Toast.LENGTH_SHORT).show();
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
            scoreCount = scoreCount + 2;
            regresBar.clearAnimation();
            loop.cancel();
            success();
        } else {
            loop.cancel();
            backToStart();
        }
    }

    public void backToStart(){
        Toast.makeText(MainActivity.this, "ZJEBAŁEŚ", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MainActivity.this, StartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Log.d("RandomNumberbla: ", randomNumber.getRanNumString());
        startActivity(i);
    }

    public void textSwitcherConfiguration(){
        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                TextView myText = new TextView(MainActivity.this);
                myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(70);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

    }

    public void success() {
        //if (i % 10 == 0 && i <= timeDecreaseLevel) time -= timeDecreaseValue;
        i++;
        loop = gameLoop(time).start();
    }
}