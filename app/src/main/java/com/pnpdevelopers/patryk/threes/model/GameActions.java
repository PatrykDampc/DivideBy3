package com.pnpdevelopers.patryk.threes.model;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;

public class GameActions {
    private LevelData levelData = new LevelData();
    private TextSwitcher textSwitcher;

    private int number;
    private int[] gameArray, levelLengths;
    private Context mContext;




    public GameActions(Context context, LevelData levelData) {
        gameArray = levelData.getGameArray();
        levelLengths = levelData.getLevelLenghtsArray();
        this.mContext = context;
        setUpTextSwitcher();
    }

    public void gameAction(int inLevelIterator){
        number = gameArray[inLevelIterator];
        textSwitcher.setText(String.valueOf(number));
    }



    public void setUpTextSwitcher() {
        Animation in = AnimationUtils.loadAnimation(mContext,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(mContext,
                android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        textSwitcher.setFactory(() -> {
            TextView myText = new TextView(mContext);
            myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            myText.setTextSize(70);
            myText.setTextColor(Color.WHITE);
            return myText;
        });

    }

}
