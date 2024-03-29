package com.example.hand_shoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllContents extends AppCompatActivity implements View.OnLongClickListener {

    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private List<Fetching_Image> myImage;
    private  RecyclerAdapter adapter;
    private ApiInterface apiInterface;
    private  Fetching_Image fetching_image;
    private Toolbar toolbar;
    private  SearchView searchView;
    private  SearchManager searchManager;
    boolean is_in_action_mode=false;
    TextView counter_text_view;
    ArrayList<Fetching_Image> selection_img=new ArrayList<>();
    ArrayList<Integer> selected_id;
    ArrayList<Integer> tempList_ID=new ArrayList<>();
    ArrayList<Fetching_Image> tempList_Image=new ArrayList<>();

    int count=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contents);

        fetching_image=new Fetching_Image();
        recyclerView=findViewById(R.id.recyclerID);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        toolbar=findViewById(R.id.Allcontents_toolbarID);
        counter_text_view=findViewById(R.id.counter_text_id);
        counter_text_view.setVisibility(View.GONE);
        selected_id=new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fetch_all_products();
    }

    public  AllContents getInstance(){
        return this;
    }

    public  void  fetch_all_products(){
        HashMap<String,String> map=new HashMap<>();
        map.put("user_name", MainActivity.getInstance().r_user_name());
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Fetching_Image>> call=apiInterface.image_fetching(map);

        call.enqueue(new Callback<List<Fetching_Image>>() {
            @Override
            public void onResponse(Call<List<Fetching_Image>> call, Response<List<Fetching_Image>> response) {
                if (response.isSuccessful()){
                    myImage=response.body();
                    adapter=new RecyclerAdapter(myImage,AllContents.this);
                    recyclerView.setAdapter(adapter);

                }else {
                    Toast.makeText(AllContents.this, "server not respond ..try later", Toast.LENGTH_SHORT).show();
                    myImage=response.body();
                    adapter=new RecyclerAdapter(myImage,AllContents.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Fetching_Image>> call, Throwable t) {
                Toast.makeText(AllContents.this, "Connection failed , please try again "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchitemID);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
         searchManager=(SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search product ");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText!=null) {
                    adapter.getFilter().filter(newText);
                }else {
                    Toast.makeText(AllContents.this, "Wait till fetching products properly ", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId()==R.id.delete_item_id){
           RecyclerAdapter recyclerAdapter= adapter;
           recyclerAdapter.update_Adapter(selection_img);
           reset_actionbar();
           delete_products();


       }else if (item.getItemId()==android.R.id.home){
           if (is_in_action_mode){
               finish();
               overridePendingTransition(0,0);
               startActivity(getIntent());
               overridePendingTransition(0,0);
           }else {
               finish();
           }

       }else  if(item.getItemId()==R.id.acc_detailsID){
           startActivity(new Intent(AllContents.this,Account_Details.class));
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public boolean onLongClick(View v) {

        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.delete_menu);
        counter_text_view.setVisibility(View.VISIBLE);
        is_in_action_mode=true;
        adapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    public  void   prepareSelection(View view,int position){
        if (((CheckBox)view).isChecked()){
            selection_img.add(myImage.get(position));
            selected_id.add(myImage.get(position).getId());
            count=count+1;
            update_selection(count);
        }else{
            try {
                for (int i = 0; i <= selected_id.size(); i++) {
                    tempList_ID.add(myImage.get(position).getId());
                    tempList_Image.add(myImage.get(position));
                }
                selected_id.removeAll(tempList_ID);
                selection_img.removeAll(tempList_Image);
            } catch (Exception e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            count = count - 1;
            update_selection(count);


        }

    }
    public  void  update_selection(int counter){
        if (counter==0){
            counter_text_view.setText(counter+"  item selected");
        }else if (counter==1){
            counter_text_view.setText(counter+ " item selected");
        }else {
            counter_text_view.setText(counter+ " items selected");
        }

    }

    public  void  reset_actionbar() {
        is_in_action_mode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.search_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        counter_text_view.setVisibility(View.GONE);
        count = 0;
        selection_img.clear();
    }


    @Override
    public void onBackPressed() {
        if (is_in_action_mode){
            reset_actionbar();
            adapter.notifyDataSetChanged();
        }else {
            super.onBackPressed();
        }


    }

    public  void  delete_products(){

        HashMap<String,Object> map=new HashMap<>();
        map.put("ids",selected_id);
        map.put("user_name",MainActivity.getInstance().r_user_name());
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<Fetching_Image> call=apiInterface.delete_products(map);
        call.enqueue(new Callback<Fetching_Image>() {
            @Override
            public void onResponse(Call<Fetching_Image> call, Response<Fetching_Image> response) {
                if (response.body().getResponse().equals("ok")) {
                        Toast.makeText(AllContents.this, "Products deleted successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AllContents.this, "no response ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Fetching_Image> call, Throwable t) {
                Toast.makeText(AllContents.this, "", Toast.LENGTH_SHORT).show();

            }
        });


    }



}
