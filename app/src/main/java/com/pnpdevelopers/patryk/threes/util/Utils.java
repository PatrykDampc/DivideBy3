package com.pnpdevelopers.patryk.threes.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.pnpdevelopers.patryk.threes.R;

import java.util.Random;

/**
 * Created by pszen on 14.02.2018.
 */

public class Utils {
    // number of iterations for new level, to add new level u need to make changes in generateRandomNumberArray, customArrayShuffle, MainActivity.Ontick
    public static final int LEVEL_ONE = 13;
    public static final int LEVEL_TWO = 31;
    public static final int LEVEL_THREE = 56;
    public static final int LEVEL_FOUR = 106;
    public static final int LEVEL_FIVE = 214;
    public static final int LEVEL_SIX = 284;

    public static boolean isDivisibleByThree(int number){
        return number%3 == 0 ? true : false;
    }

    public static int generateRanNum(int minValue, int maxValue){
        Random random = new Random();
        return minValue + random.nextInt(maxValue - minValue +1);
    }

    public static int[] generateRandomNumberArray(int ammountOfNumbers){
        int [] array = new int[ammountOfNumbers];
        int min = 3;
        int max = 100;
        for (int i = 0; i < ammountOfNumbers; i++) {
            switch (i){
                case LEVEL_ONE:
                    min = 101;  //49
                    max = 200;
                    break;
                case LEVEL_TWO:
                    min = 201;  //90
                    max = 310;
                    break;
                case LEVEL_THREE:
                    min = 396; //396
                    max = 720;
                    break;
                case LEVEL_FOUR:
                    min = 721; //550
                    max = 999;
                    break;
                case LEVEL_FIVE:
                    min = 1000;
                    max = 1310;
                    break;
                case LEVEL_SIX:
                    min = 1396;
                    max = 2000;
            }
            array[i]= generateRanNum(min,max);
        }
        return array;
    }


    public static void customArrayShuffle(int[] ar){
        int min = LEVEL_FOUR;
        int max = ar.length-1;

        for (int i = ar.length - 1; i > 0; i--)
        {
            switch(i){
                case LEVEL_SIX:
                    min = LEVEL_FIVE;
                    max = LEVEL_SIX-1;
                    break;
                case LEVEL_FIVE:
                    min = LEVEL_FOUR;
                    max = LEVEL_FIVE-1;
                    break;
                case LEVEL_FOUR:
                    min = LEVEL_THREE;
                    max = LEVEL_FOUR-1;
                    break;
                case LEVEL_THREE:
                    min = LEVEL_TWO;
                    max = LEVEL_THREE-1;
                    break;
                case LEVEL_TWO:
                    min = LEVEL_ONE;
                    max = LEVEL_TWO-1;
                    break;
                case LEVEL_ONE:
                    min = 0;
                    max = LEVEL_ONE-1;
                    break;
            }
            // Simple swap
            int index1 = generateRanNum(min,max);
            int index2 = generateRanNum(min,max);
            int a = ar[index1];
            ar[index1] = ar[index2];
            ar[index2] = a;
        }
    }

    public static boolean succesCondition(int number){
        boolean containsThree = String.valueOf(number).contains("3");
        boolean isDivisible =  number%3==0;
        return containsThree || isDivisible;
    }

    public static void textSwitcherConfiguration(TextSwitcher textSwitcher, final Context context) {
        Animation in = AnimationUtils.loadAnimation(context,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(context,
                android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                TextView myText = new TextView(context);
                myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(70);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });
    }

    public static void printLostMessage(int number, String score, TextView numberViewStart, TextView scoreViewStart, Button startButton, Context context){
        if( number == 0 && score == null){
            numberViewStart.setVisibility(View.GONE);
            scoreViewStart.setVisibility(View.GONE);
        } else if(Utils.isDivisibleByThree(number)){
            int result = number/3;
            numberViewStart.setText(context.getString(R.string.your_lost) +" "+ String.valueOf(number) +" ÷ 3 = "+ result);
            startButton.setText(context.getString(R.string.tryagain));
        }  else if (String.valueOf(number).contains("3")){
            numberViewStart.setText(context.getString(R.string.your_lost) +" "+ String.valueOf(number) +" "+ context.getString(R.string.contains));
            startButton.setText(context.getString(R.string.tryagain));
        }  else {
            numberViewStart.setText(context.getString(R.string.your_lost) +" "+ String.valueOf(number) + "...");
            startButton.setText(context.getString(R.string.tryagain));
        }
    }

    public static void fullScreenIfHasFocus(boolean ifHas, Activity activity){
        if (ifHas) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }



}


// GÓWNO
