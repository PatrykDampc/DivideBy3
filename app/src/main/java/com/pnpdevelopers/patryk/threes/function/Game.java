package com.pnpdevelopers.patryk.threes.function;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Vibrator;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.LevelNumbers;

public abstract class Game {
    private ObjectAnimator animation;
    private ProgressBar progressBar;
    private TextView nextLevelView;
    private TextView scoreView;
    private TextView highScoreView;
    private TextSwitcher textSwitcher;

    private int[] gameArray;
    private int number;
    private Context context;

    private Vibrator  vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    private ProgressHandler  progressHandler = new ProgressHandler(progressBar,context,nextLevelView);
    private PreferenceManager preferenceManager = new PreferenceManager(context);
    private GameMusic gameMusic = new GameMusic(context, preferenceManager);
    private HighScore highScore = new HighScore(context,preferenceManager);
    private LevelNumbers  mLevelNumbers = new LevelNumbers();
    private Score score = new Score(context);
    private GameMechanics gameMechanics;

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Game(ObjectAnimator animation, ProgressBar progressBar, TextView nextLevelView, TextView scoreView, TextView highScoreView, TextSwitcher textSwitcher, Context context) {
        this.animation = animation;
        this.progressBar = progressBar;
        this.nextLevelView = nextLevelView;
        this.scoreView = scoreView;
        this.highScoreView = highScoreView;
        this.textSwitcher = textSwitcher;
        this.context = context;
    }

    public void gameSetup(){
        gameArray = mLevelNumbers.createGameArray();
        highScoreView.setText(context.getText(R.string.high_score) + String.valueOf(highScore.getHighScore()));
        nextLevelView.setText(context.getString(R.string.level) + String.valueOf(progressHandler.getLevel() + 1) + context.getString(R.string.next_level_progress));
        progressHandler.setUpProgressBar();
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
                    gameStop();
                }
            }
        };
    }

    public void start(){
        gameMechanics.startGameTimer();
    }

    private void atActionBeginning() {
        number = gameArray[score.getScoreCount()];
        textSwitcher.setText(String.valueOf(number));
        animation.start();
    }


    public void success(){
        score.setAndPutScore(scoreView);
        progressHandler.incrementProgress();
        highScore.checkIfAndPutNewHighScore(score.getScoreCount(),highScoreView);
        progressHandler.setLevel();
        vibe.vibrate(40);
        gameMechanics.skipGameAction();
    }


    public void gameStop(){
        gameMechanics.stopGameAction();
        gameMusic.stop();
        gameStopAction();
    }

    public abstract void gameStopAction();

    public void onTouch(){
        if (GameConditions.successCondition(number)) {
            success();
        } else {
            gameStop();
        }
    }

    public void onSwipe(){
        if(GameConditions.successCondition(number)) {
            gameStop();
        } else {
            success();
        }
    }


}
