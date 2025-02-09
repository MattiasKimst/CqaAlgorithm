package main.data;

import java.util.Random;

/**
 * A helper class for generating random alphanumeric attribute values of length 10
 */
public class RandomAttributeValueGenerator {

    private final static int LENGTH = 10;
    private final static Random random = new Random();
    private final static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomAlphanumericString() {

        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }

}
