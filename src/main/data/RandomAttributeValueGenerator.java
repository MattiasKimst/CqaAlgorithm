package main.data;

import java.util.Random;

public class RandomAttributeValueGenerator {
    public static String generateRandomStringOfLengthBetween4And10() {
        Random random = new Random();
        int length = 4 + random.nextInt(7);

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
