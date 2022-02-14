package com.example.yugiohdeck.holders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yugiohdeck.DescCard;
import com.example.yugiohdeck.databinding.DeckListItemBinding;
import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.placeholder.PlaceholderContent;
import com.example.yugiohdeck.tasks.DownloadImageTask;

public class DeckListViewHolder extends RecyclerView.ViewHolder {

    ImageView cardImageView;
    Card card;

    public DeckListViewHolder(DeckListItemBinding binding) {
        super(binding.getRoot());
        cardImageView = binding.cardImageView;

        cardImageView.setOnClickListener(onClickListener);
    }


    public void setContent(Card card){
        this.card = card;

        DownloadImageTask task = new DownloadImageTask(cardImageView);
        task.execute(card.getSmallImgUrl());
    }

    View.OnClickListener onClickListener = view -> {

        Intent it = new Intent(view.getContext(), DescCard.class);
        it.putExtra("card", card);
        view.getContext().startActivity(it);
    };


}