package com.example.sixteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Final extends AppCompatActivity {
    //widgets
    RecyclerView recyclerView;
    //firebase
    private DatabaseReference myRef;
    //Variable
    private ArrayList<Messages> messagesList;

    private Context mContext;
    private  RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mContext,messagesList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();
        //ArrayList
        messagesList = new ArrayList<>();
        // Clear ArrayList
        ClearAll();
        //Get Data Method
        GetDataFromFirebase();



    }
    private void GetDataFromFirebase(){
        Query query = myRef.child("pics");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Messages messages = new Messages();
                    messages.setImage(snapshot.child("url").getValue().toString());
                    messages.setUsername(snapshot.child("username").getValue().toString());
                    messages.setEmail(snapshot.child("email").getValue().toString());
                    messages.setLocation(snapshot.child("address").getValue().toString());
                    messagesList.add(messages);
                }
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), messagesList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(messagesList!=null){
            messagesList.clear();
            if(recyclerAdapter!=null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        messagesList = new ArrayList<>();
    }
}