package com.example.hand_shoping;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("register.php")
    Call<User> UserRegistration(@Query("name") String Name, @Query("user_name") String User_name,
                                @Query("user_password") String User_password);

    @GET("login.php")
    Call<User> UserLogIn(@Query("user_name") String User_name,@Query("user_password") String User_password);

    @FormUrlEncoded
    @POST("server_side.php")
    Call<ImageClass> uploadImage(@Field("title") String title, @Field("image") String image, @Field("price") int  price
            , @Field("user_name") String user_name);

    @GET("dataFetching.php")
    Call<List<Fetching_Image>> image_fetching(@Query("user_name") String u_name);


}
