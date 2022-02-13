package com.example.yugiohdeck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.tasks.DBTask;
import com.example.yugiohdeck.utils.DAOCallable;
import com.example.yugiohdeck.utils.DAOCallback;
import com.example.yugiohdeck.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;


public class DeckDAO {

    public static String TABLE_NAME = "decks";
    public static String COLUMNS = "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "name TEXT NOT NULL,"
            + "description TEXT NOT NULL);";


    public static String CREATE_DECK_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " + COLUMNS;



    DBHelper dbHelper;




    public DeckDAO(Context context) {

        dbHelper = DBHelper.getInstance(context);

    }

    public void salvar(Deck deck, DAOCallback callback) {

        new DBTask(
                new DAOCallable() {
                    @Override
                    public Object run() {

                        SQLiteDatabase write = dbHelper.getWritableDatabase();

                        ContentValues cv = new ContentValues();

                        cv.put("name", deck.getName());

                        cv.put("description", deck.getDescription());

                        try {
                            long response = write.insertOrThrow(TABLE_NAME, null, cv);
                            Log.i("INFO", "Registro salvo com sucesso!");
                        } catch (Exception e) {
                            Log.i("INFO", "Erro ao salvar registro: " + e.getMessage());
                            return false;
                        }

                        write.close();
                        return true;
                    }},
                callback).execute();



    }

    public void listar(DAOCallback callback) {

        new DBTask(new DAOCallable() {
            @Override
            public Object run() {

                SQLiteDatabase read = dbHelper.getReadableDatabase();

                List<Deck> decks = new ArrayList<>();

                // 1. string sql de consulta
                String sql = "SELECT * FROM " + TABLE_NAME + ";";

                // 2. Cursor para acesso aos dados
                Cursor c = read.rawQuery(sql, null);

                // 3. percorrer o cursor
                boolean result = c.moveToFirst();

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

                read.close();

                return decks;
            }
        }, callback).execute();
    }

    public boolean atualizar(Deck deck) {

        SQLiteDatabase write = dbHelper.getWritableDatabase();

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

            write.update(TABLE_NAME, cv, "id=?", args);

            write.close();
            Log.i("INFO", "Registro atualizado com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao atualizar registro!" + e.getMessage());
            return false;
        }
        return true;
    }

    public void deletar(Deck deck, DAOCallback callback) {

        new DBTask(new DAOCallable() {
            @Override
            public Object run() {
                try {
                    SQLiteDatabase write = dbHelper.getWritableDatabase();

                    // id do registro que será deletado
                    String[] args = { deck.getId().toString() };
                    write.delete(TABLE_NAME, "id=?", args);

                    write.close();
                    Log.i("INFO", "Registro apagado com sucesso!");
                } catch (Exception e) {
                    Log.i("INFO", "Erro apagar registro!" + e.getMessage());
                    return false;
                }
                return true;
            }
        }, callback).execute();

    }
}
