package com.example.sunday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ChatRVAdapter extends RecyclerView.Adapter{
    private final ArrayList<ChatsModal> chatsModalArrayList;
    private Context context;

    public ChatRVAdapter(ArrayList<ChatsModal> chatsModalArrayList, Context context) {
        this.chatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_layout, parent, false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModal modal = chatsModalArrayList.get(position);
        switch (modal.getSender()) {
            case "user":
                ((UserViewHolder)holder).userTV.setText(modal.getMessage());
                break;
            case "bot":
                ((BotViewHolder) holder).botTV.setText(modal.getMessage());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + modal.getSender());
        }
    }

    @Override
    public int getItemCount() {
        return chatsModalArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsModalArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userTV;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.idTVuser);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView botTV;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botTV = itemView.findViewById(R.id.idTVBot);
        }
    }
}



/*
public class ChatRVAdapter extends RecyclerView.Adapter {

    private ArrayList<ChatsModal> chatsModalArrayList;
    private Context context;

    public ChatRVAdapter(ArrayList<ChatsModal> chatsModalArrayList, Context context) {
        this.chatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
                return new UserViewHolder(view);

            case 1:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_layout, parent, false);
                return new UserViewHolder(view);
        }
        Toast.makeText(context, "ChatRVAdapter ERROR", Toast.LENGTH_LONG).show();
        return null;

    }

    //ChatsModal
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModal modal = chatsModalArrayList.get(position);
        switch (modal.getSender()) {
            case "user":
                ((UserViewHolder) holder).userTV.setText(modal.getMessage());
                break;
            case "bot":
                ((BotMsgViewHolder) holder).botMSgTV.setText(modal.getMessage());
                break;
        }
    }



    @Override
    public int getItemCount() {
        return chatsModalArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsModalArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userTV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userTV = itemView.findViewById(R.id.idTVuser);
        }
    }

    public  static  class  BotMsgViewHolder extends  RecyclerView.ViewHolder{
        TextView botMSgTV;
        public BotMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            botMSgTV =itemView.findViewById(R.id.idTVBot);
        }
    }


}

 *///Error
