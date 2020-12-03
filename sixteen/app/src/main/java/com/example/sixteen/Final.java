package com.example.sixteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.sixteen.Messages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Final extends AppCompatActivity {
    //widgets
    ListView recyclerView;
    //firebase
    private DatabaseReference myRef;
    //Variable
    public ArrayList<Messages> messagesList;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        recyclerView = findViewById(R.id.listview);
        myRef = FirebaseDatabase.getInstance().getReference();
        // Clear ArrayList
        //Get Data Method
        GetDataFromFirebase();
    }
    private void fun(final ArrayList<Messages> me)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageLoader im = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            LruCache<String, Bitmap> cache = new LruCache<String,Bitmap>(me.size());
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
        RecyclerAdapter ad = new RecyclerAdapter(this,R.layout.message_item,me,im);
        recyclerView.setAdapter(ad);
    }
    private void GetDataFromFirebase(){
        Query query = myRef.child("pics");
        messagesList = new ArrayList<Messages>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int s = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();
                    messages.setImage(snapshot.child("url").getValue().toString());
                    messages.setUsername(snapshot.child("username").getValue().toString());
                    messages.setEmail(snapshot.child("email").getValue().toString());
                    messages.setLocation(snapshot.child("address").getValue().toString());
                    messagesList.add(messages);
                }
                fun(messagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}