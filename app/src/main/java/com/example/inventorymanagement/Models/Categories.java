package com.example.inventorymanagement.Models;

public class Categories {
    private String id, category, type;


    public Categories(String id, String category, String type) {
        this.id = id;
        this.category = category;
        this.type = type;
    }

    public Categories() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
