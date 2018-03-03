package com.pnpdevelopers.patryk.threes.model;

import static com.pnpdevelopers.patryk.threes.util.Utils.LEVEL_FIVE;
import static com.pnpdevelopers.patryk.threes.util.Utils.LEVEL_FOUR;
import static com.pnpdevelopers.patryk.threes.util.Utils.LEVEL_ONE;
import static com.pnpdevelopers.patryk.threes.util.Utils.LEVEL_SIX;
import static com.pnpdevelopers.patryk.threes.util.Utils.LEVEL_THREE;
import static com.pnpdevelopers.patryk.threes.util.Utils.LEVEL_TWO;
import static com.pnpdevelopers.patryk.threes.util.Utils.generateRanNum;

/**
 * Created by patryk on 02.03.2018.
 */

public class RandomArray {
    private int[] randomArrayList;
    private int arrayVolume;

    public int[] getRandomArrayList() {
        return randomArrayList;
}

    public void setRandomArrayList(int[] randomArrayList) {
        this.randomArrayList = randomArrayList;
    }

    public int getArrayVolume() {
        return arrayVolume;
    }

    public void setArrayVolume(int arrayVolume) {
        this.arrayVolume = arrayVolume;
    }


    public RandomArray(int[] randomArrayList, int arrayVolume) {
        this.randomArrayList = randomArrayList;
        this.arrayVolume = arrayVolume;

        randomArrayList = new int[arrayVolume];
        int min = 3;
        int max = 100;
        for (int i = 0; i < arrayVolume; i++) {
            switch (i) {
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
            randomArrayList[i] = generateRanNum(min, max);
        }
    }






}
