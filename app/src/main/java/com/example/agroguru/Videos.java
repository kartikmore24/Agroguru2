package com.example.agroguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Videos extends AppCompatActivity {

    RecyclerView Mrecyclerview;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Mrecyclerview = findViewById(R.id.recyclerview_video);
        Mrecyclerview.setHasFixedSize(true);
        Mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("video");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<member> options =
                new FirebaseRecyclerOptions.Builder<member>()
                        .setQuery(reference, member.class)
                        .build();

        FirebaseRecyclerAdapter<member, ViewHolder>firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<member, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull member model) {
                         holder.setVideo(getApplication(), model.getTitle(), model.getUrl());

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                        return new ViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        Mrecyclerview.setAdapter(firebaseRecyclerAdapter);







    }
}