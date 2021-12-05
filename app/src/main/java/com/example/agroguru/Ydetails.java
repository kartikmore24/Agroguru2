package com.example.agroguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Ydetails extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseFirestore database;
    private FirestoreRecyclerAdapter<ViewModel, HarvestViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ydetails);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseFirestore.getInstance();

        Query query = database.collection("harvestDetails");

        FirestoreRecyclerOptions<ViewModel> options = new FirestoreRecyclerOptions.Builder<ViewModel>()
                .setQuery(query, ViewModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ViewModel, HarvestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HarvestViewHolder holder, int position, @NonNull ViewModel model) {
                holder.mPeriod.setText(model.getPeriod());
                holder.mCrop_type.setText(model.getCropType());
                holder.mProduction.setText(model.getProduction());
                holder.mRegion.setText(model.getRegion());
                holder.mArea.setText(model.getArea());
            }

            @NonNull
            @Override
            public HarvestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewrecycler, parent, false);
                return new HarvestViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);

    }

    private class HarvestViewHolder extends RecyclerView.ViewHolder {
        private View view;
        TextView mPeriod, mCrop_type, mProduction, mRegion, mArea;

        HarvestViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            mPeriod = (TextView) view.findViewById(R.id.pid);
            mCrop_type = (TextView) view.findViewById(R.id.cid);
            mProduction = (TextView) view.findViewById(R.id.prid);
            mRegion = (TextView) view.findViewById(R.id.rid);
            mArea = (TextView) view.findViewById(R.id.aid);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}