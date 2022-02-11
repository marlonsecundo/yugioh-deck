package com.example.yugiohdeck;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.yugiohdeck.dao.DeckDAO;
import com.example.yugiohdeck.databinding.ActivityNewDeckBinding;
import com.example.yugiohdeck.models.Deck;

public class NewDeckActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewDeckBinding binding;

    EditText titleTextInput;
    EditText descTextInput;
    Button confirmButton;

    DeckDAO deckDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewDeckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        titleTextInput = binding.titleTextInput;
        descTextInput = binding.descTextInput;

        confirmButton = binding.newDeckConfirmButton;
        confirmButton.setOnClickListener(onConfirmClick);

        deckDAO = new DeckDAO(getApplicationContext());

    }

    View.OnClickListener onConfirmClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = titleTextInput.getText().toString();
            String desc = descTextInput.getText().toString();

            deckDAO.salvar(new Deck(desc, title), result -> {

                finish();
            });
        }
    };
}