package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccount extends AppCompatActivity {
    private EditText Country,District,Sub_district,Shop_region, Name,Currency,UserName,UserPassword;
    private Button CreateAccButton;
    public  static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Country=findViewById(R.id.CAcceditCountrynameID);
        District=findViewById(R.id.CAcceditDistrictnameID);
        Sub_district=findViewById(R.id.CAcceditSubDistrictnameID);
        Shop_region=findViewById(R.id.CAcceditShopRegionnameID);
        Name=findViewById(R.id.CAcceditnameID);
        Currency=findViewById(R.id.CAcceditcurrencyID);
        UserName=findViewById(R.id.CaccounteditusernameID);
        UserPassword=findViewById(R.id.CccounteditpassID);
        CreateAccButton=findViewById(R.id.CaccountBtnID);
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);

        CreateAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Name.getText().toString()) || TextUtils.isEmpty(Country.getText().toString()) || TextUtils.isEmpty(District.getText().toString())
                        || TextUtils.isEmpty(Sub_district.getText().toString())
                        ||TextUtils.isEmpty(UserName.getText().toString())
                        || TextUtils.isEmpty(Shop_region.getText().toString())
                        || TextUtils.isEmpty(Currency.getText().toString())
                        || TextUtils.isEmpty(UserPassword.getText().toString())){
                    Toast.makeText(CreateAccount.this, "All of the information is strongly required, fill up properly", Toast.LENGTH_SHORT).show();
                }else {
                    registration_user();

                }


            }
        });
    }

    private void registration_user() {

        String country=Country.getText().toString();
        String district=District.getText().toString();
        String sub_district=Sub_district.getText().toString();
        String shop_region=Shop_region.getText().toString();
        String name=Name.getText().toString();
        String currency=Currency.getText().toString();
        String user_name=UserName.getText().toString();
        String user_password=UserPassword.getText().toString();


        Call<User> call=apiInterface.userRegistration(name,user_name,user_password,country,district,sub_district,shop_region,currency);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("OK")){
                    Toast.makeText(CreateAccount.this, "registration Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateAccount.this,LogIn_Activity.class));
                    finish();
                    Name.setText("");
                    UserName.setText("");
                    UserPassword.setText("");
                }else if(response.body().getResponse().equals("exist")){
                    Toast.makeText(CreateAccount.this, "User already exist", Toast.LENGTH_SHORT).show();
                }else if(response.body().getResponse().equals("Error")){
                    Toast.makeText(CreateAccount.this, "registration Failed", Toast.LENGTH_SHORT).show();

                }else if (response.body().getResponse().equals("Invalid")){
                    Toast.makeText(CreateAccount.this, "Invalid user name ", Toast.LENGTH_SHORT).show();

                }else if(response.body().getResponse().contains("Invalid_low")){
                    Toast.makeText(CreateAccount.this, "Minimum length of user name is  3 ", Toast.LENGTH_SHORT).show();


                }else if (response.body().getResponse().contains("Invalid_high")){
                    Toast.makeText(CreateAccount.this, "Maximum length of user name is 20", Toast.LENGTH_SHORT).show();

                }else if (response.body().getResponse().contains("Invalid_password")){
                    Toast.makeText(CreateAccount.this, "Maximum length of password is 10", Toast.LENGTH_SHORT).show();

                }else  if(response.body().getResponse().contains("Invalid_currency")){
                    Toast.makeText(CreateAccount.this, "Invalid Currency ", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CreateAccount.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
