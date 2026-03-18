package com.example.morselamp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class TextToMorseActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> speechLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_morse);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        speechLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<String> resultList =
                                result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        if (resultList != null && !resultList.isEmpty()) {
                            EditText input = findViewById(R.id.inputTextText);
                            input.setText(resultList.get(0));
                        }
                    }
                }
        );

        EditText input = findViewById(R.id.inputTextText);
        Button morseBtn = findViewById(R.id.inputTextButton);
        Button backBtn = findViewById(R.id.ttmBackButton);
        ImageButton micBtn = findViewById(R.id.micButton);

        micBtn.setOnClickListener(v -> startSpeechToText());

        morseBtn.setOnClickListener(v -> {
            String text = input.getText().toString();

            if (text.isEmpty())
                return;

            LampUtils.flashMessage(text);
        });

        backBtn.setOnClickListener(v -> {
            LampUtils.stopFlashing();
            finish();
        });
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hu-HU");

        speechLauncher.launch(intent);
    }
}