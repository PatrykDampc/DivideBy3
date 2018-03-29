package com.pnpdevelopers.patryk.threes.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pszen on 09.03.2018.
 */

public class Level {
    int numbersFrom;
    int numbersTo;
    int levelLenght;
    public Level(){

    }

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

    public int getLevelLength() {
        return levelLenght;
    }


}
