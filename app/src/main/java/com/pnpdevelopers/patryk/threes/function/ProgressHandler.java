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
    private Context context  = MyApplication.getAppContext();
    private int progress;
    private int level;

    public int getLevel() {
        return level;
    }

    public ProgressHandler() {
        progress = 1;
        level = 0;
    }

    public void setBaseProgress(ProgressBar progressBar){
        progressBar.setMax(mLevelLengths[0]);
    }


    public void incrementProgress(ProgressBar progressBar){
        progressBar.setProgress(progress);
        progress++;
        setLevel(progressBar);
    }

    public void zeroProgress(ProgressBar progressBar){
        progress = 1;
        progressBar.setProgress(0);
    }

    public boolean isNextLevel(ProgressBar progressBar){
        return progressBar.getProgress() == progressBar.getMax();
    }

    public void nextLevel(ProgressBar progressBar){
        level++;
        zeroProgress(progressBar);
        progressBar.setMax(mLevelLengths[level]);
        Toast.makeText(context.getApplicationContext(), context.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
    }

    public void setLevel(ProgressBar progressBar){
        if(isNextLevel(progressBar))
            nextLevel(progressBar);
    }

}
