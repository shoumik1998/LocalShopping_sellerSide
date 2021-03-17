package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn_Activity extends AppCompatActivity {
    private Button loginbtn;
    private TextView createAccTextV,Forgate_password;
    private EditText User_name,User_password;
    private ImageView Password_Visibility;
    protected   static SharedPreferences sharedPreferences;
    public Context context;
    public   ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);

        loginbtn=findViewById(R.id.loginBtnID);
        createAccTextV=findViewById(R.id.logintextID);
        User_name=findViewById(R.id.LogIneditusernameID);
        User_password=findViewById(R.id.logIneditpassID);
        Forgate_password=findViewById(R.id.forgatePassID);
        Password_Visibility = findViewById(R.id.log_pass_visibilityID);

        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);

        Password_Visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    Password_Visibility.setImageResource(R.drawable.visibility_of);
                    User_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    Password_Visibility.setImageResource(R.drawable.visibility_image);
                    User_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });




        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user();
            }
        });

        createAccTextV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn_Activity.this,CreateAccount.class));
            }
        });

        Forgate_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(User_name.getText().toString())){
                    Toast.makeText(LogIn_Activity.this, "User name is strongly required", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(LogIn_Activity.this,Reset_Password.class));

                }
            }
        });



    }

    private void login_user() {

        String user_name=User_name.getText().toString();
        String user_password=User_password.getText().toString();

        HashMap<String,String> map=new HashMap<>();
        map.put("user_name",user_name);
        map.put("user_password",user_password);

        Call<User> call =apiInterface.userLogIn(map);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("OK")) {
                        Toast.makeText(LogIn_Activity.this, "LogIn Success", Toast.LENGTH_SHORT).show();
                        MainActivity.getInstance().wlogStatus(true);
                        MainActivity.getInstance().wName(response.body().getName(), User_name.getText().toString());
                        Intent intent = new Intent(LogIn_Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else if (response.body().getResponse().equals("failed")) {
                        Toast.makeText(LogIn_Activity.this, "This user name is not available", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(LogIn_Activity.this, "not responding "+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LogIn_Activity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}
