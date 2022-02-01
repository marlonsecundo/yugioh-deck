package com.example.yugiohdeck.models;

import java.io.Serializable;

public class DeckCard implements Serializable {

    Integer id;
    Integer deckId;
    Integer cardId;

    public DeckCard() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DeckCard(int deckId, int cardId) {
        this.deckId = deckId;
        this.cardId = cardId;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
