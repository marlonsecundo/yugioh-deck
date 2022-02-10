package com.example.yugiohdeck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;
import com.example.yugiohdeck.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DeckCardDAO {

    private final SQLiteDatabase escreve;
    private final SQLiteDatabase le;

    DBHelper dbHelper;


    public DeckCardDAO(Context context) {

        String columns = "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "deck_id INTEGER NOT NULL,"
                + "card_id INTEGER NOT NULL);";


        dbHelper = new DBHelper(context, "decks_cards", columns);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();

    }

    public boolean salvar(Deck deckCard) {

        // 1. definir o conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("deck_id", deckCard.getName());
        cv.put("card_id", deckCard.getDescription());

        try {
            escreve.insert(dbHelper.tabelName, null, cv);
            Log.i("INFO", "Registro salvo com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao salvar registro: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<DeckCard> listar(int deckIdFilter) {

        List<DeckCard> decksCards = new ArrayList<>();

        // 1. string sql de consulta
        String sql = "SELECT * FROM " + dbHelper.tabelName + "WHERE deck_id=" + deckIdFilter + ";";

        // 2. Cursor para acesso aos dados
        Cursor c = le.rawQuery(sql, null);

        // 3. percorrer o cursor
        c.moveToFirst();
        while (c.moveToNext()) {

            DeckCard deckCard = new DeckCard();

            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            int cardId = c.getInt(c.getColumnIndexOrThrow("card_id"));
            int deckId = c.getInt(c.getColumnIndexOrThrow("deck_id"));

            deckCard.setId(id);
            deckCard.setCardId(cardId);
            deckCard.setDeckId(deckId);

            decksCards.add(deckCard);
        }
        c.close();

        return decksCards;
    }


    public boolean deletar(DeckCard deckCard) {

        // 1. deletar um registro de nota na tabela notas

        try {
            // id do registro que será deletado
            String[] args = { deckCard.getId().toString() };
            escreve.delete(dbHelper.tabelName, "id=?", args);
            Log.i("INFO", "Registro apagado com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro apagar registro!" + e.getMessage());
            return false;
        }
        return true;

    }
}
