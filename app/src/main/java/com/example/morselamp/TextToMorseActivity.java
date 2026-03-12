package com.example.morselamp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TextToMorseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_morse);

        EditText input = findViewById(R.id.inputTextText);
        Button morseBtn = findViewById(R.id.inputTextButton);
        Button backBtn = findViewById(R.id.ttmBackButton);

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
}
