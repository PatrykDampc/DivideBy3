package com.pnpdevelopers.patryk.threes.Activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.RandomArrayFactory;
import com.pnpdevelopers.patryk.threes.util.OnSwipeTouchListener;
import com.pnpdevelopers.patryk.threes.util.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{  //implements View.OnClickListener {
    public static final String PREFERENCES = "Prefs";
    public static final String HIGH_SCORE = "HIGH_SCORE_KEY_BALANCED";
    //Views
    private ConstraintLayout layout;
    private TextSwitcher textSwitcher;
    private TextView scoreView;
    private TextView highScoreView;
    private ProgressBar regresBar;
    private ProgressBar progressBar;
    private TextView nextLevel;
    private ObjectAnimator animation;
    //regular variables
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;
    private int[] randomArray;
    private CountDownTimer loop;
    private int progressStatus;
    private int progressScope = 13;
    private int level = 1;
    private int highScore;
    private  Vibrator vibe;
    private MediaPlayer mediaPlayer2;
    private RandomArrayFactory array1, array2, array3, array4, array5, array6;
    private ArrayList<Integer> currentArray;
    private int inLevelIterator = 0;
    private int scoreCount = 0;
    private int time = 2500;
    private int number;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        layout = findViewById(R.id.mainActivityLayoutID);
        textSwitcher = findViewById(R.id.numberTextSwitcherID);
        scoreView = findViewById(R.id.scoreID);
        highScoreView = findViewById(R.id.highScoreTextViewID);
        regresBar = findViewById(R.id.regresBar);
        progressBar = findViewById(R.id.progressBarID);
        nextLevel = findViewById(R.id.nextLevelID);
        animation = ObjectAnimator.ofInt(regresBar, "progress", 500, 0).setDuration(time);
        editor = prefs.edit();
        highScore = prefs.getInt(HIGH_SCORE, 0);
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer2 = MediaPlayer.create(this, R.raw.bensound_funkysuspense);

        array1 = new RandomArrayFactory(3,100);
        array2 = new RandomArrayFactory(101,200);
        array3 = new RandomArrayFactory(201,310);
        array4 = new RandomArrayFactory(396,720);
        array5 = new RandomArrayFactory(721,999);
        array5 = new RandomArrayFactory(1000,1310);
        array6 = new RandomArrayFactory(1396,2000);

        mediaPlayer2.setLooping(true);
        mediaPlayer2.start();
        if(prefs.getBoolean(StartActivity.MUSIC_KEY, true)){
            mediaPlayer2.setVolume(1,1);
        } else {
            mediaPlayer2.setVolume(0,0);
        }
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));
        nextLevel.setText(getString(R.string.level) + String.valueOf(level) + getString(R.string.next_level_progress));
        progressBar.setMax(progressScope);
        Utils.textSwitcherConfiguration(textSwitcher, MainActivity.this);

        loop = gameLoop(time).start();

        //noinspection AndroidLintClickableViewAccessibility
        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onClick() {
                super.onClick();
                if (Utils.succesCondition(number)) {
                    loop.cancel();
                    success();
                } else {
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

    public CountDownTimer gameLoop(int time){
        return new CountDownTimer(time, time) {
            @Override
            public void onTick(long millisUntilFinished) {
                //circle time animation
                animation.start();
                //setting level depending on game score
                switch (scoreCount) {
                    case 0:
                        progressScope = 13;
                        currentArray = array1.getRandomArrayList();
                        break;
                    case 13:
                        progressScope = 31;
                        currentArray = array2.getRandomArrayList();
                        break;
                    case 43:
                        progressScope = 56;
                        currentArray = array3.getRandomArrayList();
                        break;
                    case 99:
                        progressScope = 106;
                        currentArray = array4.getRandomArrayList();
                        break;
                    case 205:
                        progressScope = 214;
                        currentArray = array5.getRandomArrayList();
                        break;
                    case 419:
                        progressScope = currentArray.size();
                        currentArray = array6.getRandomArrayList();
                        break;
                }
                number = currentArray.get(inLevelIterator);
                //present random to player
                textSwitcher.setText(String.valueOf(number));
                //progress bar logic
                if (progressBar.getProgress() == progressBar.getMax()) {
                    Toast.makeText(MainActivity.this, MainActivity.this.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
                    level++;
                    nextLevel.setText(getString(R.string.level) + String.valueOf(level) + getString(R.string.next_level_progress));
                    progressStatus = 0;
                    inLevelIterator = 0;
                    progressBar.setProgress(progressStatus);
                    progressBar.setMax(progressScope);
                }
            }
            @Override
            public void onFinish() {
                if (!Utils.succesCondition(number)) {
                    success();
                } else {
                    fail();
                }
            }
        };
    }
    public void success() {
        regresBar.clearAnimation();
        loop.start();
        scoreCount++;
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
        progressBar.setProgress(++progressStatus);
        inLevelIterator++;
    }

    //returns to startAcvivity, contains info about last shown number and earned score
    public void fail(){
        loop.cancel();
        mediaPlayer2.stop();
        mediaPlayer2.release();
        onPause();
        layout.setOnTouchListener(null);

        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("scoreKey", String.valueOf(scoreCount));
        intent.putExtra("numberKey", number);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        loop.cancel();
        mediaPlayer2.stop();
        mediaPlayer2.release();
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
