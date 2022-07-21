package com.example.inventorymanagement.Models;

import java.util.List;

public class ParentStocks {
    List<Stocks> childModelStocks;
    private String id, date, time;


    public ParentStocks(String id, String date, String time, List<Stocks> childModelStocks) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.childModelStocks = childModelStocks;
    }

    public ParentStocks() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Stocks> getChildModelStocks() {
        return childModelStocks;
    }

    public void setChildModelStocks(List<Stocks> childModelStocks) {
        this.childModelStocks = childModelStocks;
    }
}
