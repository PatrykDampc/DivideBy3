package com.pnpdevelopers.patryk.threes.function;

import android.os.Handler;

public abstract class GameMechanics {
    private Handler handler;
    private Runnable runnable;
    private int time;


    public GameMechanics() {
    }


    public void startGameAction() {
        onTimerStart();
        handler = new Handler();
        runnable = () -> {
           onTimerFinish();
        };
        handler.postDelayed(runnable,time);
    }

    protected abstract void onTimerStart();

    protected abstract void onTimerFinish();

    public void stopGameAction(){
        handler.removeCallbacks(runnable);
        handler = null;
    }

    public void skipGameAction(){
        handler.removeCallbacks(runnable);
        handler = null;
        startGameAction();
    }





}
