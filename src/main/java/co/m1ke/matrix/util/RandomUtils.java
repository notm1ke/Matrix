package co.m1ke.matrix.util;

import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static final Random RANDOM = new Random();

    private static final char[] ALPHABET = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static <T> T getRandom(List<T> array) {
        return array.get(RANDOM.nextInt(array.size() - 1));
    }

    public static int getRandomInt() {
        return RANDOM.nextInt();
    }

    public static int getRandomInt(int i1, int i2) {
        return (int) (Math.random() * ((i2 - i1) + 1)) + i1;
    }

    public static long getNextLong() {
        return RANDOM.nextLong();
    }

    public static double getRandomDouble() {
        return RANDOM.nextDouble();
    }

    public static double getRandomDouble(int i1, int i2) {
        return (Math.random() * ((i2 - i1) + 1)) + i1;
    }

    public static float getRandomFloat() {
        return RANDOM.nextFloat();
    }

    public static boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }

    public static char getRandomLetter() {
        return ALPHABET[RANDOM.nextInt(ALPHABET.length) - 1];
    }

}
