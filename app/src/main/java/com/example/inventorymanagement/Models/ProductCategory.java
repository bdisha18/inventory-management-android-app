package com.example.inventorymanagement.Models;

public class ProductCategory {
    private String id, product_id, category_id, category_value;

    public ProductCategory(){

    }

    public ProductCategory(String id, String product_id, String category_id, String category_value) {
        this.id = id;
        this.product_id = product_id;
        this.category_id = category_id;
        this.category_value = category_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_value() {
        return category_value;
    }

    public void setCategory_value(String category_value) {
        this.category_value = category_value;
    }
}
