package com.example.patryk.divideby3;

import java.util.Random;

/**
 * Created by pszen on 14.02.2018.
 */

public class RandomNumber {
    int ranNumInt;
    String ranNumString;
    boolean divisibleByThree;

    public RandomNumber() {
        this.ranNumInt = Numbers.randomNumberGenerator(0,100);
        this.ranNumString = String.valueOf(ranNumInt);
        this.divisibleByThree = Numbers.isDivisibleByThree(ranNumInt);
    }

    public boolean isDivisibleByThree() {
        return divisibleByThree;
    }

    public void setDivisibleByThree(boolean divisibleByThree) {
        this.divisibleByThree = divisibleByThree;
    }

    public int getRanNumInt() {
        return ranNumInt;
    }

    public void setRanNumInt(int ranNumInt) {
        this.ranNumInt = ranNumInt;
    }

    public String getRanNumString() {
        return ranNumString;
    }

    public void setRanNumString(String ranNumString) {
        this.ranNumString = ranNumString;
    }

    public RandomNumber(int minValue,int maxValue){
        this.ranNumInt = Numbers.randomNumberGenerator(minValue,maxValue);
        this.ranNumString = String.valueOf(ranNumInt);
        this.divisibleByThree = Numbers.isDivisibleByThree(ranNumInt);
    }


}
