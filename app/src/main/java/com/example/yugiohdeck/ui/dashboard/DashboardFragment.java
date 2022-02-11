package com.example.yugiohdeck.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yugiohdeck.DeckListFragment;
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
    DeckListFragment deckListFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        deckDropDownList = binding.deckDropDownList;
        deckListFragment = (DeckListFragment) this.getChildFragmentManager().findFragmentById(R.id.deckListFragment);

        deckDAO = new DeckDAO(root.getContext());
        cardDAO = new CardDAO(root.getContext());
        deckCardDAO = new DeckCardDAO(root.getContext());

        loadDecks();

        setLayoutContent();

        return root;
    }


    public void loadDecks()
    {
        deckDAO.listar(result -> {
            decks = (List<Deck>) result;

            if (decks.size() > 0)
            {
                Deck firstDeck = decks.get(0);

                 deckCardDAO.listar(firstDeck.getId(), deckCardResult -> {

                     List<DeckCard> deckCards = (List<DeckCard>) deckCardResult;

                     List<Integer> cardIds = new ArrayList<>();

                     for (int i = 0; i < deckCards.size(); i++) {
                         cardIds.add(deckCards.get(i).getCardId());
                     }

                     cardDAO.listar(cardIds, result1 -> {

                         List<Card> cards = (List<Card>) result1;

                         firstDeck.setCards(cards);

                         currentDeck = firstDeck;
                     });

                 });



            }
        });


    }

    public void setLayoutContent()
    {
        setSpinnerContent();
        setCards();
    }

    public void setSpinnerContent() {

        ArrayAdapter<String> adapter = DeckArrayAdapterHelper.getArrayAdapterByDeckList(decks, getContext());

        deckDropDownList.setAdapter(adapter);
    }

    public void setCards() {

        if (currentDeck != null)
        {
            deckListFragment.setContent(currentDeck.getCards());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        loadDecks();
        setLayoutContent();

    }
}