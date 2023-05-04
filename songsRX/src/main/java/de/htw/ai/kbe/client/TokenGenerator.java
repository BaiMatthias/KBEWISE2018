package de.htw.ai.kbe.client;

import java.util.Random;

public class TokenGenerator {


    public static String generateToken(String userId) {
        String userIdHash = String.valueOf(userId.hashCode());
        // This will give value from 0 to 9999;
        Random rand = new Random();
        int value = rand.nextInt(10000);
        String token = userIdHash + String.valueOf(value);
        //Create a String alphabet with the chars that you want.
        //Say N = alphabet.length()
        //Then we can ask a java.util.Random for an int x = nextInt(N)
        //alphabet.charAt(x) is a random char from the alphabet.
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < rand.nextInt(16) + 5; i++) {
            int random_from_aplhabet = rand.nextInt(alphabet.length());
            Character c = alphabet.charAt(random_from_aplhabet);
            int random_spot = rand.nextInt(token.length() - 1);
            token = token.substring(0, random_spot) + c + token.substring(random_spot, token.length());
        }
        return token;
    }
}
