package com.example.yugiohdeck.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_BD = "bd_yugioh";

    public String tabelName;
    public String columns;

    public DBHelper(@Nullable Context context, String tabelName, String columns) {
        super(context, NOME_BD, null, VERSION);

        this.tabelName = tabelName;
        this.columns = columns;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + tabelName + columns;
//
//                + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
//                + "descricao TEXT NOT NULL,"
//                + "titulo TEXT NOT NULL,"
//                + "data TEXT NOT NULL);";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar ao tabela!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + tabelName + ";";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao criar ao tabela!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela " + e.getMessage());
        }

    }
}
