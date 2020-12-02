package com.example.sixteen;




import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
    }

    public void setDetails(Context context,String image,String username,String address){

        ImageView imageView = itemView.findViewById(R.id.rImageView);
        TextView usernameTextView = itemView.findViewById(R.id.UsernameView);
        TextView addressTextView = itemView.findViewById(R.id.Addressview);


        Picasso.get().load(image).into(imageView);
        usernameTextView.setText(username);
        addressTextView.setText(address);

        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        itemView.setAnimation(animation);
    }
}