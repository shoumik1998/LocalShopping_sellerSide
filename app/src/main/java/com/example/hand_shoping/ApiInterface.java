package com.example.hand_shoping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @POST("register")
    Call<User> userRegistration(@Body HashMap<String ,Object> map);
    
    @POST("login")
    Call<User> userLogIn(@Body HashMap<String,String> map);


    @POST("upload")
    Call<ImageClass> uploadImage(@Body HashMap<String,Object> upload_map);


    @POST("data_fetching")
    Call<List<Fetching_Image>> image_fetching(@Body HashMap<String,String> user_name);


    @POST("delete_products")
    Call<Fetching_Image> delete_products(@Body HashMap<String,Object> id_map);


    
    @POST("details_fetching")
    Call<User> user_authentication(@Body HashMap<String,String> map);


    @POST("account_delete")
   Call<User> account_delete_forever(@Body String User_Name);


    @POST("update_products")
    Call<Fetching_Image> update_product(@Body HashMap<String,Object> map);

    @POST("order")
    Call<List<Order_Model>> orders(@Body HashMap<String,String> map);

    @POST("order_receive")
    Call<List<Order_Model>> onOrder_Receive(@Body HashMap<String,Object> map);


}
