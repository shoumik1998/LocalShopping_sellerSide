package com.example.hand_shoping;

public class Helper_Model {

    public  String country;
    public  String code;

    public Helper_Model(String country, String code) {
        this.country = country;
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }
}
