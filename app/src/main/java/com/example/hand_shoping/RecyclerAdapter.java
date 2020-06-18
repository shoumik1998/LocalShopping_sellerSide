package com.example.hand_shoping;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {

    private List<Fetching_Image> myImage;
    private Context context;
    private  static AllContents allContents;


    public RecyclerAdapter(List<Fetching_Image> myImage, Context context) {
        this.myImage = myImage;
        this.context = context;
        allContents = (AllContents) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_layout, parent, false);
        return new MyViewHolder(view, allContents);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Fetching_Image myImg = myImage.get(position);
        holder.Name.setText(myImage.get(position).getName()+" "+myImg.getId());
        holder.Price.setText("Price :" + myImage.get(position).getPrice()+" "+myImg.getCurrency());
        Glide.with(context).load(myImage.get(position).getImage_Path()).into(holder.imageView);
        if (!allContents.is_in_action_mode) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return myImage.size();
    }

    @Override
    public Filter getFilter() {
        return pro_filter;
    }

    private Filter pro_filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Fetching_Image> filterd_img=new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                allContents.getInstance().fetch_all_products();
            }else {
                String filtering_text=constraint.toString().toLowerCase().trim();
                for (Fetching_Image item: myImage){
                    if (item.getName().toLowerCase().contains(filtering_text)){
                        filterd_img.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterd_img;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myImage.clear();
            myImage.addAll((List)results.values);
            notifyDataSetChanged();

        }

    };


    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView Name,Price;
         View view;
         CheckBox checkBox;
         AllContents allContents;
         CardView cardView;

        public MyViewHolder( View itemView,AllContents allContents) {
            super(itemView);
            imageView=itemView.findViewById(R.id.proIMgID);
            Name=itemView.findViewById(R.id.proNameID);
            Price=itemView.findViewById(R.id.propriceID);
            checkBox=itemView.findViewById(R.id.chechBoxID);
            this.allContents=allContents;
            cardView=itemView.findViewById(R.id.cardID);
            cardView.setLongClickable(true);
            cardView.setOnLongClickListener(allContents);
            checkBox.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            allContents.prepareSelection(v,getAdapterPosition());
        }

    }

    public  void  update_Adapter(List<Fetching_Image> fetching_images){
        for (Fetching_Image fetchingImage:fetching_images){
            myImage.remove(fetchingImage);
        }
        notifyDataSetChanged();

    }



}
