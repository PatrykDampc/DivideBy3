package com.pnpdevelopers.patryk.threes.model;

import android.os.Handler;

/**
 * Created by patryk on 24.03.2018.
 */

public abstract class GameLooper {

    private Handler handler = new Handler();



    public GameLooper(final int score, boolean showIsOn) {

            while(showIsOn){
                Runnable runnable = new Runnable() {
                    public void run() {
                        int time;
                        score == 0 ? time = 0 : time = 2500;
                        handler.postDelayed(this, time);
                        success();
                    }
                };

            }



    }


    public abstract void success();
    public abstract void fail();




}

