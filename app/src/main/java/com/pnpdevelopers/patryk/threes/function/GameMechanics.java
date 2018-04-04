package com.pnpdevelopers.patryk.threes.function;

import android.os.Handler;

import com.pnpdevelopers.patryk.threes.model.LevelNumbers;

public abstract class GameMechanics {
    private Handler handler;
    private Runnable runnable;
    private LevelNumbers levelNumbers;
    int[] gameArray;
    int number;
    private int iterator;

    public int getTime() {
        return time;
    }

    private int time = 2500;


    public GameMechanics() {
    }


    public void startGameTimer() {
        onTimerStart();
        nextNumber();
        handler = new Handler();
        runnable = () -> {
           onTimerFinish();
        };
        handler.postDelayed(runnable,time);
    }

    public void stopGameAction(){
        handler.removeCallbacks(runnable);
    }

    public void skipGameAction(){
        handler.removeCallbacks(runnable);
        startGameTimer();
    }


    protected abstract void onTimerStart();

    protected abstract void onTimerFinish();

    public void setUp(){
        levelNumbers = new LevelNumbers();
        gameArray = levelNumbers.getGameArray();
        iterator=0;
    }
    private void nextNumber(){
        this.number = gameArray[iterator];
        iterator++;
    }

    public int getNumber() {
        return number;
    }
}
