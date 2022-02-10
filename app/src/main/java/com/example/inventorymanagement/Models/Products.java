package com.example.inventorymanagement.Models;

public class Products {
    private String id;
    private String design;
    private String image;
    private String barcode;
    private String quantity;
    private String salePrice;
    private String purchasePrice;

    public Products(){

    }

    public Products(String id, String design, String image, String barcode, String quantity, String salePrice, String purchasePrice) {
        this.id = id;
        this.design = design;
        this.image = image;
        this.barcode = barcode;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
