package com.example.yugiohdeck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yugiohdeck.models.Card;
import com.example.yugiohdeck.models.Deck;
import com.example.yugiohdeck.tasks.DBTask;
import com.example.yugiohdeck.utils.DAOCallable;
import com.example.yugiohdeck.utils.DAOCallback;
import com.example.yugiohdeck.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    public static String TABLE_NAME = "cards";
    public static String COLUMNS = "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "number INTEGER NOT NULL,"
            + "name TEXT NOT NULL,"
            + "type TEXT NOT NULL,"
            + "desc TEXT NOT NULL,"
            + "atk INTEGER NOT NULL,"
            + "def INTEGER NOT NULL,"
            + "level INTEGER NOT NULL,"
            + "race TEXT NOT NULL,"
            + "attribute TEXT NOT NULL,"
            + "small_image_url TEXT NOT NULL,"
            + "image_url TEXT NOT NULL);";

    public static String CREATE_CARD_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " + COLUMNS;



    DBHelper dbHelper;



    public CardDAO(Context context) {



        dbHelper = DBHelper.getInstance(context);

    }

    public void salvar(Card card, DAOCallback callback) {

        new DBTask(new DAOCallable() {
            @Override
            public Object run() {
                long response;
                // 1. definir o conteudo a ser salvo
                ContentValues cv = new ContentValues();
                cv.put("number", card.getNumber());
                cv.put("name", card.getName());
                cv.put("type", card.getType());
                cv.put("desc", card.getDesc());
                cv.put("atk", card.getAtk());
                cv.put("def", card.getDef());
                cv.put("level", card.getLevel());
                cv.put("race", card.getRace());
                cv.put("attribute", card.getAttribute());
                cv.put("image_url", card.getImgUrl());
                cv.put("small_image_url", card.getSmallImgUrl());
                try {
                    SQLiteDatabase write = dbHelper.getWritableDatabase();

                    response = write.insertOrThrow(TABLE_NAME, null, cv);

                    write.close();

                    Log.i("INFO", "Registro salvo com sucesso!");
                } catch (Exception e) {
                    Log.i("INFO", "Erro ao salvar registro: " + e.getMessage());
                    return -1;
                }

                return Long.valueOf(response).intValue();
            }
        }, callback).execute();

    }

    public void listar(List<Integer> cardIds, DAOCallback callback) {

        new DBTask(new DAOCallable() {
            @Override
            public Object run() {
                SQLiteDatabase read = dbHelper.getReadableDatabase();

                List<Card> cards = new ArrayList<>();

                // 1. string sql de consulta
                String sql = "SELECT * FROM " + TABLE_NAME;



                if (cardIds.size() > 0)
                {
                    sql += " WHERE";
                    for (int i = 0; i < cardIds.size(); i++) {
                        sql += " id = " + cardIds.get(i);

                        if (i + 1 == cardIds.size())
                            break;

                        sql += " OR";
                    }
                }
                else {
                    return cards;
                }

                sql += ";";

                // 2. Cursor para acesso aos dados
                Cursor c = read.rawQuery(sql, null);

                // 3. percorrer o cursor
                boolean response = c.moveToFirst();
                while (c.moveToNext()) {

                    Card card = new Card();

                    int id = c.getInt(c.getColumnIndexOrThrow("id"));
                    int number = c.getInt(c.getColumnIndexOrThrow("number"));
                    String name = c.getString(c.getColumnIndexOrThrow("name"));
                    String type = c.getString(c.getColumnIndexOrThrow("type"));
                    String desc = c.getString(c.getColumnIndexOrThrow("desc"));
                    int atk = c.getInt(c.getColumnIndexOrThrow("atk"));
                    int def = c.getInt(c.getColumnIndexOrThrow("def"));
                    int level = c.getInt(c.getColumnIndexOrThrow("level"));
                    String race = c.getString(c.getColumnIndexOrThrow("race"));
                    String attribute = c.getString(c.getColumnIndexOrThrow("attribute"));
                    String smallImageUrl = c.getString(c.getColumnIndexOrThrow("small_image_url"));
                    String imageUrl = c.getString(c.getColumnIndexOrThrow("image_url"));

                    card.setId(id);
                    card.setNumber(number);
                    card.setName(name);
                    card.setType(type);
                    card.setDesc(desc);
                    card.setAtk(atk);
                    card.setDef(def);
                    card.setLevel(level);
                    card.setRace(race);
                    card.setAttribute(attribute);
                    card.setImgUrl(imageUrl);
                    card.setSmallImgUrl(smallImageUrl);




                    cards.add(card);
                }
                c.close();
                read.close();

                return cards;
            }
        }, callback).execute();
    }

    public boolean atualizar(Card card) {


        // 1. definir conteudo a ser salvo
        ContentValues cv = new ContentValues();
        cv.put("number", card.getNumber());
        cv.put("name", card.getName());
        cv.put("type", card.getType());
        cv.put("desc", card.getDesc());
        cv.put("atk", card.getAtk());
        cv.put("def", card.getDef());
        cv.put("level", card.getLevel());
        cv.put("race", card.getRace());
        cv.put("attribute", card.getAttribute());
        cv.put("image_url", card.getImgUrl());
        cv.put("small_image_url", card.getSmallImgUrl());


        // 2. atualizar valor no banco
        try {
            SQLiteDatabase write = dbHelper.getWritableDatabase();

            String[] args = { card.getId().toString() };

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

    public boolean deletar(Card card) {

        // 1. deletar um registro de nota na tabela notas

        try {
            SQLiteDatabase write = dbHelper.getWritableDatabase();

            // id do registro que será deletado
            String[] args = { card.getId().toString() };
            write.delete(TABLE_NAME, "id=?", args);
            Log.i("INFO", "Registro apagado com sucesso!");
        } catch (Exception e) {
            Log.i("INFO", "Erro apagar registro!" + e.getMessage());
            return false;
        }
        return true;

    }
}
