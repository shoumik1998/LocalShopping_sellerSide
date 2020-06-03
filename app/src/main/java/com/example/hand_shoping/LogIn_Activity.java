package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn_Activity extends AppCompatActivity {
    private Button loginbtn;
    private TextView createAccTextV;
    private EditText User_name,User_password;
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

        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);


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

    }

    private void login_user() {

        String user_name=User_name.getText().toString();
        String user_password=User_password.getText().toString();

        Call<User> call =apiInterface.userLogIn(user_name,user_password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("OK")){
                    Toast.makeText(LogIn_Activity.this, "hmmmmmmmm", Toast.LENGTH_SHORT).show();
                    MainActivity.getInstance().wlogStatus(true);
                    MainActivity.getInstance().wName(response.body().getName(),User_name.getText().toString());
                    Intent intent=new Intent(LogIn_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                }else if(response.body().getResponse().equals("failed")){
                    Toast.makeText(LogIn_Activity.this, "LogIn  failed...Please try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



    }


}
