package com.example.inventorymanagement.Models;

public class Stocks {
    private String id, location_id, stock_type, product_id, quantity,
            client_type, vendor_id, customer_id, date, time, note;




    public Stocks(String id, String location_id, String stock_type, String product_id,String quantity,
                  String client_type, String vendor_id, String customer_id, String date, String time, String note) {

        this.id= id;
        this.location_id= location_id;
        this.stock_type = stock_type;
        this.product_id =  product_id;
        this.quantity = quantity;
        this.client_type = client_type;
        this.vendor_id = vendor_id;
        this.customer_id = customer_id;
        this.note = note;
        this.date = date;
        this.time = time;

    }

    public Stocks() {

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getStock_type() {
        return stock_type;
    }

    public void setStock_type(String stock_type) {
        this.stock_type = stock_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
