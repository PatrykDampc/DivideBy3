package com.pnpdevelopers.patryk.threes.function;

import android.os.Handler;

public abstract class GameMechanics {
    private Handler handler;
    private Runnable runnable;
    private int time = 2500;


    public GameMechanics() {
    }


    public void startGameTimer() {
        onTimerStart();
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


}
