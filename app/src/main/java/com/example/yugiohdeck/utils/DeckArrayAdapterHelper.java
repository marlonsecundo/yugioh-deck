package com.example.yugiohdeck.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.yugiohdeck.models.Deck;

import java.util.List;

public class DeckArrayAdapterHelper {

    public static ArrayAdapter<String> getArrayAdapterByDeckList (List<Deck> decks, Context context)
    {
        String[] decksNames = new String[decks.size()];

        for (int i = 0; i < decks.size(); i++) {
            decksNames[i] = decks.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, decksNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        return  adapter;
    }
}
