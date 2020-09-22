package com.example.hand_shoping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account_Details extends AppCompatActivity {
    private EditText Password;
    private Button Submit,Update,Delete_Forever;
    private ImageView Password_Visibility;
    private TextView Country,District,SubDistrict,Region,Location,Name,Currency,Cell_number,User_Name;
    private  ApiInterface apiInterface;
    private LinearLayout info_holder;
    private  User info_getter;
    private  InputMethodManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__details);

        Password=findViewById(R.id.acc_details_editID);
        Submit=findViewById(R.id.acc_details_submitBtnID);
        Update=findViewById(R.id.acc_det_updateID);
        Delete_Forever=findViewById(R.id.acc_deletbtnID);
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
        Password_Visibility = findViewById(R.id.acc_detail_pass_visibilityID);

        Password.requestFocus();

         manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
         manager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

         Password_Visibility.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (Password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                     Password_Visibility.setImageResource(R.drawable.visibility_of);
                     Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                 } else {
                     Password_Visibility.setImageResource(R.drawable.visibility_image);
                     Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                 }
             }
         });




        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        authenticate_user();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        CreateAccount.selector_code=1;
               Intent intent= new Intent(Account_Details.this,CreateAccount.class);
               intent.putExtra("country",info_getter.getCountry())
                       .putExtra("district",info_getter.getDistrict())
                       .putExtra("subdistrict",info_getter.getSubdistrict())
                       .putExtra("region",info_getter.getRegion())
                       .putExtra("location",info_getter.getLocation())
                       .putExtra("currency",info_getter.getCurrency())
                       .putExtra("cell_number",info_getter.getCell_number())
                       .putExtra("name",info_getter.getName())
                       .putExtra("user_name",info_getter.getUser_name())
                       .putExtra("selector_code",1);
                startActivity(intent);


            }
        });
        Delete_Forever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Account_Details.this);
                builder.setMessage("Do you really want to delete your account?" +
                        " All of the information and " +
                        "data will deleted forever if you do that. ");
                builder.setCancelable(true);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
                        Call<User> call=apiInterface.account_delete_forever(MainActivity.getInstance().r_user_name());

                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.body().getResponse().equals("deleted")){
                                    MainActivity.getInstance().wlogStatus(false);
                                    MainActivity.getInstance().wName(null,null);
                                    Intent intent=new Intent(Account_Details.this,LogIn_Activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    ActivityCompat.finishAffinity(Account_Details.this);
                                    Toast.makeText(Account_Details.this, "Account has been deleted permanently", Toast.LENGTH_SHORT).show();

                                }else if(response.body().getResponse().equals("failed")) {
                                    Toast.makeText(Account_Details.this, "Something error occurred..please try again "+MainActivity.getInstance().r_user_name(), Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });






    }

//    @Override
//    public void onUserInteraction() {
//        if(getCurrentFocus()!=null){
//            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
//
//        }
//    }

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
                     info_getter= response.body();
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
                    Toast.makeText(Account_Details.this, "Incorrect Password", Toast.LENGTH_SHORT).show();






                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (t instanceof NetworkErrorException){
                    Toast.makeText(Account_Details.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Account_Details.this, "something Error Occured ..try later", Toast.LENGTH_SHORT).show();

                }


            }
        });



    }
}
