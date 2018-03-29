package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.model.Level;
import com.pnpdevelopers.patryk.threes.model.LevelNumbers;

public class GameLevelMechanics {
    private ProgressBar progressBar;
    private int level = 0;
    private Context context;
    private int progressStatus;
    private int progressScope;
    private int[] levelLengths;
    private Level mLevel;
    private LevelNumbers levelNumbers = new LevelNumbers(mLevel);


    public GameLevelMechanics(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;

    }



    private void checkIfNextLevel(TextView nextLevel){
        if (progressBar.getProgress() == progressBar.getMax()) {

            Toast.makeText(context, context.getText(R.string.level_up), Toast.LENGTH_SHORT).show();
            level++;
            nextLevel.setText(context.getString(R.string.level) + String.valueOf(level+1) + context.getString(R.string.next_level_progress));
            progressStatus = 0;
            progressScope = levelLengths[level];
            progressBar.setMax(progressScope);
        }
    }






}
