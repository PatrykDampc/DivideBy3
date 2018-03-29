package com.pnpdevelopers.patryk.threes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pszen on 13.03.2018.
 */

public class LevelData {
    public int[] getLevelLenghtsArray() {
        return levelLenghtsArray;
    }

    public void setLevelLenghtsArray(int[] levelLenghtsArray) {
        this.levelLenghtsArray = levelLenghtsArray;
    }

    int[] levelLenghtsArray;
    int[] gameArray;
    List<Level> levels;
    List<List<Integer>> levelsNumbers;

    public LevelData() {
    }



    private List<Level> createLevels(List<Level> levels){   // levelLength must be less than numbersTo - numbersFrom
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

    public int[] createLevelLengthsArray(){
        List<Level> levels = createLevels(new ArrayList<>());
        int[] levelLenghtsArray = new int[levels.size()];
        for(int i = 0; i < levels.size(); i++){
            levelLenghtsArray[i] = levels.get(i).getLevelLenght();
        }
        return levelLenghtsArray;
    }

    public int[] createGameArray(){
        int[] levelLengthsArray = createLevelLengthsArray();
        List<List<Integer>> levelsNumbers = createLevelsNumbers(createLevels(new ArrayList<>()));
        int gameLength = sumIntInArray(levelLengthsArray);
        int[] gameArray = new int[gameLength];
        int level = 0;
        int iterator = 0;
        for(int i = 0; i < gameLength; i++){
            gameArray[i] = levelsNumbers.get(level).get(iterator);
            if(levelLengthsArray[level] == iterator && level<levelsNumbers.size()-1){
                level++;
                iterator=0;
            }
            iterator++;
        }
        return gameArray;
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


    private int sumIntInArray(int[] arrayToSum){
        int sum = 0;
        for(int i: arrayToSum){
            sum+=i;
        }
        return sum;
    }

    public int getLevelLength(int level){
        int[] levelLengths = createLevelLengthsArray();
        return levelLengths[level]; 
    }


}
