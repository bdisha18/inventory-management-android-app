package com.example.inventorymanagement.Models;

public class Users {

    private String id, name, email, contactNo, companyName, address, pincode, password;

    public Users(){

    }

    public Users(String id, String name, String email, String contactNo, String companyName, String address,
    String pincode, String password ){
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.companyName = companyName;
        this.address = address;
        this.pincode = pincode;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
