package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.pnpdevelopers.patryk.threes.R;

import static com.pnpdevelopers.patryk.threes.function.PreferenceManager.HIGH_SCORE_KEY;

public class HighScore {
    private PreferenceManager preferenceManager;
    private Context context;
    private int highScore;

    public HighScore(Context context, PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
        this.context = context;
        highScore = preferenceManager.getPrefs().getInt(HIGH_SCORE_KEY,0);
    }

    public int getHighScore(){
       return highScore;
    }

    public void setHighScore(int scoreCount){
        preferenceManager.getEditor().putInt(HIGH_SCORE_KEY,scoreCount);
    }

    public void checkIfAndPutNewHighScore(int scoreCount, TextView highScoreView){
        if (scoreCount == highScore && scoreCount != 0) {
            Toast.makeText(context, context.getText(R.string.new_record), Toast.LENGTH_SHORT).show();
            preferenceManager.getEditor().putInt(HIGH_SCORE_KEY, scoreCount).apply();
        }
        if (scoreCount > highScore) {
           setHighScore(scoreCount);
           highScoreView.setText(context.getText(R.string.high_score) +" "+ String.valueOf(scoreCount));
            preferenceManager.getEditor().putInt(HIGH_SCORE_KEY, scoreCount).apply();
        }
    }

}
