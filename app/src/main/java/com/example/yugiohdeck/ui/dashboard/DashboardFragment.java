package com.example.yugiohdeck.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.yugiohdeck.R;
import com.example.yugiohdeck.dao.CardDAO;
import com.example.yugiohdeck.dao.DeckCardDAO;
import com.example.yugiohdeck.dao.DeckDAO;
import com.example.yugiohdeck.databinding.FragmentDashboardBinding;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    Spinner deckDropDownList;

    List<Deck> decks = new ArrayList<>();

    CardDAO cardDAO;
    DeckDAO deckDAO;
    DeckCardDAO deckCardDAO;

    Deck currentDeck;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        deckDropDownList = binding.deckDropDownList;

        deckDAO = new DeckDAO(root.getContext());
        cardDAO = new CardDAO(root.getContext());
        deckCardDAO = new DeckCardDAO(root.getContext());

        loadDecks();

        setLayoutContent();

        return root;
    }


    public void loadDecks()
    {
        decks = deckDAO.listar();

        if (decks.size() > 0)
        {
            Deck firstDeck = decks.get(0);

            List<DeckCard> deckCards = deckCardDAO.listar(firstDeck.getId());

            List<Integer> cardIds = new ArrayList<>();

            for (int i = 0; i < deckCards.size(); i++) {
                cardIds.add(deckCards.get(i).getCardId());
            }

            List<Card> cards = cardDAO.listar(cardIds);

            firstDeck.setCards(cards);

            currentDeck = firstDeck;
        }
        else {
            decks = new ArrayList<>();

            Deck mockDeck = new Deck(1, "deck mockado", "deck1");
            List<Card> cards = new ArrayList<>();
            cards.add(new Card(34541863, "\\\"A\\\" Cell Breeding Device", "Spell Card", "During each of your Standby Phases, put 1 A-Counter on 1 face-up monster your opponent controls", 0, 0, 0, "Continuous", "", ""));

            mockDeck.setCards(cards);
            decks.add(mockDeck);

            currentDeck = mockDeck;
        }
    }

    public void setLayoutContent()
    {
        setSpinnerContent();
    }

    public void setSpinnerContent() {

        String[] decksNames = new String[decks.size()];


        for (int i = 0; i < decks.size(); i++) {
            decksNames[i] = decks.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, decksNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deckDropDownList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}