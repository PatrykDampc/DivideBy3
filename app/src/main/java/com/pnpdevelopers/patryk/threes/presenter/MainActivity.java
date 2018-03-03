package com.pnpdevelopers.patryk.threes.presenter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.util.CustomTimer;
import com.pnpdevelopers.patryk.threes.util.OnSwipeTouchListener;
import com.pnpdevelopers.patryk.threes.util.Utils;

public class MainActivity extends AppCompatActivity{  //implements View.OnClickListener {
    public static final String PREFERENCES = "Prefs";
    public static final String HIGH_SCORE = "HIGH_SCORE_KEY_BALANCED";
    //regular variables
    private SharedPreferences prefs;
    private  SharedPreferences.Editor editor;
    private int[] randomArray;
    private CustomTimer loop;
    private int progressStatus;
    private int progressScope = Utils.LEVEL_ONE;
    private int level = 1;
    private int highScore;
    private  Vibrator vibe;
    private MediaPlayer mediaPlayer2;
    //Views
    private ConstraintLayout layout;
    private TextSwitcher textSwitcher;
    private TextView scoreView;
    private TextView highScoreView;
    private ProgressBar regresBar;
    private ProgressBar progressBar;
    private TextView nextLevel;
    private ObjectAnimator animation;
    //game variables
    private int scoreCount = 0;
    private int ammountOfNumbersInArray = 10000;
    private int time = 2500;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        mediaPlayer2 = new MediaPlayer();
        mediaPlayer2 = MediaPlayer.create(this, R.raw.bensound_funkysuspense);
        mediaPlayer2.setLooping(true);
        mediaPlayer2.start();
        if(prefs.getBoolean(StartActivity.MUSIC_KEY, true)){
            mediaPlayer2.setVolume(1,1);
        } else {
            mediaPlayer2.setVolume(0,0);
        }

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
        editor = prefs.edit();
        highScore = prefs.getInt(HIGH_SCORE, 0);
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));

        //Set up main game info & controls
        nextLevel.setText(getString(R.string.level) + String.valueOf(level) + getString(R.string.next_level_progress));
        randomArray = Utils.generateRandomNumberArray(ammountOfNumbersInArray);
        Utils.customArrayShuffle(randomArray);
        progressBar.setMax(progressScope);
        Utils.textSwitcherConfiguration(textSwitcher, MainActivity.this);
        loop = gameLoop(time).start();

        //noinspection AndroidLintClickableViewAccessibility
        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onClick() {
                super.onClick();
                if (Utils.succesCondition(randomArray[scoreCount])) {
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

    public CustomTimer gameLoop(int speedValue){
        return new CustomTimer(speedValue, speedValue) {
            @Override
            public void onTick(long millisUntilFinished) {
                //circle time animation
                animation.start();
                //setting level depending on game score
                switch (scoreCount) {
                    case Utils.LEVEL_ONE:
                        progressScope = Utils.LEVEL_TWO - Utils.LEVEL_ONE;
                        break;
                    case Utils.LEVEL_TWO:
                        progressScope = Utils.LEVEL_THREE - Utils.LEVEL_TWO;
                        break;
                    case Utils.LEVEL_THREE:
                        progressScope = Utils.LEVEL_FOUR - Utils.LEVEL_THREE;
                        break;
                    case Utils.LEVEL_FOUR:
                        progressScope = Utils.LEVEL_FIVE - Utils.LEVEL_FOUR;
                        break;
                    case Utils.LEVEL_SIX:
                        progressScope = randomArray.length;
                        break;
                }
                //present random to player
                textSwitcher.setText(String.valueOf(randomArray[scoreCount]));
                //progress bar logic
                if (progressBar.getProgress() == progressBar.getMax()) {
                    Toast.makeText(MainActivity.this, MainActivity.this.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
                    level++;
                    nextLevel.setText(getString(R.string.level) + String.valueOf(level) + getString(R.string.next_level_progress));
                    progressStatus = 0;
                    progressBar.setProgress(progressStatus);
                    progressBar.setMax(progressScope);
                }
            }

            @Override
            public void onFinish() {
                if (!Utils.succesCondition(randomArray[scoreCount])) {
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
        intent.putExtra("numberKey", randomArray[scoreCount]);
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
