package com.example.hand_shoping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Helper_Adapter extends RecyclerView.Adapter<Helper_Adapter.Helper_ViewHolder> {

    private  Context context;
    private  List<Helper_Model> helper;

    public Helper_Adapter(Context context, List<Helper_Model> helper) {
        this.context = context;
        this.helper = helper;
    }


    @NonNull
    @Override
    public Helper_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.helper_layout,parent,false);
        return new Helper_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Helper_ViewHolder holder, int position) {
        holder.Country.setText(helper.get(position).getCountry());
        holder.Code.setText("Code : "+helper.get(position).getCode());


    }

    @Override
    public int getItemCount() {
        return helper.size();
    }


    public  class  Helper_ViewHolder extends RecyclerView.ViewHolder {
        private TextView Country,Code;


        public Helper_ViewHolder (@NonNull View itemView) {
            super(itemView);
            Country=itemView.findViewById(R.id.cNameID);
            Code=itemView.findViewById(R.id.cCodeID);

        }
    }
}
