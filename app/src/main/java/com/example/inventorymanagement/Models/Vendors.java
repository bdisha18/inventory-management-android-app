package com.example.inventorymanagement.Models;

public class Vendors {

    private String id, name, contact, email, address, note, city, zipcode, state;
    


    public Vendors(String vendor_id, String vendor_name, String vendor_contact, String vendor_email, String vendor_address, String note, String city, String state, String zipcode){
        this.id= vendor_id;
        this.name= vendor_name;
        this.contact = vendor_contact;
        this.email = vendor_email;
        this.address= vendor_address;
        this.note = note;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public Vendors() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
