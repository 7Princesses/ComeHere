package com.example.comehere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPageArticleListAdapter extends RecyclerView.Adapter<MyPageArticleListAdapter.CustomViewHolder> {

    private ArrayList<ArticleData> arrayList;
    private Context context;

    public MyPageArticleListAdapter(ArrayList<ArticleData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_article, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
       /* Glide.with(holder.itemView)
                .load(arrayList.get(position).getImageIv())
                .into(holder.imageIv);*/

        holder.titleTv.setText(arrayList.get(position).getProductName());
        holder.priceTv.setText(String.valueOf(arrayList.get(position).getTotalPrice()));
        holder.remain.setText(String.valueOf(arrayList.get(position).getProductCount()));
        holder.stick.setText(String.valueOf(arrayList.get(position).getProductCount()));      // 수정 필요
        holder.unitTv.setText(arrayList.get(position).getUnit());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public ArrayList<ArticleData> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ArticleData> arrayList) {
        this.arrayList = arrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //ImageView imageIv;
        TextView titleTv;
        TextView unitTv;
        TextView priceTv;
        TextView stick;
        TextView remain;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.imageIv = itemView.findViewById(R.id.imageIv);
            this.priceTv = itemView.findViewById(R.id.priceTv);
            this.titleTv = (TextView)itemView.findViewById(R.id.titleTv);
            this.unitTv = (TextView)itemView.findViewById(R.id.unitTv);
            this.remain = itemView.findViewById(R.id.remain);
            this.stick = itemView.findViewById(R.id.stick);
        }
    }
}