package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.widget.TextView;

import com.pnpdevelopers.patryk.threes.R;
import com.pnpdevelopers.patryk.threes.util.MyApplication;

public class ScoreCounter {

    private int scoreCount = 0;
    private Context context  = MyApplication.getAppContext();

    public ScoreCounter() {
    }

    public void setAndPutScore(TextView scoreView){
        scoreCount++;
        scoreView.setText(context.getText(R.string.score) +" "+ String.valueOf(scoreCount));
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

}