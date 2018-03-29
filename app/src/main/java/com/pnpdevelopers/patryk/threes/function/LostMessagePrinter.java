package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pnpdevelopers.patryk.threes.R;

public class LostMessagePrinter {
    private Context context;


    public LostMessagePrinter(Context context) {
        this.context = context;
    }


    public void print(Button startButton, TextView numberViewStart, TextView scoreView, String score, int lostNumber){
        if(score == null){
            scoreView.setVisibility(View.GONE);
        }
        if( lostNumber == 0){
            numberViewStart.setVisibility(View.GONE);
        } else if(GameControls.isDivisibleByThree(lostNumber)){
            int result = lostNumber/3;
            numberViewStart.setText(context.getString(R.string.your_lost) +" "+ String.valueOf(lostNumber) +" ÷ 3 = "+ result);
            startButton.setText(context.getString(R.string.tryagain));
        }  else if (GameControls.containsThree(lostNumber)){
            numberViewStart.setText(context.getString(R.string.your_lost) +" "+ String.valueOf(lostNumber) +" "+ context.getString(R.string.contains));
            startButton.setText(context.getString(R.string.tryagain));
        }  else {
            numberViewStart.setText(context.getString(R.string.your_lost) + " " + String.valueOf(lostNumber) + "...");
            startButton.setText(context.getString(R.string.tryagain));
        }
    }




}
