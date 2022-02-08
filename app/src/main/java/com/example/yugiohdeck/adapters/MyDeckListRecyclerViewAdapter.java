package com.example.yugiohdeck.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yugiohdeck.holders.DeckListViewHolder;
import com.example.yugiohdeck.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.yugiohdeck.databinding.DeckListItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDeckListRecyclerViewAdapter extends RecyclerView.Adapter<DeckListViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyDeckListRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public DeckListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new DeckListViewHolder(DeckListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final DeckListViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}