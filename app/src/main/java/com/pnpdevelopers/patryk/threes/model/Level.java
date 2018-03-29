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

    public List<Level> getLevels(){
        List<Level> levels = new ArrayList<>();
        levels.add(new Level(3,100,13));
        levels.add(new Level(101,200,20));
        levels.add(new Level(201,310,30));
        levels.add(new Level(396,720,50));
        levels.add(new Level(721,999,60));
        levels.add(new Level(1000,2000,70));
        levels.add(new Level(2001,3100,70));
        levels.add(new Level(3960,7200,70));
        levels.add(new Level(7210,9999,70));
        levels.add(new Level(10000,20000 ,70));
        levels.add(new Level(20001,31000,70));
        levels.add(new Level(39600,72000,70));
        levels.add(new Level(72001,99999,1000));
        return levels;
    }
}
