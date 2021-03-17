package com.example.hand_shoping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {

    private static AllContents allContents;
    private List<Fetching_Image> myImage;
    private Context context;
    private ApiInterface apiInterface;
    private Filter pro_filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Fetching_Image> filterd_img = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                allContents.getInstance().fetch_all_products();
            } else {
                String filtering_text = constraint.toString().toLowerCase().trim();
                for (Fetching_Image item : myImage) {
                    if (item.getName().toLowerCase().contains(filtering_text)) {
                        filterd_img.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterd_img;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myImage.clear();
            myImage.addAll((List) results.values);
            notifyDataSetChanged();

        }

    };

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
        holder.Name.setText(myImg.getName());
        holder.Price.setText("Price :" + myImg.getPrice() + " " + myImg.getCurrency());
        Glide.with(context).load(myImg.getImage_Path()).into(holder.imageView);
        if (!allContents.is_in_action_mode) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

        if (myImg.isChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = LayoutInflater.from(context).inflate(R.layout.update_product_layout, null);

                final AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(view)
                        .setTitle("Update Title and Price ")
                        .setPositiveButton("Update", null)
                        .setNegativeButton("cancel", null)
                        .show();
                final EditText update_product_title = view.findViewById(R.id.update_product_nameID);
                final EditText update_product_price = view.findViewById(R.id.update_product_priceID);
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,Object> map=new HashMap<>();
                        map.put("id",myImg.getId());
                        map.put("user_name",MainActivity.getInstance().r_user_name());
                        map.put("product_name",update_product_title.getText().toString());
                        map.put("product_price", Integer.parseInt(update_product_price.getText().toString()));

                        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                        Call<Fetching_Image> call = apiInterface.update_product(map);
                        call.enqueue(new Callback<Fetching_Image>() {
                            @Override
                            public void onResponse(Call<Fetching_Image> call, Response<Fetching_Image> response) {
                                if (response.body().getResponse().equals("updated")) {
                                    myImg.setName(update_product_title.getText().toString());
                                    myImg.setPrice(update_product_price.getText().toString());
                                    holder.Name.setText(myImg.getName());
                                    holder.Price.setText(myImg.getPrice());
                                    Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                } else if (response.body().getResponse().equals("failed")) {
                                    Toast.makeText(context, "Failed dto update", Toast.LENGTH_SHORT).show();
                                } else if (response.body().getResponse().equals("Invalid_high")) {
                                    Toast.makeText(context, "Maximum length of title is 50", Toast.LENGTH_SHORT).show();

                                } else if (response.body().getResponse().equals("Invalid")) {
                                    Toast.makeText(context, "Use letters and numbers only", Toast.LENGTH_SHORT).show();
                                } else if (response.body().getResponse().equals("exists")) {
                                    Toast.makeText(context, "This title already exists", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Fetching_Image> call, Throwable t) {
                                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });

                update_product_title.setText(myImg.getName());
                update_product_price.setText(myImg.getPrice());


            }
        });


    }

    @Override
    public int getItemCount() {
        return myImage.size();
    }

    @Override
    public Filter getFilter() {
        return pro_filter;
    }

    public void update_Adapter(List<Fetching_Image> fetching_images) {
        for (Fetching_Image fetchingImage : fetching_images) {
            myImage.remove(fetchingImage);
        }
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView Name, Price;
        CheckBox checkBox;
        AllContents allContents;
        CardView cardView;


        public MyViewHolder(View itemView, AllContents allContents) {
            super(itemView);
            imageView = itemView.findViewById(R.id.proIMgID);
            Name = itemView.findViewById(R.id.proNameID);
            Price = itemView.findViewById(R.id.propriceID);
            checkBox = itemView.findViewById(R.id.chechBoxID);

            this.allContents = allContents;
            cardView = itemView.findViewById(R.id.cardID);
            cardView.setOnLongClickListener(allContents);
            checkBox.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            if (myImage.get(getAdapterPosition()).isChecked()) {
                ((CheckBox) v).setChecked(false);
                myImage.get(getAdapterPosition()).setChecked(false);
            } else {
                ((CheckBox) v).setChecked(true);
                myImage.get(getAdapterPosition()).setChecked(true);

            }
            allContents.prepareSelection(v, getAdapterPosition());
            checkBox = (CheckBox) v;
        }

    }

}
