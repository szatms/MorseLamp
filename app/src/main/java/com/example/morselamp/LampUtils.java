package com.example.morselamp;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.hardware.camera2.CameraManager;

public class LampUtils {
    //thread megszakításhoz
    private static volatile boolean running = false;

    // Morse időzítés
    private static final int DOT = 200;
    private static final int DASH = DOT * 3;
    private static final int SYMBOL_SPACE = DOT;
    private static final int LETTER_SPACE = DOT * 3;
    private static final int WORD_SPACE = DOT * 7;

    private static CameraManager cameraManager;
    private static String cameraId;

    // Morse dictionary
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
    }

    public static void stopFlashing() {
        running = false;
        setFlash(false);
    }

    public static void init(Context context) {
        try {
            cameraManager =
                    (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

            cameraId = cameraManager.getCameraIdList()[0];

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setFlash(boolean on) {
        try {
            cameraManager.setTorchMode(cameraId, on);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void flashDot() throws InterruptedException {
        if (!running) return;

        setFlash(true);
        Thread.sleep(DOT);
        setFlash(false);
    }

    private static void flashDash() throws InterruptedException {
        if (!running) return;

        setFlash(true);
        Thread.sleep(DASH);
        setFlash(false);
    }

    public static void flashMessage(String text) {
        if (running) return;
        running = true;

        new Thread(() -> {

            String textUpperCase = text.toUpperCase();

            try {

                for (char c : textUpperCase.toCharArray()) {
                    if (!running) return;
                    if (c == ' ') {
                        Thread.sleep(WORD_SPACE);
                        continue;
                    }

                    String code = MORSE.get(c);
                    if (code == null) continue;

                    for (char symbol : code.toCharArray()) {
                        if (!running) return;
                        if (symbol == '.')
                            flashDot();
                        else
                            flashDash();

                        Thread.sleep(SYMBOL_SPACE);
                    }

                    Thread.sleep(LETTER_SPACE);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                running = false;
                setFlash(false);
            }

        }).start();
    }
}