package com.example.yugiohdeck;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.yugiohdeck.dao.DeckDAO;
import com.example.yugiohdeck.databinding.ActivityNewDeckBinding;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.utils.DAOCallback;
import com.google.android.material.snackbar.Snackbar;

public class NewDeckActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewDeckBinding binding;

    TextView newDeckTextView;

    EditText titleTextInput;
    EditText descTextInput;
    Button confirmButton;

    DeckDAO deckDAO;

    Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewDeckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        newDeckTextView = binding.txtAddNewDeck;

        titleTextInput = binding.titleTextInput;
        descTextInput = binding.descTextInput;

        confirmButton = binding.newDeckConfirmButton;
        confirmButton.setOnClickListener(onConfirmClick);

        deckDAO = new DeckDAO(getApplicationContext());

        setContent();
    }

    public void setContent()
    {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            newDeckTextView.setText("Atualizar Deck");

            deck = (Deck) extras.get("deck");

            titleTextInput.setText(deck.getName());
            descTextInput.setText(deck.getDescription());
        }
    }

    View.OnClickListener onConfirmClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = titleTextInput.getText().toString();
            String desc = descTextInput.getText().toString();

            if (title.length() < 1 || desc.length() < 1)
            {


                Toast.makeText(getApplicationContext(), "Preencha todos os campos!!!", Toast.LENGTH_LONG).show();

                return;
            }

            if (deck == null)
            {
                deckDAO.salvar(new Deck(desc, title), result -> {


                    finish();
                });
            }
            else {

                deck.setName(title);
                deck.setDescription(desc);

                deckDAO.atualizar(deck, result -> {

                    Snackbar snackbar = Snackbar
                            .make(binding.getRoot(), "Deck atualizado!", Snackbar.LENGTH_LONG)
                            .setAction("Voltar", view1 -> {
                                finish();
                            });

                    snackbar.show();

                });
            }


        }
    };
}