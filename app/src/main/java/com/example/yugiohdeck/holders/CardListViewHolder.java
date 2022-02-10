package com.example.yugiohdeck.holders;

import static android.view.View.*;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yugiohdeck.OpenCamera;
import com.example.yugiohdeck.databinding.CardItemBinding;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.services.CardService;
import com.example.yugiohdeck.tasks.DownloadImageTask;
import com.example.yugiohdeck.utils.CardSelectCallback;

public class CardListViewHolder extends RecyclerView.ViewHolder  {
    public final TextView nameTextView;
    public final TextView typeTextView;
    public final TextView raceTextView;
    public final TextView atkTextView;
    public final TextView defTextView;
    public final ImageView cardImageView;
    public CardView cardView;

    Card card;
    boolean selected = false;

    CardSelectCallback cardSelectCallback;


    public CardListViewHolder(CardItemBinding binding) {
        super(binding.getRoot());

        nameTextView = binding.nameTextView;
        typeTextView = binding.typeTextView;
        raceTextView = binding.raceTextView;
        atkTextView = binding.atkTextView;
        defTextView = binding.defTextView;
        cardImageView = binding.cardImageView;
        cardView = binding.cardView;

        cardView.setOnLongClickListener(onCardLongPress);

    }

    public OnClickListener onShowClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Tarefa foi removida", Toast.LENGTH_SHORT).show();
        }
    };


    public void setContent(Card card, CardSelectCallback cardSelectCallback)
    {
        this.card = card;
        this.cardSelectCallback = cardSelectCallback;

        Log.e("YUGI", card.getImgUrl());

        nameTextView.setText(card.getName());
        typeTextView.setText(card.getType());
        raceTextView.setText(card.getRace());
        atkTextView.setText(String.valueOf(card.getAtk()));
        defTextView.setText(String.valueOf(card.getDef()));

        new DownloadImageTask(cardImageView).execute(card.getSmallImgUrl());
    }

    View.OnLongClickListener onCardLongPress = view -> {

        selected = !selected;

        if (selected) {
            cardView.setCardBackgroundColor(Color.parseColor("#FF9FD3CF"));
        }
        else {
            cardView.setCardBackgroundColor(Color.WHITE);
        }

        cardSelectCallback.onCardSelect(this.card, selected);


        return false;
    };

}