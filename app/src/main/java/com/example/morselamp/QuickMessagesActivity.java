package com.example.morselamp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QuickMessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_messages);

        Button helloBtn = findViewById(R.id.btnHello);
        Button sosBtn = findViewById(R.id.btnSOS);
        Button helpMeBtn = findViewById(R.id.btnHelpMe);
        Button backBtn = findViewById(R.id.qmBackButton);

        helloBtn.setOnClickListener(v -> {
            LampUtils.flashMessage("Hello");
        });

        sosBtn.setOnClickListener(v -> {
            LampUtils.flashMessage("SOS");
        });

        helpMeBtn.setOnClickListener(v -> {
            LampUtils.flashMessage("Help me");
        });

        backBtn.setOnClickListener(v -> {
            LampUtils.stopFlashing();
            finish();
        });
    }
}
