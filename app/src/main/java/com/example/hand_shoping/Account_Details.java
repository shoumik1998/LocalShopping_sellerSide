package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account_Details extends AppCompatActivity {
    private EditText Password;
    private Button Submit,Update;
    private TextView Country,District,SubDistrict,Region,Location,Name,Currency,Cell_number,User_Name;
    private  ApiInterface apiInterface;
    private LinearLayout info_holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__details);

        Password=findViewById(R.id.acc_details_editID);
        Submit=findViewById(R.id.acc_details_submitBtnID);
        Update=findViewById(R.id.acc_det_updateID);
        Country=findViewById(R.id.acc_det_countryID);
        District=findViewById(R.id.acc_det_districtID);
        SubDistrict=findViewById(R.id.acc_det_subDistrictID);
        Region=findViewById(R.id.acc_det_regionID);
        Location=findViewById(R.id.acc_det_locationID);
        Name=findViewById(R.id.acc_det_shop_nameID);
        Currency=findViewById(R.id.acc_det_currencyID);
        Cell_number=findViewById(R.id.acc_det_cell_numberID);
        User_Name=findViewById(R.id.acc_det_userNameID);
        info_holder=findViewById(R.id.acc_detAllID);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        authenticate_user();
            }
        });





    }

    private void authenticate_user() {
        String password=Password.getText().toString();

        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);

        Call<User> call=apiInterface.user_authentication(MainActivity.getInstance().r_user_name(),password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("OK")){
                    info_holder.setVisibility(View.VISIBLE);
                    Submit.setVisibility(View.GONE);
                    Password.setVisibility(View.GONE);
                    User info_getter= response.body();
                    Name.setText("Shop Name : "+info_getter.getName());
                    User_Name.setText("User Name : "+info_getter.getUser_name());
                    Country.setText("Country : "+info_getter.getCountry());
                    District.setText("District : "+info_getter.getDistrict());
                    SubDistrict.setText("SubDistrict : "+info_getter.getSubdistrict());
                    Region.setText("Region : "+info_getter.getRegion());
                    Location.setText("Location : "+info_getter.getLocation());
                    Currency.setText("Currency : "+info_getter.getCurrency());
                    Cell_number.setText("Cell Number : "+info_getter.getCell_number());

                }else {
                    Toast.makeText(Account_Details.this, "Na thik Nai", Toast.LENGTH_SHORT).show();






                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Account_Details.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}