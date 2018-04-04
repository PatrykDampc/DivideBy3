package com.pnpdevelopers.patryk.threes.function;

import android.os.Handler;

import com.pnpdevelopers.patryk.threes.model.Level;
import com.pnpdevelopers.patryk.threes.model.Levels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GameMechanics {
    private Handler handler;
    private Runnable runnable;

    public int getTime() {
        return time;
    }

    private int time = 2500;


    public GameMechanics() {
    }


    public void startGameTimer() {
        onTimerStart();
        handler = new Handler();
        runnable = () -> {
           onTimerFinish();
        };
        handler.postDelayed(runnable,time);
    }

    public void stopGameAction(){
        handler.removeCallbacks(runnable);
    }

    public void skipGameAction(){
        handler.removeCallbacks(runnable);
        startGameTimer();
    }


    protected abstract void onTimerStart();

    protected abstract void onTimerFinish();

    public int[] getGameArray() {
        return createGameArray();
    }

    private int[] createLevelLengthsArray(){
        List<Level> levels = Levels.getLevels();
        int[] levelLengthsArray = new int[levels.size()];
        for(int i = 0; i < levels.size(); i++){
            levelLengthsArray[i] = levels.get(i).getLevelLength();
        }
        return levelLengthsArray;
    }

    private int[] createGameArray(){
        int[] levelLengthsArray = createLevelLengthsArray();
        List<List<Integer>> levelsNumbers = createLevelsNumbers(Levels.getLevels());
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


}
