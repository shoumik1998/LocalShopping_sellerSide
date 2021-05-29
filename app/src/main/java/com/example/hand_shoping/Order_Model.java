package com.example.hand_shoping;

import com.google.gson.annotations.SerializedName;

public class Order_Model {
    private String product_name, product_price, number_of_product,client_name,contact_no, address, delivering_date, imagepath;
    @SerializedName("date/time")
    String issue_date;


    public String getProduct_name() {
        return product_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getNumber_of_product() {
        return number_of_product;
    }

    public void setNumber_of_product(String number_of_product) {
        this.number_of_product = number_of_product;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getDelivering_date() {
        return delivering_date;
    }

    public void setDelivering_date(String delivering_date) {
        this.delivering_date = delivering_date;
    }
}
