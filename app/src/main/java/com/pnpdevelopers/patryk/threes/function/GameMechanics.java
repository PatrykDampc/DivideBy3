package com.pnpdevelopers.patryk.threes.function;

import android.os.Handler;

import com.pnpdevelopers.patryk.threes.model.LevelNumbers;

public abstract class GameMechanics {
    private Handler handler;
    private Runnable runnable;
    private LevelNumbers levelNumbers = new LevelNumbers();
    int[] gameArray = levelNumbers.getGameArray();
    int number;
    private int iterator = 0;

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


    private void nextNumber(){
        this.number = gameArray[iterator];
        iterator++;
    }

    public int getNumber() {
        return number;
    }
}
