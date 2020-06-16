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
}
