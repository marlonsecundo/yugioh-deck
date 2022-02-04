package com.example.yugiohdeck.holders;

import static android.view.View.*;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yugiohdeck.databinding.CardItemBinding;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.services.CardService;
import com.example.yugiohdeck.tasks.DownloadImageTask;

public class CardListViewHolder extends RecyclerView.ViewHolder {
    public final TextView nameTextView;
    public final TextView typeTextView;
    public final TextView raceTextView;
    public final TextView atkTextView;
    public final TextView defTextView;
    public final ImageView cardImageView;

    Card card;


    public CardListViewHolder(CardItemBinding binding) {
        super(binding.getRoot());

        nameTextView = binding.nameTextView;
        typeTextView = binding.typeTextView;
        raceTextView = binding.raceTextView;
        atkTextView = binding.atkTextView;
        defTextView = binding.defTextView;
        cardImageView = binding.cardImageView;

    }

    public OnClickListener onShowClick = new OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };

    public void setContent(Card card)
    {
        this.card = card;

        Log.e("YUGI", card.getImgUrl());

        nameTextView.setText(card.getName());
        typeTextView.setText(card.getType());
        raceTextView.setText(card.getRace());
        atkTextView.setText(String.valueOf(card.getAtk()));
        defTextView.setText(String.valueOf(card.getDef()));

        new DownloadImageTask(cardImageView).execute(card.getSmallImgUrl());


    }




}