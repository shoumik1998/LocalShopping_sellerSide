package com.example.hand_shoping;

import com.google.gson.annotations.SerializedName;

public class ImageClass  {
    @SerializedName("title")
    private  String Title;
    @SerializedName("image")
    private  String Image;
    @SerializedName("response")
    private  String Response;

    public String getTitle(){
        return Title;
    }


    public String getResponse() {
        return Response;
    }

}
