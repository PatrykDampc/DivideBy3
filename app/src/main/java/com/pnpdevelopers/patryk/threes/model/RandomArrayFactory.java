package com.pnpdevelopers.patryk.threes.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by patryk on 02.03.2018.
 */

public class RandomArrayFactory {
    private ArrayList<Integer> randomArrayList;
    private int from;
    private int to;

    public ArrayList<Integer> getRandomArrayList() {
        return randomArrayList;
    }
    public void setRandomArrayList(ArrayList<Integer> randomArrayList) {
        this.randomArrayList = randomArrayList;
    }
    public int getFrom() {
        return from;
    }
    public void setFrom(int from) {
        this.from = from;
    }
    public int getTo() {
        return to;
    }
    public void setTo(int to) {
        this.to = to;
    }

    public RandomArrayFactory(int from, int to) {
        randomArrayList = new ArrayList<>();
        this.from = from;
        this.to = to;
        for (int i = 0 ; i <(to-from) ; i++) {
            randomArrayList.add(from);
            from++;
        }
        Collections.shuffle(randomArrayList);
        setRandomArrayList(randomArrayList);
    }
}