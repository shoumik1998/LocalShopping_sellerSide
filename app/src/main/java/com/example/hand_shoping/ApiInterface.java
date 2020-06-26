package com.example.hand_shoping;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("register.php")
    Call<User> userRegistration(@Query("name") String Name, @Query("user_name") String User_name,
                                @Query("user_password") String User_password,@Query("country") String Country,@Query("district") String District,@Query("subdistrict") String SubDistrict,
                                @Query("region") String Region,@Query("location") String Location, @Query("currency") String Currency,@Query("cell_number") String Cell_number,@Query("selector_code") int code);

    @GET("login.php")
    Call<User> userLogIn(@Query("user_name") String User_name,@Query("user_password") String User_password);

    @FormUrlEncoded
    @POST("server_side.php")
    Call<ImageClass> uploadImage(@Field("title") String title, @Field("image") String image, @Field("price") int  price
            , @Field("user_name") String user_name);

    @GET("dataFetching.php")
    Call<List<Fetching_Image>> image_fetching(@Query("user_name") String u_name);

    @FormUrlEncoded
    @POST("dataDelete.php")
    Call<List<Fetching_Image>> delete_products(@Field("id[]") ArrayList<Integer> id);


    @GET("details_fetching.php")
    Call<User> user_authentication(@Query("user_name") String User_name,@Query("user_password") String user_password);

    @FormUrlEncoded
    @POST("account_delete.php")
   Call<User> account_delete_forever(@Field("user_name")String User_Name);

    @FormUrlEncoded
    @POST("update_products.php")
    Call<Fetching_Image> update_product(@Field("id") int Id,@Field("user_name") String User_name,@Field("product_name") String Product_name,@Field("product_price") int Product_price);


}
