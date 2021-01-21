package com.example.comehere;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatData> cList;
    private String username;
    private String master;
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_msg;
        public TextView tv_master;
        public LinearLayout chat_align;
        public ChatViewHolder(View v) {
            super(v);
            tv_name = v.findViewById(R.id.tv_name);
            tv_msg = v.findViewById(R.id.tv_msg);
            tv_master = v.findViewById(R.id.tv_master);
            chat_align = v.findViewById(R.id.chat_align);
        }
    }

    public ChatAdapter(List<ChatData> myDataset, Context context, String username, String master) {
        cList = myDataset;
        this.username = username;
        this.master = master;
    }


    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        ChatViewHolder holder = new ChatViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatData chat = cList.get(position);

        holder.tv_name.setText(chat.getName());
        holder.tv_msg.setText(chat.getMsg());

        if(chat.getName().equals(this.username)) {
            holder.tv_msg.setBackgroundResource(R.drawable.bg_chatbubble_me);
            holder.chat_align.setGravity(Gravity.RIGHT);
        }
        else {
            holder.chat_align.setGravity(Gravity.LEFT);
        }

        if(chat.getName().equals(this.master)){
            holder.tv_master.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return cList == null ? 0 :  cList.size();
    }

    public void addChat(ChatData chat) {
        cList.add(chat);
        notifyItemInserted(cList.size()-1);
    }
}
