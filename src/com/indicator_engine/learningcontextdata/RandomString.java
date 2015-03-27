package com.indicator_engine.learningcontextdata;
import java.util.Random;

/**
 * Created by Tanmaya Mahapatra on 22-02-2015.
 */
public class RandomString {

    public static String randomString(final int length) {
        Random prng = new Random();
        char[] subset = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] chars = new char[length];
        final int subsetLength = subset.length;
        for (int i = 0; i < length; i++) {
            int index = prng.nextInt(subsetLength);
            chars[i] = subset[index];
        }
        return new String(chars);
    }
}