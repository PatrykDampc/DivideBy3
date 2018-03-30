package com.pnpdevelopers.patryk.threes.Activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.pnpdevelopers.patryk.threes.function.Game;
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

    private Animation in, scale;
    private ObjectAnimator animation;

    private boolean gameLeft;
    private Context context = this;
    private Game game;

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

        game = new Game(animation,progressBar,nextLevelView,scoreView,highScoreView,textSwitcher) {
            @Override
            public void gameStopAction() {
                onStopGameActions();
            }
        };
        game.gameSetup();
        game.start();

    }

    private void onStopGameActions() {
        layout.setOnTouchListener(null);
        game.stop();
        startActivity(new Intent(GameActivity.this, StartActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("scoreKey", String.valueOf(game.getScore().getScoreCount()))
                .putExtra("numberKey", game.getNumber()));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
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
                game.onTouch();
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                game.onSwipe();
            }
        });

    }

    private void setUpViews() {
        in = AnimationUtils.loadAnimation(this,R.anim.slide_in_from_top);
        scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        ButterKnife.bind(this);

        animation = ObjectAnimator.ofInt(regresBar, "progress", 500, 0).setDuration(2500);

        Animation in = AnimationUtils.loadAnimation(GameActivity.this,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(GameActivity.this,
                android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        textSwitcher.setFactory(() -> {
            TextView myText = new TextView(GameActivity.this);
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
            startActivity(new Intent(GameActivity.this, StartActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .putExtra("scoreKey", String.valueOf(game.getScore().getScoreCount())));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameLeft = true;
        game.stop();
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
        gameLeft = true;
        game.stop();
        startActivity(new Intent(GameActivity.this, StartActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("scoreKey", String.valueOf(game.getScore().getScoreCount())));
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        super.onBackPressed();
    }

}

//GÓWNO