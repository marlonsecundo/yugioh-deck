package com.example.yugiohdeck.utils;

import com.example.yugiohdeck.models.Card;

public interface CardSelectCallback {

    void onCardSelect(Card card, boolean selected);
}
