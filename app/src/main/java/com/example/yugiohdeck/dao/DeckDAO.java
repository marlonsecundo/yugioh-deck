package com.example.yugiohdeck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DeckDAO {

    private final SQLiteDatabase escreve;
    private final SQLiteDatabase le;

    DBHelper dbHelper;


    public DeckDAO(Context context) {

        String columns = "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "name TEXT NOT NULL,"
                + "description TEXT NOT NULL);";


        dbHelper = new DBHelper(context, "decks", columns);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();

    }

    public boolean salvar(Deck deck) {

        // 1. definir o conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("name", deck.getName());
        cv.put("description", deck.getDescription());

        try {
            escreve.insert(dbHelper.tabelName, null, cv);
            Log.i("INFO", "Registro salvo com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao salvar registro: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Deck> listar() {

        List<Deck> decks = new ArrayList<>();

        // 1. string sql de consulta
        String sql = "SELECT * FROM " + dbHelper.tabelName + ";";

        // 2. Cursor para acesso aos dados
        Cursor c = le.rawQuery(sql, null);

        // 3. percorrer o cursor
        c.moveToFirst();
        while (c.moveToNext()) {

            Deck deck = new Deck();

            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            String description = c.getString(c.getColumnIndexOrThrow("description"));
            String name = c.getString(c.getColumnIndexOrThrow("name"));

            deck.setId(id);
            deck.setDescription(description);
            deck.setName(name);

            decks.add(deck);
        }
        c.close();

        return decks;
    }

    public boolean atualizar(Deck deck) {


        // 1. definir conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("description", deck.getDescription());
        cv.put("name", deck.getName());

        // 2. atualizar valor no banco
        try {
            String[] args = { deck.getId().toString() };

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

    public boolean deletar(Deck deck) {

        // 1. deletar um registro de nota na tabela notas

        try {
            // id do registro que será deletado
            String[] args = { deck.getId().toString() };
            escreve.delete(dbHelper.tabelName, "id=?", args);
            Log.i("INFO", "Registro apagado com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro apagar registro!" + e.getMessage());
            return false;
        }
        return true;

    }
}
