package com.example.yugiohdeck.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yugiohdeck.DeckListFragment;
import com.example.yugiohdeck.NewDeckActivity;
import com.example.yugiohdeck.R;
import com.example.yugiohdeck.dao.CardDAO;
import com.example.yugiohdeck.dao.DeckCardDAO;
import com.example.yugiohdeck.dao.DeckDAO;
import com.example.yugiohdeck.databinding.FragmentDashboardBinding;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;
import com.example.yugiohdeck.utils.DAOCallback;
import com.example.yugiohdeck.utils.DeckArrayAdapterHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    Spinner deckDropDownList;
    FloatingActionButton newDeckButton;
    Button deleteButton;
    Button updateButton;
    TextView descTextView;

    List<Deck> decks = new ArrayList<>();

    CardDAO cardDAO;
    DeckDAO deckDAO;
    DeckCardDAO deckCardDAO;

    Deck currentDeck;
    DeckListFragment deckListFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        descTextView = binding.descTextView;

        deleteButton = binding.deleteButton;
        deleteButton.setOnClickListener(onDeleteClick);

        updateButton = binding.updateButton;
        updateButton.setOnClickListener(onUpdateClick);

        deckDropDownList = binding.deckDropDownList;
        deckDropDownList.setOnItemSelectedListener(onDeckItemChange);

        newDeckButton = binding.newDeckButton;
        newDeckButton.setOnClickListener(onNewDeckClick);

        deckListFragment = (DeckListFragment) this.getChildFragmentManager().findFragmentById(R.id.deckListFragment);

        deckDAO = new DeckDAO(root.getContext());
        cardDAO = new CardDAO(root.getContext());
        deckCardDAO = new DeckCardDAO(root.getContext());

        loadDecks();


        return root;
    }


    public void loadDecks()
    {
        deckDAO.listar(result -> {
            decks = (List<Deck>) result;

            if (decks.size() > 0)
            {
                setSpinnerContent();
            }
        });

    }

    public void loadCards(Deck deck, DAOCallback callback)
    {
        deckCardDAO.listar(deck.getId(), deckCardResult -> {

            List<DeckCard> deckCards = (List<DeckCard>) deckCardResult;

            List<Integer> cardIds = new ArrayList<>();

            for (int i = 0; i < deckCards.size(); i++) {
                cardIds.add(deckCards.get(i).getCardId());
            }

            cardDAO.listar(cardIds, result1 -> {

                List<Card> cards = (List<Card>) result1;

                deck.setCards(cards);

                currentDeck = deck;

                callback.onJobFinish(deck);
            });

        });
    }


    public void setSpinnerContent() {

        ArrayAdapter<String> adapter = DeckArrayAdapterHelper.getArrayAdapterByDeckList(decks, getContext());

        deckDropDownList.setAdapter(adapter);
    }

    public void setCards() {

        if (currentDeck != null)
        {
            deckListFragment.setContent(currentDeck.getCards());
            descTextView.setText(currentDeck.getDescription());
        }
    }


    View.OnClickListener onNewDeckClick = view -> {

        Intent intent = new Intent(this.getContext(), NewDeckActivity.class);
        startActivity(intent);

    };

    AdapterView.OnItemSelectedListener onDeckItemChange = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            currentDeck = decks.get(i);

            loadCards(currentDeck, result -> {
                setCards();
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    View.OnClickListener onDeleteClick = view -> {

        if (currentDeck != null)
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Deseja apagar o Deck: " + currentDeck.getName() + "?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "SIM",
                    (dialog, id) -> {
                        deckDAO.deletar(currentDeck, result -> {
                            loadDecks();
                        });

                        dialog.cancel();
                    });

            builder1.setNegativeButton(
                    "NÃO",
                    (dialog, id) -> dialog.cancel());


            AlertDialog alert11 = builder1.create();
            alert11.show();


        }
    };

    View.OnClickListener onUpdateClick = view -> {
        if (currentDeck != null)
        {
            Intent i = new Intent(getContext(), NewDeckActivity.class);
            i.putExtra("deck", currentDeck);
            startActivity(i);
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume(){
        super.onResume();

        loadDecks();
    }
}