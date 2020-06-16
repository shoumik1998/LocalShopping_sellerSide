package com.example.hand_shoping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okio.Utf8;

public class Helper_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  Helper_Adapter adapter;
    private List<Helper_Model> country_codes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_);

        recyclerView=findViewById(R.id.recyclerViewHelperID);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            String jsonToString=readJsonFromFile();
            JSONArray jsonArray=new JSONArray(jsonToString);

            for (int i=0;i<jsonArray.length();i++){
                JSONObject cObject=jsonArray.getJSONObject(i);
                String name=cObject.getString("name");
                String code=cObject.getString("dial_code");

               Helper_Model model=new Helper_Model(name,code);
               country_codes.add(model);

            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        adapter= new Helper_Adapter(Helper_Activity.this,country_codes);
        recyclerView.setAdapter(adapter);


    }

    private  String readJsonFromFile()  throws IOException{

        InputStream inputStream=null;
        StringBuilder builder=new StringBuilder();

        try {
            String jsonTostring=null;
            inputStream=getResources().openRawResource(R.raw.countrycodes);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            while ((jsonTostring=bufferedReader.readLine())!=null){
                builder.append(jsonTostring);
            }

        }finally {
            if (inputStream!=null){
                inputStream.close();
            }
        }
        return  new String(builder);

    }
}
