package com.example.inventorymanagement;

import java.util.Random;

public class RandomString {

    private final String NUMBERS= "0123456789";
    private final char[] NUMERIC = NUMBERS.toCharArray();

    public String generateNumber(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<length; i++) {
            result.append(NUMERIC[new Random().nextInt(NUMERIC.length)]);
        }
        return result.toString();
    }
}
