package com.example.hand_shoping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Order_Recycler_Adapter extends RecyclerView.Adapter<Order_Recycler_Adapter.Order_ViewHolder> {
    private  ApiInterface apiInterface;
    private List<Order_Model> order_list;
    private Context context;

    public Order_Recycler_Adapter(List<Order_Model> order_list, Context context) {
        this.order_list = order_list;
        this.context = context;
    }

    @NonNull
    @Override
    public Order_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_item_layout,parent,false);
        return new Order_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Order_ViewHolder holder, int position) {
        Order_Model order_model_list = order_list.get(position);
        holder.p_name.setText(order_model_list.getProduct_name());
        //holder.p_price.setText(order_model_list.getProduct_price());
        holder.p_number.setText(order_model_list.getNumber_of_product());
        holder.destination.setText(order_model_list.getAddress());
        //holder.issue_date.setText(order_model_list.getIssue_date());
        //holder.delivering_date.setText(order_model_list.getDelivering_date());

        Glide.with(context).load(order_model_list.getImagepath()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "order clicked...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return order_list.size();
    }


    public  class  Order_ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView p_name,p_price,p_number,issue_date,delivering_date,destination;
        CardView cardView;

        public Order_ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.order_item_imageID);
            p_name = itemView.findViewById(R.id.product_name_txtID);
            //p_price = itemView.findViewById(R.id.product_price_txtID);
            p_number = itemView.findViewById(R.id.product_number_txtID);
            //issue_date = itemView.findViewById(R.id.issue_date_txtID);
            //delivering_date = itemView.findViewById(R.id.delivary_date_txtID);
            destination = itemView.findViewById(R.id.destination_txtID);
            cardView = itemView.findViewById(R.id.order_item_cardID);


        }
    }
}



