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
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by pszen on 14.02.2018.
 */

public class Utils {

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
                case 10:
                    min = 101;  //49
                    max = 200;
                    break;
                case 25:
                    min = 201;  //90
                    max = 310;
                    break;
                case 45:
                    min = 396; //396
                    max = 720;
                    break;
                case 80:
                    min = 721; //550
                    max = 999;
            }
            array[i]= generateRanNum(min,max);
        }
        return array;
    }


    public static void customArrayShuffle(int[] ar){
        int min = 80;
        int max = ar.length-1;

        for (int i = ar.length - 1; i > 0; i--)
        {
            switch(i){
                case 80:
                    min = 45;
                    max = 79;
                    break;
                case 45:
                    min = 25;
                    max = 44;
                    break;
                case 25:
                    min = 10;
                    max = 24;
                    break;
                case 10:
                    min = 0;
                    max = 9;
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

    public static int[] generateRandomNumberArray(int min, int max, int numOf){
        int [] array = new int[numOf];
        for(int i = 0; i < numOf; i++){
            array[i] = generateRanNum(min,max);
        }
        return array;
    }

}
