package com.example.agroguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Udetails extends AppCompatActivity {

    EditText period,crop_type,production,region,area;
    Button add;
    AGdatabase mDatabaseHelper;

    ProgressDialog progressDialog;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_udetails);

        database = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(Udetails.this);
        progressDialog.setTitle("Harvest Details");
        progressDialog.setMessage("Adding Harvest Details");

        period = (EditText) findViewById(R.id.editText7);
        crop_type = (EditText) findViewById(R.id.editText8);
        production = (EditText) findViewById(R.id.editText9);
        region = (EditText) findViewById(R.id.editText10);
        area = (EditText) findViewById(R.id.editText111);
        add = findViewById(R.id.btnadd);

        mDatabaseHelper = new AGdatabase(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String periodEntry = period.getText().toString();
                String crop_typeEntry = crop_type.getText().toString();
                String productionEntry = production.getText().toString();
                String regionEntry = region.getText().toString();
                String areaEntry = area.getText().toString();

                if (periodEntry.length() != 0 && crop_typeEntry.length() != 0 && productionEntry.length() != 0 && regionEntry.length() != 0 && areaEntry.length() != 0){
                    Map<String, String> harvestDetails = new HashMap<>();
                    harvestDetails.put("period", periodEntry);
                    harvestDetails.put("cropType", crop_typeEntry);
                    harvestDetails.put("production", productionEntry);
                    harvestDetails.put("region", regionEntry);
                    harvestDetails.put("area", areaEntry);

                    database.collection("harvestDetails").add(harvestDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Udetails.this, "Harvest Details Added Successfully", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Udetails.this, Agropanel.class);
                            finish();
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Udetails.this, "Failed to Add Harvest Details", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(Udetails.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddData(String periodEntry,String crop_typeEntry,String productionEntry,String regionEntry,String areaEntry){
        boolean insertData = mDatabaseHelper.addData(periodEntry,crop_typeEntry,productionEntry,regionEntry,areaEntry);
        if (insertData == true){
            Toast.makeText(Udetails.this,"Details added successfully",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Udetails.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
}