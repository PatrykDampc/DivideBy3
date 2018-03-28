package com.pnpdevelopers.patryk.threes.model;

/**
 * Created by pszen on 09.03.2018.
 */

public class Level {
    int numbersFrom;
    int numbersTo;
    int levelLenght;

    public Level(int numbersFrom, int numbersTo, int levelLength) {
        this.numbersFrom = numbersFrom;
        this.numbersTo = numbersTo;
        this.levelLenght = levelLength;
    }

    public int getNumbersFrom() {
        return numbersFrom;
    }

    public int getNumbersTo() {
        return numbersTo;
    }

    public int getLevelLenght() {
        return levelLenght;
    }
}
