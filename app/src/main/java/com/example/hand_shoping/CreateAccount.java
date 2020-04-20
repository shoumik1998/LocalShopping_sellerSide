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
    private EditText Name,UserName,UserPassword;
    private Button CreateAccButton;
    public  static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Name=findViewById(R.id.CAcceditnameID);
        UserName=findViewById(R.id.CaccounteditusernameID);
        UserPassword=findViewById(R.id.CccounteditpassID);
        CreateAccButton=findViewById(R.id.CaccountBtnID);
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);

        CreateAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Name.getText().toString())
                        ||TextUtils.isEmpty(UserName.getText().toString())
                        || TextUtils.isEmpty(UserPassword.getText().toString())){
                    Toast.makeText(CreateAccount.this, "All of the information is strongly required", Toast.LENGTH_SHORT).show();
                }else {
                    registration_user();

                }


            }
        });
    }

    private void registration_user() {

        String name=Name.getText().toString();
        String user_name=UserName.getText().toString();
        String user_password=UserPassword.getText().toString();

        Call<User> call=apiInterface.UserRegistration(name,user_name,user_password);

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

                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



    }
}
