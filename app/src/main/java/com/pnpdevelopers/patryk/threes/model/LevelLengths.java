package com.pnpdevelopers.patryk.threes.model;

public class LevelLengths {
    Level level;

    public LevelLengths(Level level) {
        this.level = level;
    }

    public int[] getLevelLengths(){
        int[] levelLengths = new int[Levels.getLevels().size()];
        for(int i=0;i < levelLengths.length; i++){
            levelLengths[i] = Levels.getLevels().get(i).getLevelLength();
        }
        return levelLengths;
    }
}
