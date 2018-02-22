package com.pnpdevelopers.patryk.threes.presenter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.CustomTimer;
import com.pnpdevelopers.patryk.threes.util.OnSwipeTouchListener;
import com.pnpdevelopers.patryk.threes.util.Utils;

public class MainActivity extends AppCompatActivity{  //implements View.OnClickListener {
    public static final String PREFERENCES = "Prefs";
    public static final String HIGH_SCORE = "HIGH_SCORE_KEY";
    //regular variables
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;
    private int highScore;
    private int scoreCount = 0;
    private int i =0;
    private int[] randomArray;
    private CustomTimer loop;
    private int progressStatus;
    private int progressScope = 10;
    private  Vibrator vibe;
    private ObjectAnimator animation;
    private int time = 2500;
    //Views
    private ConstraintLayout layout;
    private TextSwitcher textSwitcher;
    private TextView scoreView;
    private TextView highScoreView;
    private ProgressBar regresBar;
    private ProgressBar progressBar;
    private TextView nextLevel;

    @SuppressLint("ClickableViewAccessibility")
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
        animation = ObjectAnimator.ofInt(regresBar, "progress", 500, 0).setDuration(time);

        //read High Score from Shared Preferences
        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt(HIGH_SCORE, 0);
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));

        //Set up main game info & controls
        randomArray = Utils.generateRandomNumberArray(200);
        progressBar.setMax(progressScope);
        Utils.textSwitcherConfiguration(textSwitcher, MainActivity.this);
        loop = gameLoop(time).start();

        //noinspection AndroidLintClickableViewAccessibility
        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            @Override
            public void onClick() {
                super.onClick();
                if (Utils.succesCondition(randomArray[i])) {
                    //happens when user taps screen on number that meets conditions;
                    loop.cancel();
                    scoreCount += 2;
                    regresBar.clearAnimation();
                   // vibe.vibrate(25);
                    success();
                } else {
                    //happens when user taps screen on number that doesn't meets conditions
                    fail();
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                loop.cancel();
                loop.onFinish();
            }
        });

    }


    public CustomTimer gameLoop(int speedValue){

        return new CustomTimer(speedValue, speedValue+5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("onTick: ","ok");
                //setting level depending on game itaration count
                switch (i){
                    case 10:
                        progressScope = 15;
                        break;
                    case 25:
                        progressScope = 20;
                        break;
                    case 45:
                        progressScope = 35;
                        break;
                    case 80:
                        //disabling progress,because there are no more levels
                        progressBar.setVisibility(View.GONE);
                        nextLevel.setVisibility(View.GONE);
                        break;
                }
                //present random to player
                textSwitcher.setText(String.valueOf(randomArray[i]));

                //progress bar logic
                if (progressBar.getProgress() == progressBar.getMax()) {
                    Toast.makeText(MainActivity.this,MainActivity.this.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
                    progressStatus = 0;
                    progressBar.setProgress(progressStatus);
                    progressBar.setMax(progressScope);
                }
                //circle time animation
                animation.start();
            }

            @Override
            public void onFinish() {
                Log.d("onFinish: ","ok");
                if (!Utils.succesCondition(randomArray[i])) {
                    //happens then user didn't do anything when he shouldn't
                    scoreCount++;
                    regresBar.clearAnimation();
                    success();
                } else {
                   //happens then user didn't tap the screen, but number was meeting conditions;
                    fail();
                }
            }
        };
    }

    public void success() {
        scoreView.setText(MainActivity.this.getText(R.string.score) +" "+ String.valueOf(scoreCount));
        if (scoreCount == highScore && scoreCount != 0) {
            Toast.makeText(MainActivity.this, MainActivity.this.getText(R.string.new_record), Toast.LENGTH_SHORT).show();
        }
        if (scoreCount > highScore) {
            editor.putInt(HIGH_SCORE, scoreCount);
            editor.commit();
            highScoreView.setText(MainActivity.this.getText(R.string.high_score) +" "+ String.valueOf(scoreCount));
        }
        vibe.vibrate(25);
        i++;
        progressBar.setProgress(progressStatus +=1);
        loop.start();
    }

    //returns to startAcvivity, contains info about last shown number and earned score
    public void fail(){
        loop.cancel();
        onPause();
        layout.setOnTouchListener(null);

        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("scoreKey", String.valueOf(scoreCount));
        intent.putExtra("numberKey", randomArray[i]);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);

    }

    @Override
    public void onBackPressed() {
        loop.cancel();
        onPause();
        layout.setOnTouchListener(null);

        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, StartActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utils.fullScreenIfHasFocus(hasFocus, this);
    }


}
