package com.example.yugiohdeck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    private final SQLiteDatabase escreve;
    private final SQLiteDatabase le;

    DBHelper dbHelper;


    public CardDAO(Context context) {

        String columns = "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "name TEXT NOT NULL,"
                + "type TEXT NOT NULL,"
                + "desc TEXT NOT NULL,"
                + "atk INTEGER NOT NULL,"
                + "def INTEGER NOT NULL,"
                + "level INTEGER NOT NULL,"
                + "race TEXT NOT NULL,"
                + "attribute TEXT NOT NULL,"
                + "image_url TEXT NOT NULL);";


        dbHelper = new DBHelper(context, "cards", columns);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();

    }

    public boolean salvar(Card card) {

        // 1. definir o conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("name", card.getName());
        cv.put("type", card.getType());
        cv.put("desc", card.getDesc());
        cv.put("atk", card.getAtk());
        cv.put("def", card.getDef());
        cv.put("level", card.getLevel());
        cv.put("race", card.getRace());
        cv.put("attribute", card.getAttribute());
        cv.put("image_url", card.getImgUrl());



        try {
            escreve.insert(dbHelper.tabelName, null, cv);
            Log.i("INFO", "Registro salvo com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao salvar registro: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Card> listar() {

        List<Card> cards = new ArrayList<>();

        // 1. string sql de consulta
        String sql = "SELECT * FROM " + dbHelper.tabelName + ";";

        // 2. Cursor para acesso aos dados
        Cursor c = le.rawQuery(sql, null);

        // 3. percorrer o cursor
        c.moveToFirst();
        while (c.moveToNext()) {

            Card card = new Card();

            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            String type = c.getString(c.getColumnIndexOrThrow("type"));
            String desc = c.getString(c.getColumnIndexOrThrow("desc"));
            int atk = c.getInt(c.getColumnIndexOrThrow("atk"));
            int def = c.getInt(c.getColumnIndexOrThrow("def"));
            int level = c.getInt(c.getColumnIndexOrThrow("level"));
            String race = c.getString(c.getColumnIndexOrThrow("race"));
            String attribute = c.getString(c.getColumnIndexOrThrow("attribute"));
            String imageUrl = c.getString(c.getColumnIndexOrThrow("image_url"));

            card.setId(id);
            card.setName(name);
            card.setType(type);
            card.setDesc(desc);
            card.setAtk(atk);
            card.setDef(def);
            card.setLevel(level);
            card.setRace(race);
            card.setAttribute(attribute);
            card.setImgUrl(imageUrl);




            cards.add(card);
        }
        c.close();

        return cards;
    }

    public boolean atualizar(Card card) {


        // 1. definir conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("name", card.getName());
        cv.put("type", card.getType());
        cv.put("desc", card.getDesc());
        cv.put("atk", card.getAtk());
        cv.put("def", card.getDef());
        cv.put("level", card.getLevel());
        cv.put("race", card.getRace());
        cv.put("attribute", card.getAttribute());
        cv.put("image_url", card.getImgUrl());


        // 2. atualizar valor no banco
        try {
            String[] args = { card.getId().toString() };

            // 2.1 update(nome da tabela, conteudo para atualizar, clausula de atualização
            // (where)
            // o argumento da condição --> ?)

            escreve.update(dbHelper.tabelName, cv, "id=?", args);

            Log.i("INFO", "Registro atualizado com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao atualizar registro!" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deletar(Card card) {

        // 1. deletar um registro de nota na tabela notas

        try {
            // id do registro que será deletado
            String[] args = { card.getId().toString() };
            escreve.delete(dbHelper.tabelName, "id=?", args);
            Log.i("INFO", "Registro apagado com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro apagar registro!" + e.getMessage());
            return false;
        }
        return true;

    }
}
