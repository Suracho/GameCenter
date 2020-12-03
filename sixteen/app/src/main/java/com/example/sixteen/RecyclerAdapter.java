package com.example.sixteen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private  static final  String tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Messages> messagesList;

    public RecyclerAdapter(Context mContext, ArrayList<Messages> messagesList) {
        this.mContext = mContext;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(messagesList.get(position).getLocation());
        //ImageView : Glide Library
//        Picasso.get().load(messagesList.get(position).getImage()).into(holder.imageView).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder_error);
        Glide.with(mContext)
                .load(messagesList.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public  class  ViewHolder extends  RecyclerView.ViewHolder{
        //Widgets
        ImageView imageView;
        TextView textView;
        public  ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
