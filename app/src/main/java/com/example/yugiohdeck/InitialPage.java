package com.example.yugiohdeck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitialPage extends AppCompatActivity {

    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = view -> {
        Intent it = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(it);
    };
}