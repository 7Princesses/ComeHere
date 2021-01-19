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
                .load(arrayList.get(position).getImageIv())
                .into(holder.imageIv);
        holder.titleTv.setText(arrayList.get(position).getTitleTv());
        holder.priceTv.setText(String.valueOf(arrayList.get(position).getPriceTv()));
        holder.remain.setText(String.valueOf(arrayList.get(position).getRemain()));
        holder.stick.setText(String.valueOf(arrayList.get(position).getStick()));
        holder.unitTv.setText(arrayList.get(position).getUnitTv());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageIv;
        TextView titleTv;
        TextView unitTv;
        TextView priceTv;
        TextView stick;
        TextView remain;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageIv = itemView.findViewById(R.id.imageIv);
            this.priceTv = itemView.findViewById(R.id.priceTv);
            this.titleTv = (TextView)itemView.findViewById(R.id.titleTv);
            this.unitTv = (TextView)itemView.findViewById(R.id.unitTv);
            this.remain = itemView.findViewById(R.id.remain);
            this.stick = itemView.findViewById(R.id.stick);
        }
    }
}