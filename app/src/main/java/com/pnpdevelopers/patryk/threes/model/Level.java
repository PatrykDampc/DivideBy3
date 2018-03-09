package com.pnpdevelopers.patryk.threes.model;


import android.os.CountDownTimer;

/**
 * Created by patryk on 08.03.2018.
 */

public class Level {



    private RandomArrayFactory randomArrayFactory;
    private int scopeFrom;
    private int scopeTo;
    private int progressScope;



    public Level(int numbersFrom, int NumbersTo , int scopeFrom, int scopeTo) {
        this.randomArrayFactory = new RandomArrayFactory(numbersFrom,NumbersTo);
        this.scopeFrom = scopeFrom;
        this.scopeTo = scopeTo;
        this.progressScope = scopeTo - scopeFrom;
    }





    public int getProgressScope() {
        return progressScope;
    }

    public void setProgressScope(int progressScope) {
        this.progressScope = progressScope;
    }

    public RandomArrayFactory getRandomArrayFactory() {
        return randomArrayFactory;
    }

    public void setRandomArrayFactory(RandomArrayFactory randomArrayFactory) {
        this.randomArrayFactory = randomArrayFactory;
    }

    public int getScopeFrom() {
        return scopeFrom;
    }

    public void setScopeFrom(int scopeFrom) {
        this.scopeFrom = scopeFrom;
    }

    public int getScopeTo() {
        return scopeTo;
    }

    public void setScopeTo(int scopeTo) {
        this.scopeTo = scopeTo;
    }
}
