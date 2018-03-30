package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.LevelLengths;

public class ProgressHandler {
    private LevelLengths levelLengths = new LevelLengths();
    private int[] mLevelLengths = levelLengths.getLevelLengths();
    private ProgressBar progressBar;
    private Context context;
    private int progressIterator;
    private int level;

    public int getLevel() {
        return level;
    }

    public ProgressHandler(ProgressBar progressBar, Context context, TextView textView) {
        this.progressBar = progressBar;
        this.context = context;
        TextView nextLevelTxt = textView;
        progressIterator = 1;
        level = 0;
    }

    public void setUpProgressBar(){
        progressBar.setMax(mLevelLengths[0]);
    }

    public void incrementProgress(){
        progressBar.setProgress(progressIterator);
        progressIterator++;
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
