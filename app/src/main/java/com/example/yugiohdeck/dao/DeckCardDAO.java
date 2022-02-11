package com.example.yugiohdeck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.models.DeckCard;
import com.example.yugiohdeck.tasks.DBTask;
import com.example.yugiohdeck.utils.DAOCallable;
import com.example.yugiohdeck.utils.DAOCallback;
import com.example.yugiohdeck.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DeckCardDAO {

    public static String TABLE_NAME = "decks_cards";
    public static String COLUMNS = "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "deck_id INTEGER NOT NULL,"
            + "card_id INTEGER NOT NULL);";

    public static String CREATE_DECK_CARD_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " + COLUMNS;


    DBHelper dbHelper;


    public DeckCardDAO(Context context) {

        dbHelper = DBHelper.getInstance(context);

    }

    public void salvar(DeckCard deckCard, DAOCallback callback) {

        new DBTask(new DAOCallable() {
            @Override
            public Object run() {

                // 1. definir o conteudo a ser salvo
                ContentValues cv = new ContentValues();
                cv.put("deck_id", deckCard.getDeckId());
                cv.put("card_id", deckCard.getCardId());

                try {
                    SQLiteDatabase write = dbHelper.getWritableDatabase();

                    write.insertOrThrow(TABLE_NAME, null, cv);
                    Log.i("INFO", "Registro salvo com sucesso!");
                } catch (Exception e) {
                    Log.i("INFO", "Erro ao salvar registro: " + e.getMessage());
                    return false;
                }
                return true;

            }
        }, callback).execute();


    }

    public void listar(int deckIdFilter, DAOCallback callback) {

        new DBTask(new DAOCallable() {
            @Override
            public Object run() {

                SQLiteDatabase read = dbHelper.getReadableDatabase();

                List<DeckCard> decksCards = new ArrayList<>();

                // 1. string sql de consulta
                String sql = "SELECT * FROM " + TABLE_NAME + " WHERE deck_id=" + deckIdFilter + ";";

                // 2. Cursor para acesso aos dados
                Cursor c = read.rawQuery(sql, null);

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
        }, callback).execute();

    }


    public boolean deletar(DeckCard deckCard) {

        // 1. deletar um registro de nota na tabela notas

        try {
            SQLiteDatabase write = dbHelper.getWritableDatabase();

            // id do registro que será deletado
            String[] args = { deckCard.getId().toString() };
            write.delete(TABLE_NAME, "id=?", args);
            Log.i("INFO", "Registro apagado com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro apagar registro!" + e.getMessage());
            return false;
        }
        return true;

    }
}
