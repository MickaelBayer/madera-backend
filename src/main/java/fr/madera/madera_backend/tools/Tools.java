package fr.madera.madera_backend.tools;

import java.util.Random;

public class Tools {


    public static String GeneratePassword(int length) {
        String cap_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String small_letter = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String finalString = cap_letter + small_letter +
                numbers;

        Random random = new Random();

        char[] password = new char[length];

        for (int i = 0; i < length; i++)
        {
            password[i] = finalString.charAt(random.nextInt(finalString.length()));

        }
        return String.copyValueOf(password);
    }

}
