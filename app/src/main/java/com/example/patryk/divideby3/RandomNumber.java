package com.example.patryk.divideby3;

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
        this.ranNumInt = Numbers.randomNumberGenerator(1,100);
        this.ranNumString = String.valueOf(ranNumInt);
        this.divisibleByThree = Numbers.isDivisibleByThree(ranNumInt);
        this.containsThree = ranNumString.contains("3");
        this.winCondition = divisibleByThree || containsThree;
    }

    public boolean getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(boolean winCondition) {
        this.winCondition = winCondition;
    }

    public boolean containsThree() {
        return containsThree;
    }

    public void setContainsThree(boolean containsThree) {
        this.containsThree = containsThree;
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
