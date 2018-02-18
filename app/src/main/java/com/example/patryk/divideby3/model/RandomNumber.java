package com.example.patryk.divideby3.model;

import com.example.patryk.divideby3.util.Utils;

/**
 * Created by pszen on 14.02.2018.
 */

public class RandomNumber {
   private int ranNumInt;
   private String ranNumString;
   private boolean divisibleByThree;
   private boolean containsThree;
   private boolean winCondition;

    public RandomNumber() {
        this.ranNumInt = Utils.randomNumberGenerator(3,100);
    }

    public RandomNumber(int minValue,int maxValue){
        this.ranNumInt = Utils.randomNumberGenerator(minValue,maxValue);

    }

    public boolean getWinCondition() {
        return containsThree() || isDivisibleByThree();
    }

    public void setWinCondition(boolean winCondition) {
        this.winCondition = winCondition;
    }

    public boolean containsThree() {
        return String.valueOf(ranNumInt).contains("3");
    }

    public void setContainsThree(boolean containsThree) {
        this.containsThree = containsThree;
    }

    public boolean isDivisibleByThree() {
        return ranNumInt%3==0;
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
        return String.valueOf(ranNumInt);
    }

    public void setRanNumString(String ranNumString) {
        this.ranNumString = ranNumString;
    }




}
