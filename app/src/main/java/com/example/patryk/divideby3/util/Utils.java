package com.example.patryk.divideby3.util;

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

import com.example.patryk.divideby3.R;

import java.util.Random;

/**
 * Created by pszen on 14.02.2018.
 */

public class Utils {


    public static int randomNumberGenerator(int minValue, int maxValue){
        Random ran = new Random();
        return minValue + ran.nextInt(maxValue - minValue +1);
    }

    public static boolean isDivisibleByThree(int number){
        return number%3 == 0 ? true : false;
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

    public static void ifHasFocus(boolean ifHas, Activity activity){
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