package com.example.yugiohdeck.holders;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yugiohdeck.databinding.DeckListItemBinding;
import com.example.yugiohdeck.placeholder.PlaceholderContent;

public class DeckListViewHolder extends RecyclerView.ViewHolder {
    public final TextView mIdView;
    public final TextView mContentView;
    public PlaceholderContent.PlaceholderItem mItem;

    public DeckListViewHolder(DeckListItemBinding binding) {
        super(binding.getRoot());
        mIdView = binding.itemNumber;
        mContentView = binding.content;
    }


}