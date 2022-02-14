package com.example.yugiohdeck.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yugiohdeck.R;
import com.example.yugiohdeck.dao.CardDAO;
import com.example.yugiohdeck.dao.DeckCardDAO;
import com.example.yugiohdeck.dao.DeckDAO;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;
import com.example.yugiohdeck.utils.DAOCallback;
import com.example.yugiohdeck.utils.DeckArrayAdapterHelper;

import java.util.List;

public class AddCardsDialogFragment extends Dialog {


    Button confirmButton;
    Button cancelButton;
    Spinner deckSpinner;

    List<Card> cards;
    List<Deck> decks;

    DeckDAO deckDAO;
    CardDAO cardDAO;
    DeckCardDAO deckCardDAO;

    public AddCardsDialogFragment(Context context, List<Card> cards) {
        super(context);
        this.cards = cards;

        cardDAO = new CardDAO(context);
        deckCardDAO = new DeckCardDAO(context);
        deckDAO = new DeckDAO(context);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_cards_dialog);

        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(onConfirmClick);
        cancelButton.setOnClickListener(onCancelClick);

        deckSpinner = findViewById(R.id.deckSpinner);


        deckDAO.listar(result -> {
            decks = (List<Deck>) result;

            ArrayAdapter<String> adapter = DeckArrayAdapterHelper.getArrayAdapterByDeckList(decks, getContext());

            deckSpinner.setAdapter(adapter);
        });


    }

    View.OnClickListener onConfirmClick = view -> {

        for (int i = 0; i < cards.size(); i++) {

            Deck selectedDeck = decks.get(deckSpinner.getSelectedItemPosition());

            int finalI = i;


            cardDAO.salvar(cards.get(i), new DAOCallback() {
                @Override
                public void onJobFinish(Object result) {
                    int id = (int) result;

                    cards.get(finalI).setId(id);

                    deckCardDAO.salvar(new DeckCard(selectedDeck.getId(), cards.get(finalI).getId()), result1 -> {

                        Toast.makeText(getContext(), "Carta adicionada!!!", Toast.LENGTH_LONG).show();
                        closeDialog();

                    });
                }
            });

        }



    };
    public void closeDialog (){
        this.dismiss();
    }

    View.OnClickListener onCancelClick = view -> {
        closeDialog();
    };


}