package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    private  Order_Recycler_Adapter adapter;
    private  ApiInterface apiInterface;
    List<Order_Model> orders;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);
        Toast.makeText(this, "kjhgkujjyhg", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.order_activity_recyclerID);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        apiInterface =ApiClient.getApiClient().create(ApiInterface.class);
        HashMap<String, String> order_map = new HashMap<>();
        order_map.put("user_name",MainActivity.getInstance().r_user_name());

        Call<List<Order_Model>> call = apiInterface.orders(order_map);

        call.enqueue(new Callback<List<Order_Model>>() {
            @Override
            public void onResponse(Call<List<Order_Model>> call, Response<List<Order_Model>> response) {
                if (response.isSuccessful()) {
                    orders = response.body();
                    adapter = new Order_Recycler_Adapter(orders, Order_Activity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(Order_Activity.this, "Response failed..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Order_Model>> call, Throwable t) {
                Toast.makeText(Order_Activity.this, "not responding...", Toast.LENGTH_SHORT).show();

            }
        });




//        PusherOptions options=new PusherOptions();
//        options.setCluster("ap2");
//        Pusher pusher = new Pusher("f4294e0ad72b1a26ebb2", options);
//
//        pusher.connect(new ConnectionEventListener() {
//            @Override
//            public void onConnectionStateChange(ConnectionStateChange change) {
//
//            }
//
//            @Override
//            public void onError(String message, String code, Exception e) {
//
//            }
//        },ConnectionState.ALL);
//
//        Channel channel = pusher.subscribe("my-channel");
//        channel.bind("my-event", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(final PusherEvent event) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(Order_Activity.this, "Event fired....", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                if (event != null) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(Order_Activity.this, "not available..", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });



    }
}