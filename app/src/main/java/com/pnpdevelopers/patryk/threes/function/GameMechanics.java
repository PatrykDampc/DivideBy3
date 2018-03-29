package com.pnpdevelopers.patryk.threes.function;

import android.os.Handler;

public abstract class GameMechanics {
    private Handler handler;
    private Runnable runnable;
    private int time;


    public GameMechanics(Handler handler, Runnable runnable, int time) {
        this.handler = handler;
        this.runnable = runnable;
        this.time = time;
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





}
