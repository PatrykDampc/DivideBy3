package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.LevelLengths;
import com.pnpdevelopers.patryk.threes.util.MyApplication;

public class ProgressHandler {
    private LevelLengths levelLengths = new LevelLengths();
    private int[] mLevelLengths = levelLengths.getLevelLengths();
    private ProgressBar progressBar;
    private Context context  = MyApplication.getAppContext();
    private int progressIterator;
    private int level;

    public int getLevel() {
        return level;
    }

    public ProgressHandler(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setMax(mLevelLengths[0]);
        progressIterator = 1;
        level = 0;
    }


    public void incrementProgress(){
        progressBar.setProgress(progressIterator);
        progressIterator++;
        setLevel();
    }

    public void zeroProgress(){
        progressIterator = 1;
        progressBar.setProgress(0);
    }

    public boolean isNextLevel(){
        return progressBar.getProgress() == progressBar.getMax();
    }

    public void nextLevel(){
        level++;
        zeroProgress();
        progressBar.setMax(mLevelLengths[level]);
        Toast.makeText(context.getApplicationContext(), context.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
    }

    public void setLevel(){
        if(isNextLevel())
            nextLevel();
    }

}
