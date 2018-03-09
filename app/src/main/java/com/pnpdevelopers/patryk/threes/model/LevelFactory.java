package com.pnpdevelopers.patryk.threes.model;


/**
 * Created by patryk on 08.03.2018.
 */

public class LevelFactory {



    private RandomArrayFactory randomArrayFactory;

    private int progressScope;



    public LevelFactory(int numbersFrom, int NumbersTo , int scope) {
        this.randomArrayFactory = new RandomArrayFactory(numbersFrom,NumbersTo);
        this.progressScope = scope;
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


}
