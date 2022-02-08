package com.example.yugiohdeck.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Response;
import com.example.yugiohdeck.CardListFragment;
import com.example.yugiohdeck.R;
import com.example.yugiohdeck.databinding.FragmentHomeBinding;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.services.CardService;
import com.example.yugiohdeck.utils.CardSelectCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    EditText searchEditText;
    Button searchButton;
    Button addToDeckButton;

    CardListFragment cardListFragment;
    CardService cardService;
    List<Card> selectedCards = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        searchEditText = binding.searchEditText;
        searchButton = binding.searchButton;
        searchButton.setOnClickListener(onSearchButtonClick);

        addToDeckButton = binding.addToDeckButton;


        cardListFragment = (CardListFragment) this.getChildFragmentManager().findFragmentById(R.id.cardListFragment);


        cardService = new CardService(root.getContext());
         Map<String, String> params =  new HashMap<String, String>() {{
             put("fname", "Dark Magician");
         }};
        cardService.fetchCards(params, onFetchCardsResponse, onFetchCardsError);

        return root;
    }


    Response.Listener<String> onFetchCardsResponse = response -> {

        try {
            JSONObject jsonData = new JSONObject(response);
            List<Card> cards = Card.fromJSONList(jsonData.getJSONArray("data"));
           cardListFragment.setContent(cards, this.onCardSelect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    Response.ErrorListener onFetchCardsError = error -> {


    };


    View.OnClickListener onSearchButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (searchEditText.getText().equals(""))
                return;


            Map<String, String> params =  new HashMap<String, String>() {{
                put("fname", searchEditText.getText().toString());
            }};

            cardService.fetchCards(params, onFetchCardsResponse, onFetchCardsError);

        }
    };


    CardSelectCallback onCardSelect = new CardSelectCallback() {
        @Override
        public void onCardSelect(Card card, boolean selected) {

            if (selected)
            {
                selectedCards.add(card);
            }
            else {
                selectedCards.remove(card);
            }

            if (selectedCards.size() > 0)
            {
                addToDeckButton.setVisibility(View.VISIBLE);
            }
            else{
                addToDeckButton.setVisibility(View.GONE);
            }

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}