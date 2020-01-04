package com.gmail.pashasimonpure.service.util;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static int getRandomIntBetween(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static int getRandomInt() {
        return random.nextInt();
    }
}