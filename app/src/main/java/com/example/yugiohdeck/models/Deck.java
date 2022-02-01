package com.example.yugiohdeck.models;

import java.io.Serializable;

public class Deck implements Serializable {

    Integer id;
    String description;
    String name;

    public Deck() {
    }

    public Deck(int id, String description, String name) {
        this.id = id;
        this.description = description;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
