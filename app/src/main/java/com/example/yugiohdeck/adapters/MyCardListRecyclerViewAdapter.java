package com.example.yugiohdeck.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yugiohdeck.holders.CardListViewHolder;
import com.example.yugiohdeck.databinding.CardItemBinding;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.utils.CardSelectCallback;

import java.util.List;


public class MyCardListRecyclerViewAdapter extends RecyclerView.Adapter<CardListViewHolder> {

    private final List<Card> cards;

    public MyCardListRecyclerViewAdapter(List<Card> items) {
        cards = items;
    }
    private CardSelectCallback cardSelectCallback;

    @Override
    public CardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CardListViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }


    public void setCardSelectCallback(CardSelectCallback cardSelectCallback)
    {
        this.cardSelectCallback = cardSelectCallback;
    }


    @Override
    public void onBindViewHolder(final CardListViewHolder holder, int position) {

        holder.setContent(cards.get(position), cardSelectCallback);
    }



@Override
    public int getItemCount() {
        return cards.size();
    }

}