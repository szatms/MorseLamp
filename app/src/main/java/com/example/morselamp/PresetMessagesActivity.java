package com.example.morselamp;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PresetMessagesActivity extends AppCompatActivity {

    private ArrayList<String> messages;
    private ArrayAdapter<String> adapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_messages);

        ListView listView = findViewById(R.id.messagesList);
        prefs = getSharedPreferences("morse_messages", MODE_PRIVATE);
        messages = new ArrayList<>(loadMessages());
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                messages
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String message = messages.get(position);
            LampUtils.flashMessage(message);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            messages.remove(position);
            adapter.notifyDataSetChanged();
            saveMessages();

            return true;
        });

        FloatingActionButton addBtn = findViewById(R.id.addMessageButton);

        addBtn.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New message");

            EditText input = new EditText(this);
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String text = input.getText().toString().trim();
                if (!text.isEmpty()) {
                    messages.add(text);
                    adapter.notifyDataSetChanged();
                    saveMessages();
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

        Button backBtn = findViewById(R.id.pmBackButton);

        backBtn.setOnClickListener(v -> {
            LampUtils.stopFlashing();
            finish();
        });
    }

    private void saveMessages() {
        Set<String> set = new HashSet<>(messages);
        prefs.edit()
                .putStringSet("messages", set)
                .apply();
    }

    private Set<String> loadMessages() {
        return prefs.getStringSet("messages", new HashSet<>());
    }
}