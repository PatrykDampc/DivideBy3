package com.pnpdevelopers.patryk.threes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pszen on 13.03.2018.
 */

public class GameData {
    int[] levelLenghtsArray;
    int[] gameArray;
    List<Level> levels;
    List<List<Integer>> levelsNumbers;

    public GameData() {
        this.levels = initializeLevels();
        this.levelLenghtsArray = initializeLevelLengthsArray();
        this.levelsNumbers = createLevelsNumbers(levels);
        this.gameArray = generateGameArray(levelsNumbers,levelLenghtsArray);
    }

    private List<Level> initializeLevels(){   // levelLength must be less than numbersTo - numbersFrom
        levels = new ArrayList<>();
        levels.add(new Level(3,100,13));
        levels.add(new Level(101,200,15));
        levels.add(new Level(201,310,18));
        levels.add(new Level(396,720,50));
        levels.add(new Level(721,999,60));
        levels.add(new Level(1000,2000,60));
        levels.add(new Level(2001,3100,60));
        levels.add(new Level(3960,7200,60));
        levels.add(new Level(7210,9999,60));
        levels.add(new Level(10000,20000 ,60));
        levels.add(new Level(20001,31000,60));
        levels.add(new Level(39600,72000,60));
        levels.add(new Level(72001,99999,60));
        return levels;
    }

    private int[] initializeLevelLengthsArray(){
        int[] levelLenghtsArray = new int[levels.size()];
        for(int i = 0; i < levels.size(); i++){
            levelLenghtsArray[i] = levels.get(i).getLevelLenght();
        }
        return levelLenghtsArray;
    }

    private List<List<Integer>> createLevelsNumbers(List<Level> levels){
        List<List<Integer>> levelsNumbers = new ArrayList<>();
        for(int i = 0; i < levels.size() ; i++) {
            levelsNumbers.add(createShuffledArrayList(levels.get(i)));
        }
        return levelsNumbers;
    }

    private List<Integer> createShuffledArrayList(Level level){
        List<Integer> shuffledArrayList = new ArrayList<>();
        int forLoopEnd = level.getNumbersTo()-level.getNumbersFrom();
        int numberToAdd = level.getNumbersFrom();
        for(int i = 0; i < forLoopEnd ; i++){
            shuffledArrayList.add(numberToAdd);
            numberToAdd++;
        }
        Collections.shuffle(shuffledArrayList);
        return shuffledArrayList;
    }

    private int[] generateGameArray(List<List<Integer>> levelsNumbers, int[] levelLenghtsArray){
        int gameLength = sumIntInArray(levelLenghtsArray);
        int[] gameArray = new int[gameLength];
        int level = 0;
        int iterator = 0;
        for(int i = 0; i < gameLength; i++){
            gameArray[i] = levelsNumbers.get(level).get(iterator);
            if(levelLenghtsArray[level] == iterator && level<levelsNumbers.size()-1){
                level++;
                iterator=0;
            }
            iterator++;
        }
        return gameArray;
    }


    private int sumIntInArray(int[] arrayToSum){
        int sum = 0;
        for(int i: arrayToSum){
            sum+=i;
        }
        return sum;
    }

    public int[] getLevelLenghtsArray() {
        return levelLenghtsArray;
    }

    public int[] getGameArray() {
        return gameArray;
    }
}
