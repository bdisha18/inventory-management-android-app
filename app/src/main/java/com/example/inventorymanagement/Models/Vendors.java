package com.example.inventorymanagement.Models;

public class Vendors {

    private String id, name, contactno, email, address, note;
    
    public Vendors() {

    }


    public Vendors(String vendor_id, String vendor_name, String vendor_contactno, String vendor_email, String vendor_address, String note){
        this.id= vendor_id;
        this.name= vendor_name;
        this.contactno = vendor_contactno;
        this.email = vendor_email;
        this.address= vendor_address;
        this.note = note;
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

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
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
