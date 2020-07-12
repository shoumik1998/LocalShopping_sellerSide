package com.example.hand_shoping;

import com.google.gson.annotations.SerializedName;

public class Fetching_Image {

    @SerializedName("name")
    String Name;
    @SerializedName("image_path")
    String Image_Path;
    @SerializedName("price")
    String Price;
    @SerializedName("id")
    int id;
    @SerializedName("response")
    String response;

    @SerializedName("currency")
    String Currency;

    boolean checker;

    public boolean isChecked() {
        return checker;
    }

    public void setChecked(boolean checker) {
        this.checker = checker;
    }

    public String getName() {
        return Name;
    }

    public String getImage_Path() {
        return Image_Path;
    }


    public String getPrice() {
        return Price;
    }

    public int getId() {
        return id;
    }

    public String getCurrency() {
        return Currency;
    }

    public String getResponse() {
        return response;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
