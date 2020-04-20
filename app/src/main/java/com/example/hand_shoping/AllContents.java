package com.example.hand_shoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllContents extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private List<Fetching_Image> myImage;
    private  RecyclerAdapter adapter;
    private ApiInterface apiInterface;
    private Context context;
    private  Fetching_Image fetching_image;
    View view;
    RecyclerAdapter.MyViewHolder myViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contents);

        fetching_image=new Fetching_Image();

        recyclerView=findViewById(R.id.recyclerID);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        fetch_all_products();
    }

    public  void  fetch_all_products(){
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Fetching_Image>> call=apiInterface.image_fetching(MainActivity.getInstance().r_user_name());

        call.enqueue(new Callback<List<Fetching_Image>>() {
            @Override
            public void onResponse(Call<List<Fetching_Image>> call, Response<List<Fetching_Image>> response) {
                if (response.isSuccessful()){
                    myImage=response.body();
                    adapter=new RecyclerAdapter(myImage,AllContents.this);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(AllContents.this, "nooo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Fetching_Image>> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_item_id:



            case R.id.select_all_id:
        }
        return  true;
    }


}
