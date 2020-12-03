package com.example.sixteen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class RecyclerAdapter extends ArrayAdapter<String>{
    private Context mContext;
    private ArrayList<Messages> messagesList;
    private ImageLoader img;

    public RecyclerAdapter(@NonNull Context context, int resource,ArrayList<Messages> arr,ImageLoader imageloader) {
        super(context, resource);
        this.messagesList = arr;
        this.mContext = context;
        this.img = imageloader;
    }


    @Override
    public int getCount() {
        return messagesList.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(mContext);
        View v = li.inflate(R.layout.message_item,null);
        TextView tv = (TextView)v.findViewById(R.id.textView);
        tv.setText(messagesList.get(position).getUsername());
        TextView tv1 = (TextView)v.findViewById(R.id.textView1);
        tv1.setText(messagesList.get(position).getEmail());
        final NetworkImageView im = (NetworkImageView)v.findViewById(R.id.imageView);
        im.setImageUrl(messagesList.get(position).getImage(),img);
        return v;
    }
}