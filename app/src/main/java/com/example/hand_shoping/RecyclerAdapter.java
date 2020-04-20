package com.example.hand_shoping;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private  List<Fetching_Image> myImage;
    private Context context;

    public RecyclerAdapter(List<Fetching_Image> myImage, Context context) {
        this.myImage = myImage;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_layout,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final  MyViewHolder holder, int position) {
        final  Fetching_Image myImg=myImage.get(position);
        holder.Name.setText(myImage.get(position).getName());
        holder.Price.setText("Price :"+myImage.get(position).getPrice());
        holder.id.setText("ID : "+myImage.get(position).getId());
        Glide.with(context).load(myImage.get(position).getImage_Path()).into(holder.imageView);
        holder.view.setBackgroundColor(myImg.isSelected() ? Color.CYAN:Color.WHITE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myImg.setSelected(!myImg.isSelected());
                holder.view.setBackgroundColor(myImg.isSelected()? Color.CYAN:Color.WHITE);
                int id=myImg.getId();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myImage.size();
    }






    public  class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView Name,Price,id;
         View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            imageView=itemView.findViewById(R.id.proIMgID);
            Name=itemView.findViewById(R.id.proNameID);
            Price=itemView.findViewById(R.id.propriceID);
            id=itemView.findViewById(R.id.id_ID);


        }


    }


}
