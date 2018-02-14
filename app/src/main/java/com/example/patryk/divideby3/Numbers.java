package com.example.patryk.divideby3;

import java.util.Random;

/**
 * Created by pszen on 14.02.2018.
 */

public class Numbers {
    public static int randomNumberGenerator(int minValue, int maxValue){
        Random ran = new Random();
        return minValue + ran.nextInt(maxValue - minValue +1);
    }

    public static boolean isDivisibleByThree(int number){
        return number%3 == 0 ? true : false;
    }

    public static String generateSum(){
        int randNum1 = Numbers.randomNumberGenerator(1,99);
        int randNum2 = Numbers.randomNumberGenerator(1,100-randNum1);
        return randNum1 + "+" + randNum2;
    }
}
