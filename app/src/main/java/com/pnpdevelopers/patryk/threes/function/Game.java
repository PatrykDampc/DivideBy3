package com.pnpdevelopers.patryk.threes.function;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.widget.TextSwitcher;

import com.pnpdevelopers.patryk.threes.model.Level;
import com.pnpdevelopers.patryk.threes.model.LevelLengths;
import com.pnpdevelopers.patryk.threes.model.LevelNumbers;

public class Game {
    private GameControls gameControls = new GameControls();
    private GameMechanics gameMechanics;
    private Level mLevel = new Level();
    private LevelNumbers mLevelNumbers = new LevelNumbers(mLevel);
    private LevelLengths mLevelLengths = new LevelLengths(mLevel);
    private int[] gameArray;
    private int[] levelLengths;
    private Context context;

    private int number;
    private int scoreCount;

    public Game(Context context) {
        this.context = context;

    }

    public void start(){
        gameMechanics.startGameTimer();
    }


    public void gameSetUp(ObjectAnimator animator, TextSwitcher textSwitcher){
        gameArray = mLevelNumbers.createGameArray();
        levelLengths = mLevelLengths.getLevelLengths();

        gameMechanics = new GameMechanics() {
            @Override
            protected void onTimerStart() {
                number = gameArray[scoreCount];
                textSwitcher.setText(String.valueOf(number));
                animator.start();
            }

            @Override
            protected void onTimerFinish() {
                if (!GameControls.successCondition(number)) {
                    success();
                } else {
                   // fail();
                }
            }
        };
        gameMechanics.startGameTimer();


    }


    public void success(){
//        scoreCount++;
//        progressStatus++;
//
//        scoreView.setText(MainActivity.this.getText(R.string.score) +" "+ String.valueOf(scoreCount));
//
//        highScore.checkIfAndPutNewHighScore(scoreCount,highScoreView);
//
//        checkIfNextLevel();
//        progressBar.setProgress(progressStatus);
//
//        vibe.vibrate(40);
//        gameMechanics.skipGameAction();
//    }

//    public void fail(){
//        stopGameActions();
//        startActivity(new Intent(MainActivity.this, StartActivity.class)
//                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                .putExtra("scoreKey", String.valueOf(scoreCount))
//                .putExtra("numberKey", number));
//        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }



}
