package com.pnpdevelopers.patryk.threes.util;

/**
 * Created by patryk on 04.03.2018.
 */

public class Conditions {

    public static boolean isDivisibleByThree(int number){
        return number%3 == 0 ? true : false;
    }

    public static boolean containsThree(int number){ return String.valueOf(number).contains("3");}

    public static boolean successCondition(int number){
        return isDivisibleByThree(number) || containsThree(number);
    }


}
