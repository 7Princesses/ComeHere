package com.example.comehere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myName;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_msg;
        public MyViewHolder(View v) {
            super(v);
            tv_name = v.findViewById(R.id.tv_name);
            tv_msg = v.findViewById(R.id.tv_msg);
        }
    }


    public ChatAdapter(List<ChatData> myDataset, Context context, String myName) {
        mDataset = myDataset;
        this.myName = myName;
    }


    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatData chat = mDataset.get(position);

        holder.tv_name.setText(chat.getName());
        holder.tv_msg.setText(chat.getMsg());

        if(chat.getName().equals(this.myName)) {
            holder.tv_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.tv_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        else {
            holder.tv_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.tv_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

    }


    @Override
    public int getItemCount() {
        return mDataset == null ? 0 :  mDataset.size();
    }

    public void addChat(ChatData chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);
    }
}
