package com.example.yugiohdeck.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {

    Integer id;
    Integer number;
    String name;
    String type;
    String desc;
    int atk;
    int def;
    int level;
    String race;
    String attribute;
    String imgUrl;



    String smallImgUrl;

    public Card(Integer number, String name, String type, String desc, int atk, int def, int level, String race, String attribute, String imgUrl, String smallImgUrl) {
        this.number = number;
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.atk = atk;
        this.def = def;
        this.level = level;
        this.race = race;
        this.attribute = attribute;
        this.imgUrl = imgUrl;
        this.smallImgUrl = smallImgUrl;
    }

    public Card() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }

    public static List<Card> fromJSONList(JSONArray dataList) throws JSONException {
        try {
            ArrayList<Card> cards = new ArrayList<>();

            for (int i = 0; i < dataList.length(); i++)
            {
                cards.add(Card.fromJSON(dataList.getJSONObject(i)));
            }

            return cards;

        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static Card fromJSON(JSONObject data) throws JSONException {
        try{
            Card card = new Card();

            card.setNumber(data.getInt("id"));
            card.setName(data.getString("name"));
            card.setRace(data.getString("race"));
            card.setType(data.getString("type"));
            card.setDesc(data.getString("desc"));

            JSONObject cardImage = data.getJSONArray("card_images").getJSONObject(0);
            card.setImgUrl(cardImage.getString("image_url"));
            card.setSmallImgUrl(cardImage.getString("image_url_small"));


            if (data.has("attribute"))
                card.setAttribute(data.getString("attribute"));
            if (data.has("atk"))
                card.setAtk(data.getInt("atk"));
            if (data.has("def"))
                card.setDef(data.getInt("def"));
            if (data.has("level"))
                card.setLevel(data.getInt("level"));




            return card;
        }
        catch (JSONException e) {
            e.printStackTrace();
            throw e;
        }

    }


}
