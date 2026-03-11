package com.example.morselamp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TextToMorseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_morse);

        Button backBtn = findViewById(R.id.ttmBackButton);

        backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}
