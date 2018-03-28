package com.pnpdevelopers.patryk.threes.Activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.function.GameMusic;
import com.pnpdevelopers.patryk.threes.function.PreferenceManager;
import com.pnpdevelopers.patryk.threes.model.LevelData;
import com.pnpdevelopers.patryk.threes.util.Conditions;
import com.pnpdevelopers.patryk.threes.util.OnSwipeTouchListener;
import com.pnpdevelopers.patryk.threes.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pnpdevelopers.patryk.threes.function.PreferenceManager.HIGH_SCORE_KEY;

public class MainActivity extends AppCompatActivity {
    //Views
    @BindView(R.id.mainActivityLayoutID)  ConstraintLayout layout;
    @BindView(R.id.numberTextSwitcherID) TextSwitcher textSwitcher;
    @BindView(R.id.progressBarID)  ProgressBar progressBar;
    @BindView(R.id.regresBar)  ProgressBar regresBar;
    @BindView(R.id.scoreID)  TextView scoreView;
    @BindView(R.id.highScoreTextViewID)  TextView highScoreView;
    @BindView(R.id.nextLevelID)  TextView  nextLevel;

    //Special effects
    private ObjectAnimator animation;
    private Animation in, scale;
    private Vibrator vibe;
    //regular variables
   private LevelData levelData;
    private int[] gameArray;
    private int[] levelLengths;
    private static int number;
    private int progressStatus, progressScope, level = 0, highScore, inLevelIterator = 0, scoreCount = 0, time = 2500;
    private boolean gameLeft;
    private Handler handler;
    private Runnable runnable;

    PreferenceManager preferenceManager;
    GameMusic gameMusic;
    MediaPlayer mediaPlayer;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setUpViews();

        preferenceManager = new PreferenceManager(MainActivity.this);
        gameMusic = new GameMusic(MainActivity.this, mediaPlayer, preferenceManager);
        gameMusic.setUpMusicPlayer(R.raw.bensound_funkysuspense, true);

       setUpTextSwitcher();
        setUpSharedPrefsAndGameData();
        setUpBaseValues();
        startInitialAnimations();
        setUpTouchListeners();
        startGameAction();

    }

    public void startGameAction() {
        number = gameArray[inLevelIterator];
        textSwitcher.setText(String.valueOf(number));

        animation.start();
        handler = new Handler();
        runnable = () -> {
            if (!Conditions.succesCondition(number)) {
                success();
            } else {
                fail();
            }
        };
        handler.postDelayed(runnable,time);
    }

    public void success(){
        handler.removeCallbacks(runnable);
        handler = null;
        inLevelIterator++;
        scoreCount++;
        progressStatus++;
        scoreView.setText(MainActivity.this.getText(R.string.score) +" "+ String.valueOf(scoreCount));
        checkIfHighScore();
        checkIfNextLevel();
        progressBar.setProgress(progressStatus);
        vibe.vibrate(40);
        startGameAction();
    }

    public void fail(){
        stopGameActions();
        startActivity(new Intent(MainActivity.this, StartActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("scoreKey", String.valueOf(scoreCount))
                .putExtra("numberKey", number));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    private void stopGameActions() {
        handler.removeCallbacks(runnable);
        gameMusic.getMediaPlayer().stop();
        layout.setOnTouchListener(null);
        gameLeft = true;
    }

    private void checkIfNextLevel(){
        if (progressBar.getProgress() == progressBar.getMax()) {
            Toast.makeText(MainActivity.this, MainActivity.this.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
            level++;
            nextLevel.setText(getString(R.string.level) + String.valueOf(level+1) + getString(R.string.next_level_progress));
            progressStatus = 0;
            progressScope = levelLengths[level];
            progressBar.setMax(progressScope);
        }
    }

    private void checkIfHighScore(){
        if (scoreCount == highScore && scoreCount != 0) {
            Toast.makeText(MainActivity.this, MainActivity.this.getText(R.string.new_record), Toast.LENGTH_SHORT).show();
        }
        if (scoreCount > highScore) {
            preferenceManager.getEditor().putInt(HIGH_SCORE_KEY, scoreCount).apply();
            highScoreView.setText(MainActivity.this.getText(R.string.high_score) +" "+ String.valueOf(scoreCount));
        }
    }

    private void startInitialAnimations() {
        regresBar.startAnimation(scale);
        scoreView.startAnimation(in);
        highScoreView.startAnimation(in);
        progressBar.startAnimation(in);
        nextLevel.startAnimation(in);
    }

    private void setUpTouchListeners() {
        //noinspection AndroidLintClickableViewAccessibility
        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onClick() {
                super.onClick();
                if (Conditions.succesCondition(number)) {
                    success();
                } else {
                    fail();
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if(Conditions.succesCondition(number)){
                    fail();
                } else {
                    success();
                }
            }
        });

    }



    public void setUpTextSwitcher() {
        Animation in = AnimationUtils.loadAnimation(MainActivity.this,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(MainActivity.this,
                android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        textSwitcher.setFactory(() -> {
            TextView myText = new TextView(MainActivity.this);
            myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            myText.setTextSize(70);
            myText.setTextColor(Color.WHITE);
            return myText;
        });

    }

    private void setUpViews() {
        in = AnimationUtils.loadAnimation(this,R.anim.slide_in_from_top);
        scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        ButterKnife.bind(this);
        
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        animation = ObjectAnimator.ofInt(regresBar, "progress", 500, 0).setDuration(time);

    }

    private void setUpBaseValues() {
        gameLeft = false;
        highScore = preferenceManager.getPrefs().getInt(HIGH_SCORE_KEY, 0);
        highScoreView.setText(this.getText(R.string.high_score) + " " + String.valueOf(highScore));
        progressScope = levelLengths[0];
        nextLevel.setText(getString(R.string.level) + String.valueOf(level + 1) + getString(R.string.next_level_progress));
        progressBar.setMax(progressScope);
    }

    private void setUpSharedPrefsAndGameData() {

        levelData = new LevelData();
        gameArray = levelData.getGameArray();
        levelLengths = levelData.getLevelLenghtsArray();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gameLeft){
            startActivity(new Intent(MainActivity.this, StartActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .putExtra("scoreKey", String.valueOf(scoreCount)));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopGameActions();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utils.fullScreenIfHasFocus(hasFocus, this);
    }

    @Override
    public void onBackPressed() {
        stopGameActions();
        startActivity(new Intent(MainActivity.this, StartActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("scoreKey", String.valueOf(scoreCount)));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        super.onBackPressed();
    }

    public static int getNumber(){
        return number;
    }

}
