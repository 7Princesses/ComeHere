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

public class MyPageAdapter extends RecyclerView.Adapter<MyPageAdapter.CustomViewHolder> {

    private ArrayList<MyPageData> arrayList;
    private Context context;

    public MyPageAdapter(ArrayList<MyPageData> arrayList) {
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
        holder.userNickname.setText(arrayList.get(position).getUserNickname());
        holder.userId.setText(String.valueOf(arrayList.get(position).getUserId()));
        holder.school.setText(String.valueOf(arrayList.get(position).getSchool()));
        holder.identity.setText(String.valueOf(arrayList.get(position).getIdentity()));
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView userNickname;
        TextView userId;
        TextView school;
        TextView identity;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userNickname = itemView.findViewById(R.id.userNickname);
            this.userId = itemView.findViewById(R.id.userId);
            this.school = (TextView)itemView.findViewById(R.id.school);
            this.identity = (TextView)itemView.findViewById(R.id.identity);
        }
    }
}