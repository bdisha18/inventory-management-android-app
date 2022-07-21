package com.example.inventorymanagement.Models;

public class ProductLocations {
    String id, location_id, product_id, quantity;

    public ProductLocations(String id, String location_id, String product_id, String quantity){
        this.id = id;
        this.product_id = product_id;
        this.location_id= location_id;
        this.quantity= quantity;
    }

    public ProductLocations(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
