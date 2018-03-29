package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.Activities.MainActivity;
import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.LevelLengths;

public class ProgressBarHandle {
    int[] levelLengths;
    ProgressBar progressBar;
    Context context;
    TextView nextLevelTxt;
    int iterator;
    int level;

    public ProgressBarHandle(int[] levelLengths, ProgressBar progressBar, Context context, TextView textView) {
        this.levelLengths = levelLengths;
        this.progressBar = progressBar;
        this.context = context;
        this.nextLevelTxt = textView;
        progressBar.setMax(levelLengths[0]);
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
        progressBar.setMax(levelLengths[level]);
        Toast.makeText(context.getApplicationContext(), context.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
    }
}
