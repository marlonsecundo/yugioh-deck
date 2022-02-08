package com.example.yugiohdeck;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DescCard extends AppCompatActivity {

    private Button btnAddToDeck;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_card);

        btnAddToDeck = findViewById(R.id.btnAddToDeck);

        btnAddToDeck.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }

        });

    }

}
