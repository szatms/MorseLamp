package com.example.morselamp;

import java.util.HashMap;
import java.util.Map;

public class LampUtils {
    public static final Map<Character, String> MORSE = new HashMap<>();
    static {
        String[] codes = {
                ".-","-...","-.-.","-..",".","..-.","--.","....","..",".---",
                "-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-",
                "..-","...-",".--","-..-","-.--","--.."
        };

        for (int i = 0; i < codes.length; i++) {
            MORSE.put((char) ('A' + i), codes[i]);
        }

        String[] numbers = {
                "-----",".----","..---","...--","....-",
                ".....","-....","--...","---..","----."
        };

        for (int i = 0; i < numbers.length; i++) {
            MORSE.put((char) ('0' + i), numbers[i]);
        }

        MORSE.put(' ', " ");
    };



}
