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
import com.pnpdevelopers.patryk.threes.function.GameConditions;
import com.pnpdevelopers.patryk.threes.function.GameMechanics;
import com.pnpdevelopers.patryk.threes.function.GameMusic;
import com.pnpdevelopers.patryk.threes.function.HighScore;
import com.pnpdevelopers.patryk.threes.function.ProgressHandler;
import com.pnpdevelopers.patryk.threes.function.ScoreCounter;
import com.pnpdevelopers.patryk.threes.model.LevelNumbers;
import com.pnpdevelopers.patryk.threes.util.OnSwipeTouchListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {
    @BindView(R.id.mainActivityLayoutID)  ConstraintLayout layout;
    @BindView(R.id.numberTextSwitcherID) TextSwitcher textSwitcher;
    @BindView(R.id.progressBarID)  ProgressBar progressBar;
    @BindView(R.id.regresBar)  ProgressBar regresBar;
    @BindView(R.id.scoreID)  TextView scoreView;
    @BindView(R.id.highScoreTextViewID)  TextView highScoreView;
    @BindView(R.id.nextLevelID)  TextView nextLevelView;

    private Animation switcherIn, switcherOut, switcherScaleOut, in, scale;
    private ObjectAnimator animation;
    private Vibrator vibe;

    private int[] gameArray;
    private static int number;
    private boolean gameLeft;
    private Context context = this;

    private ProgressHandler progressHandler = new ProgressHandler();
    private GameMusic  gameMusic = new GameMusic();
    private HighScore  highScore = new HighScore();
    private LevelNumbers levelNumbers = new LevelNumbers();
    private ScoreCounter scoreCounter = new ScoreCounter();
    private GameMechanics gameMechanics;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        gameLeft = false;

        gameMusic.setUpMusic(R.raw.bensound_funkysuspense, true);
        setUpViews();
        startInitialAnimations();
        setUpTouchListeners();

        setBaseGameValues();
        gameMechanics = new GameMechanics() {
            @Override
            protected void onTimerStart() {
                atActionBeginning();
            }

            @Override
            protected void onTimerFinish() {
                if (!GameConditions.successCondition(number)) {
                    success();
                } else {
                    fail();
                }
            }
        };
        gameMechanics.startGameTimer();

    }

    private void setBaseGameValues() {
        gameArray = gameMechanics.getGameArray();
        progressHandler.setBaseProgress(progressBar);
    }

    public void atActionBeginning(){
        number = gameArray[scoreCounter.getScoreCount()];
        textSwitcher.setText(String.valueOf(number));
        animation.start();
    }

    public void success(){
        scoreCounter.setAndPutScore(scoreView);
        highScore.checkIfAndPutNewHighScore(scoreCounter.getScoreCount(),highScoreView);
        gameMechanics.skipGameAction();

        vibe.vibrate(40);
        progressHandler.incrementProgress(progressBar);
    }

    public void fail(){
        stopGameActions();
        startActivity(new Intent(GameActivity.this, StartActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("scoreKey", String.valueOf(scoreCounter.getScoreCount()))
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
        nextLevelView.startAnimation(in);
    }

    private void setUpTouchListeners() {
        //noinspection AndroidLintClickableViewAccessibility
        layout.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this) {
            @Override
            public void onClick() {
                super.onClick();
                textSwitcher.setOutAnimation(switcherScaleOut);
                if (GameConditions.successCondition(number)) {
                    success();
                } else {
                    fail();
                }
                textSwitcher.setOutAnimation(switcherOut);
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if(GameConditions.successCondition(number)){
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
        switcherIn = AnimationUtils.loadAnimation(GameActivity.this, android.R.anim.slide_in_left);
        switcherOut = AnimationUtils.loadAnimation(GameActivity.this, android.R.anim.slide_out_right);
        switcherScaleOut = AnimationUtils.loadAnimation(GameActivity.this,R.anim.scale_out);

        textSwitcher.setInAnimation(switcherIn);
        textSwitcher.setOutAnimation(switcherOut);

        textSwitcher.setFactory(() -> {
            TextView myText = new TextView(GameActivity.this);
            myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            myText.setTextSize(70);
            myText.setTextColor(Color.WHITE);
            return myText;
        });
        highScoreView.setText(context.getText(R.string.high_score) + String.valueOf(highScore.getHighScore()));
        nextLevelView.setText(getString(R.string.level) + String.valueOf(progressHandler.getLevel() + 1) + getString(R.string.next_level_progress));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gameLeft){
            startActivity(new Intent(GameActivity.this, StartActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .putExtra("scoreKey", String.valueOf(scoreCounter.getScoreCount())));
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
        startActivity(new Intent(GameActivity.this, StartActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("scoreKey", String.valueOf(scoreCounter.getScoreCount())));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        super.onBackPressed();
    }

    public static int getNumber(){
        return number;
    }

}

//GÓWNO

