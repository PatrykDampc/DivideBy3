package com.pnpdevelopers.patryk.threes;

import com.pnpdevelopers.patryk.threes.model.RandomArrayFactory;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by pszen on 08.03.2018.
 */

public class RandomArrayFactoryTest {
    int from = 0 ;
    int to = 100;
    RandomArrayFactory randomArrayFactory = new RandomArrayFactory(from,to);
    int[] array = randomArrayFactory.getNumbertab();

    @Test
    public void numbersInArrayShouldBeWithinTheScope() {
        for (int num : array) {
            Assert.assertTrue(num >= from && num <= to);
        }
    }

    @Test
    public void everyNumberInArrayShouldBeDifferent() {
        boolean condition = true;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    condition = false;
                    break;
                }
            }
        }
        Assert.assertTrue(condition);
    }
}
