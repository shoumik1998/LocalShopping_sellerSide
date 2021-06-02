package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order_Details_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private TextView p_name,p_price,p_number,total_cost,c_name,contact,address,issue_date,delivering_date;
    private Button cancel_btn,date_btn,receive_btn;
    private  int year,month,day,hour,minute;
    private ImageView imageView;
    ApiInterface apiInterface;
    String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details_);

        imageView = findViewById(R.id.order_dialog_imageID);
        p_name=findViewById(R.id.order_dialog_p_nameID);
        p_price=findViewById(R.id.order_dialog_p_priceID);
        p_number=findViewById(R.id.order_dialog_p_numberID);
        total_cost=findViewById(R.id.order_dialog_totalID);
        c_name=findViewById(R.id.order_dialog_c_nameID);
        contact=findViewById(R.id.order_dialog_contactID);
        address=findViewById(R.id.order_dialog_addressID);
        issue_date=findViewById(R.id.order_dialog_issue_dateID);
        delivering_date=findViewById(R.id.order_dialog_deliver_dateID);


        cancel_btn = findViewById(R.id.order_dialog_cancel_btnID);
        date_btn = findViewById(R.id.order_dialog_deliver_date_btnID);
        receive_btn = findViewById(R.id.order_dialog_receive_btnID);

        int p_num = Integer.valueOf(getIntent().getStringExtra("p_number"));
        int price = Integer.valueOf(getIntent().getStringExtra("p_price"));
        int total=p_num*price;


        product_id = getIntent().getStringExtra("p_id");
        Toast.makeText(this, ""+product_id, Toast.LENGTH_SHORT).show();
        p_name.setText(getIntent().getStringExtra("p_name"));
        p_price.setText(getIntent().getStringExtra("p_price"));
        p_number.setText(getIntent().getStringExtra("p_number"));
        total_cost.setText(String.valueOf(total));
        c_name.setText(getIntent().getStringExtra("c_name"));
        contact.setText(getIntent().getStringExtra("contact"));
        address.setText(getIntent().getStringExtra("address"));
        issue_date.setText(getIntent().getStringExtra("issue_date"));
        delivering_date.setText(getIntent().getStringExtra("delivering_date"));
        Glide.with(Order_Details_Activity.this).load(getIntent().getStringExtra("imagepath")).into(imageView);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Order_Details_Activity.this, "order cancled", Toast.LENGTH_SHORT).show();
            }
        });

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(Order_Details_Activity.this,
                        Order_Details_Activity.this,year,month,day);
                datePickerDialog.show();
            }
        });


        receive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delivering_date.getText().toString() != null) {
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("product_id",product_id);
                    map.put("status_code",1);
                    map.put("delivering_date", getIntent().getStringExtra("delivering_date"));
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<String> call=apiInterface.onOrder_Receive(map);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(Order_Details_Activity.this, "Order received", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Order_Details_Activity.this, "Order receiving failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(Order_Details_Activity.this, "Server is not responding", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year=year;
        this.month=month;
        this.day=dayOfMonth;
        Calendar c=Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Order_Details_Activity.this, Order_Details_Activity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour=hourOfDay;
        this.minute=minute;
        int modified_month=month+1;
        delivering_date.setText(year+"-"+modified_month+"-"+day+"  "+hour+" : "+minute);

    }
}