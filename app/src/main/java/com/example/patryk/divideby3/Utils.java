package com.example.patryk.divideby3;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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
}
