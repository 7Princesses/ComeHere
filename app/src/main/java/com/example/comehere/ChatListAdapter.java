package com.example.comehere;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.CustomViewHolder> {

    private ArrayList<ChatRoom> cr_List;
    private Context cr_Context;
    private String username;

    public ChatListAdapter(String username, ArrayList<ChatRoom> cr_List, Context cr_Context) {
        this.username = username;
        this.cr_List = cr_List;
        this.cr_Context = cr_Context;
    }

    public ArrayList<ChatRoom> getArrayList() {
        return cr_List;
    }

    public void setArrayList(ArrayList<ChatRoom> cr_List) {
        this.cr_List = cr_List;
    }

    public Context getContext() {
        return cr_Context;
    }

    public void setContext(Context cr_Context) {
        this.cr_Context = cr_Context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_room, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        String status_str = cr_List.get(position).getCurrentNum() + " / "
                            + cr_List.get(position).getMaxNum();
        holder.tv_cr_status.setText(status_str);
        holder.tv_cr_name.setText(cr_List.get(position).getRoomName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("cr_name", cr_List.get(position).getRoomName());
                intent.putExtra("username", username);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cr_List != null ? cr_List.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cr_status;
        TextView tv_cr_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_cr_status = itemView.findViewById(R.id.tv_cr_status);
            this.tv_cr_name = itemView.findViewById(R.id.tv_cr_name);
        }
    }
}
