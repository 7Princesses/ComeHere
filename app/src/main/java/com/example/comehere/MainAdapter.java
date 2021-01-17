package com.example.comehere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;
    private Context context;

    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPhoto())
                .into(holder.iv_photo);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_price.setText(String.valueOf(arrayList.get(position).getPrice()));
        holder.tv_remain.setText(String.valueOf(arrayList.get(position).getRemain()));
        holder.tv_unit.setText(arrayList.get(position).getUnit());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photo;
        TextView tv_title;
        TextView tv_unit;
        TextView tv_price;
        TextView tv_remain;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_photo = itemView.findViewById(R.id.iv_photo);
            this.tv_price = itemView.findViewById(R.id.tv_price);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_unit = itemView.findViewById(R.id.tv_unit);
            this.tv_remain = itemView.findViewById(R.id.tv_remain);
        }
    }
}