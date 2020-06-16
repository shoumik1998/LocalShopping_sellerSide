package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Reset_Password extends AppCompatActivity {
    private EditText Verif_code,Reset_pass,Reset_pass_agin;
    private Button Submitbtn,ResetPassbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        Verif_code=findViewById(R.id.resetPass_edit_v_codeID);
        Reset_pass=findViewById(R.id.newPassEditID);
        Reset_pass_agin=findViewById(R.id.RenewPassEditID);
        Submitbtn=findViewById(R.id.resetPassBtn_v_codeID);
        ResetPassbtn=findViewById(R.id.resetPass_resetbtnID);


    }
}
