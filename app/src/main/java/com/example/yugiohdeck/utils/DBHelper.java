package com.example.yugiohdeck.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.yugiohdeck.dao.CardDAO;
import com.example.yugiohdeck.dao.DeckCardDAO;
import com.example.yugiohdeck.dao.DeckDAO;

public class DBHelper extends SQLiteOpenHelper {


    private static DBHelper instance;
    public static DBHelper getInstance(@Nullable Context context)
    {
        if (instance == null)
        {
            instance = new DBHelper(context);
        }

        return instance;
    }



    public static int VERSION = 1;
    public static String NOME_BD = "bd_yugioh.bd";


    private DBHelper(@Nullable Context context) {
        super(context, NOME_BD, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CardDAO.CREATE_CARD_TABLE_SQL);
            db.execSQL(DeckDAO.CREATE_DECK_TABLE_SQL);
            db.execSQL(DeckCardDAO.CREATE_DECK_CARD_TABLE_SQL);

            Log.i("INFO DB", "Sucesso ao criar ao tabelas!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String cardSql = "DROP TABLE IF EXISTS " + CardDAO.TABLE_NAME + ";";
        String deckSql = "DROP TABLE IF EXISTS " + DeckDAO.TABLE_NAME + ";";
        String deckCardSql = "DROP TABLE IF EXISTS " + DeckCardDAO.TABLE_NAME + ";";

        try {
            db.execSQL(cardSql);
            db.execSQL(deckSql);
            db.execSQL(deckCardSql);

            onCreate(db);
            Log.i("INFO DB", "Sucesso ao criar ao tabela!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela " + e.getMessage());
        }

    }
}
