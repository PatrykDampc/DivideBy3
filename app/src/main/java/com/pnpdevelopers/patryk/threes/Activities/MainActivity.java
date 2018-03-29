package com.pnpdevelopers.patryk.threes.Activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.function.GameControls;
import com.pnpdevelopers.patryk.threes.function.GameMechanics;
import com.pnpdevelopers.patryk.threes.function.GameMusic;
import com.pnpdevelopers.patryk.threes.function.HighScore;
import com.pnpdevelopers.patryk.threes.function.PreferenceManager;
import com.pnpdevelopers.patryk.threes.function.ProgressHandler;
import com.pnpdevelopers.patryk.threes.model.LevelLengths;
import com.pnpdevelopers.patryk.threes.model.LevelNumbers;
import com.pnpdevelopers.patryk.threes.util.OnSwipeTouchListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainActivityLayoutID)  ConstraintLayout layout;
    @BindView(R.id.numberTextSwitcherID) TextSwitcher textSwitcher;
    @BindView(R.id.progressBarID)  ProgressBar progressBar;
    @BindView(R.id.regresBar)  ProgressBar regresBar;
    @BindView(R.id.scoreID)  TextView scoreView;
    @BindView(R.id.highScoreTextViewID)  TextView highScoreView;
    @BindView(R.id.nextLevelID)  TextView  nextLevel;

    private ObjectAnimator animation;
    private Animation in, scale;
    private Vibrator vibe;

    private int[] gameArray;
    private static int number;
    private int scoreCount = 0;

    private boolean gameLeft;
    private Context context = this;

    private ProgressHandler progressHandler;
    private PreferenceManager preferenceManager;
    private GameMusic gameMusic;
    private HighScore highScore;
    private GameMechanics gameMechanics;
    private LevelNumbers mLevelNumbers;
    private LevelLengths mLevelLengths;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        gameLeft = false;

        setUpViews();
        startInitialAnimations();
        setUpTouchListeners();

        preferenceManager = new PreferenceManager(context);
        gameMusic = new GameMusic(context, preferenceManager);
        highScore = new HighScore(context,preferenceManager);
        mLevelNumbers = new LevelNumbers();
        mLevelLengths = new LevelLengths();
        progressHandler = new ProgressHandler(mLevelLengths,progressBar,context,nextLevel);

        gameMusic.setUpMusic(R.raw.bensound_funkysuspense, true);
        highScoreView.setText(context.getText(R.string.high_score) + String.valueOf(highScore.getHighScore()));
        nextLevel.setText(getString(R.string.level) + String.valueOf(progressHandler.getLevel() + 1) + getString(R.string.next_level_progress));

        setBaseGameValues();

        gameMechanics = new GameMechanics() {
            @Override
            protected void onTimerStart() {
                atActionBeginning();
            }

            @Override
            protected void onTimerFinish() {
                if (!GameControls.successCondition(number)) {
                    success();
                } else {
                    fail();
                }
            }
        };
        gameMechanics.startGameTimer();

    }

    private void setBaseGameValues() {
        gameArray = mLevelNumbers.createGameArray();
    }

    public void atActionBeginning(){
        number = gameArray[scoreCount];
        textSwitcher.setText(String.valueOf(number));
        animation.start();
    }

    public void success(){
        scoreCount++;
        progressHandler.incrementProgress();
        scoreView.setText(MainActivity.this.getText(R.string.score) +" "+ String.valueOf(scoreCount));

        highScore.checkIfAndPutNewHighScore(scoreCount,highScoreView);
        if(progressHandler.isNextLevel()) progressHandler.nextLevel();
        vibe.vibrate(40);
        gameMechanics.skipGameAction();
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
        gameMechanics.stopGameAction();
        gameMusic.stop();
        layout.setOnTouchListener(null);
        gameLeft = true;
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
                if (GameControls.successCondition(number)) {
                    success();
                } else {
                    fail();
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if(GameControls.successCondition(number)){
                    fail();
                } else {
                    success();
                }
            }
        });

    }

    private void setUpViews() {
        in = AnimationUtils.loadAnimation(this,R.anim.slide_in_from_top);
        scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        ButterKnife.bind(this);
        
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        animation = ObjectAnimator.ofInt(regresBar, "progress", 500, 0).setDuration(2500);

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
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
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

//GÓWNO