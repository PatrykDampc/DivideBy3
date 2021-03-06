package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.LevelLengths;

public class ProgressHandler {
    int[] mLevelLengths;
    ProgressBar progressBar;
    Context context;
    TextView nextLevelTxt;
    int iterator;

    public int getLevel() {
        return level;
    }

    int level;

    public ProgressHandler(LevelLengths levelLengths, ProgressBar progressBar, Context context, TextView textView) {
        this.mLevelLengths = levelLengths.getLevelLengths();
        this.progressBar = progressBar;
        this.context = context;
        this.nextLevelTxt = textView;
        progressBar.setMax(mLevelLengths[0]);
        iterator = 1;
        level = 0;
    }



    public void incrementProgress(){
        progressBar.setProgress(iterator);
        iterator++;
    }

    public void zeroProgress(){
        iterator = 1;
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
}
