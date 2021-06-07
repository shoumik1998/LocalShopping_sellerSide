package com.example.hand_shoping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        holder.p_number.setText(order_model_list.getNumber_of_product());
        holder.destination.setText(order_model_list.getAddress());

        if (order_model_list.order_status == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#7fe5f0"));
        }

        Glide.with(context).load(order_model_list.getImagepath()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Order_Details_Activity.class);
                intent.putExtra("p_id", order_model_list.getProduct_id());
                intent.putExtra("p_name",order_model_list.getProduct_name());
                intent.putExtra("p_price", order_model_list.getProduct_price());
                intent.putExtra("p_number", order_model_list.getNumber_of_product());
                intent.putExtra("c_name", order_model_list.getClient_name());
                intent.putExtra("contact", order_model_list.getContact_no());
                intent.putExtra("address", order_model_list.getAddress());
                intent.putExtra("issue_date", order_model_list.getIssue_date());
                intent.putExtra("delivering_date", order_model_list.getDelivering_date());
                intent.putExtra("phn_gmail", order_model_list.getPhn_gmail());
                intent.putExtra("imagepath", order_model_list.getImagepath());
                context.startActivity(intent);

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



