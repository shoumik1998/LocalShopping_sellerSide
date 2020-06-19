package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccount extends AppCompatActivity {
    public EditText Country,District,Sub_district,Shop_region,Shop_Location, Name,Currency,Cell_number,UserName,UserPassword;
    private Button CreateAccButton,Update_Button;
    private TextView Helper;
    public  static ApiInterface apiInterface;
    public static int selector_code;
    public static CreateAccount createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Country=findViewById(R.id.CAcceditCountrynameID);
        District=findViewById(R.id.CAcceditDistrictnameID);
        Sub_district=findViewById(R.id.CAcceditSubDistrictnameID);
        Shop_region=findViewById(R.id.CAcceditShopRegionnameID);
        Shop_Location=findViewById(R.id.CAcceditShopLocationnameID);
        Name=findViewById(R.id.CAcceditnameID);
        Currency=findViewById(R.id.CAcceditcurrencyID);
        Cell_number=findViewById(R.id.CAcceditcellNumberID);
        UserName=findViewById(R.id.CaccounteditusernameID);
        UserPassword=findViewById(R.id.CccounteditpassID);
        CreateAccButton=findViewById(R.id.CaccountBtnID);
        Update_Button=findViewById(R.id.cAcc_updateID);
        Helper=findViewById(R.id.helpID);
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        createAccount=this;

        if (selector_code==1){
            CreateAccButton.setVisibility(View.GONE);
            Update_Button.setVisibility(View.VISIBLE);
            Country.setText(getIntent().getStringExtra("country"));
            District.setText(getIntent().getStringExtra("district"));
            Sub_district.setText(getIntent().getStringExtra("subdistrict"));
            Shop_region.setText(getIntent().getStringExtra("region"));
            Shop_Location.setText(getIntent().getStringExtra("location"));
            Name.setText(getIntent().getStringExtra("name"));
            Currency.setText(getIntent().getStringExtra("currency"));
            Cell_number.setText(getIntent().getStringExtra("cell_number"));
            UserName.setText(getIntent().getStringExtra("user_name"));
            UserName.setKeyListener(null);
            UserName.setFocusable(false);
            UserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CreateAccount.this, "You can not change the  user name", Toast.LENGTH_SHORT).show();
                }
            });


        }







        CreateAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration_user();


            }
        });

        Update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registration_user();
            }
        });

        Helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this,Helper_Activity.class));
            }
        });


    }

    public  static CreateAccount getInstance(){
        return createAccount;
    }

    private void registration_user() {

        if (TextUtils.isEmpty(Name.getText().toString()) || TextUtils.isEmpty(Country.getText().toString()) || TextUtils.isEmpty(District.getText().toString())
                || TextUtils.isEmpty(Sub_district.getText().toString())
                ||TextUtils.isEmpty(UserName.getText().toString())
                || TextUtils.isEmpty(Shop_region.getText().toString())
                || TextUtils.isEmpty(Shop_Location.getText().toString())
                || TextUtils.isEmpty(Currency.getText().toString())
                || TextUtils.isEmpty(UserPassword.getText().toString())){

            Toast.makeText(CreateAccount.this, "All of the information is strongly required, fill up all of the block", Toast.LENGTH_SHORT).show();
        }else if (!phone_number_validation(Cell_number.getText().toString().trim())){
            Toast.makeText(CreateAccount.this, "Phone number is not valid", Toast.LENGTH_SHORT).show();
        }
        else {

            String country=Country.getText().toString();
            String district=District.getText().toString();
            String sub_district=Sub_district.getText().toString();
            String shop_region=Shop_region.getText().toString();
            String shop_location=Shop_Location.getText().toString();
            String name=Name.getText().toString();
            String currency=Currency.getText().toString();
            final String cell_number=Cell_number.getText().toString();
            String user_name=UserName.getText().toString();
            String user_password=UserPassword.getText().toString();


            Call<User> call=apiInterface.userRegistration(name,user_name,user_password,country,district,sub_district,shop_region,shop_location,currency,cell_number,selector_code);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getResponse().equals("OK")){
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

                    }else if(response.body().getResponse().contains("Invalid_location")){
                        Toast.makeText(CreateAccount.this, "Invalid Location Name ", Toast.LENGTH_SHORT).show();
                    }

                    if (selector_code==1){
                        Toast.makeText(CreateAccount.this, "Information is updated successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(CreateAccount.this,"Registration is success ",Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(CreateAccount.this, "Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });



        }
        }



    public final static boolean phone_number_validation(CharSequence cell_number) {
        if (cell_number == null || cell_number.length() < 6 || cell_number.length() > 15) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(cell_number).matches();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        selector_code=0;
    }
}
