package com.example.hand_shoping;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("response")
    private  String Response;
    @SerializedName("user_name")
    private  String user_name;
    @SerializedName("name")
    private  String Name;
    @SerializedName("cell_number")
    private  String Cell_number;
    @SerializedName("country")
    private  String Country;
    @SerializedName("district")
    private  String District;
    @SerializedName("subdistrict")
    private  String Subdistrict;
    @SerializedName("region")
    private String Region;
    @SerializedName("Location")
    private  String Location;
    @SerializedName("currency")
    private  String Currency;

    public User(String response, String user_name, String name, String cell_number, String country, String district, String subdistrict, String region, String location, String currency) {
        Response = response;
        this.user_name = user_name;
        Name = name;
        Cell_number = cell_number;
        Country = country;
        District = district;
        Subdistrict = subdistrict;
        Region = region;
        Location = location;
        Currency = currency;
    }

    public String getResponse() {
        return Response;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getName() {
        return Name;
    }

    public String getCell_number() {
        return Cell_number;
    }

    public String getCountry() {
        return Country;
    }

    public String getDistrict() {
        return District;
    }

    public String getSubdistrict() {
        return Subdistrict;
    }

    public String getRegion() {
        return Region;
    }

    public String getLocation() {
        return Location;
    }

    public String getCurrency() {
        return Currency;
    }
}
