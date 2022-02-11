package com.example.yugiohdeck;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yugiohdeck.adapters.MyCardListRecyclerViewAdapter;
import com.example.yugiohdeck.dao.CardDAO;
import com.example.yugiohdeck.holders.CardListViewHolder;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;
import com.example.yugiohdeck.utils.CardSelectCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CardListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    CardListViewHolder cardListViewHolder;
    CardDAO cardDAO;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CardListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CardListFragment newInstance(int columnCount) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_item_list, container, false);

        cardDAO = new CardDAO(getContext());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            ArrayList<Card> cards = new ArrayList<>();
            recyclerView.setAdapter(new MyCardListRecyclerViewAdapter(cards));

            List<Integer> cardIds = new ArrayList<>();
            /*List<Card> mCards = cardDAO.listar(cardIds, );*/

            /* cardListViewHolder.implementaAoClicarNoItem(new CardListViewHolder.AoClicarNoItem() {
                @Override
                public void clicouNaTarefa(int pos) {

                    Card newCard = new Card();
                    newCard = mCards.get(pos);

                    Intent it = new Intent(getActivity().getApplicationContext(), OpenCamera.class);
                    startActivity(it);
                }
            });*/




        }
        return view;
    }

    public void setContent(List<Card> cards, CardSelectCallback cardSelectCallback) {

        MyCardListRecyclerViewAdapter adapter = new MyCardListRecyclerViewAdapter(cards);
        adapter.setCardSelectCallback(cardSelectCallback);

        recyclerView.setAdapter(adapter);
    }

}