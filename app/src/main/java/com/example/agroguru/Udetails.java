package com.example.agroguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Udetails extends AppCompatActivity {

    EditText production,region,area;
    Button add;

    ProgressDialog progressDialog;
    FirebaseFirestore database;

    Spinner monthSpinner, yearSpinner, cropTypeSpinner;
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

        production = (EditText) findViewById(R.id.editText9);
        region = (EditText) findViewById(R.id.editText10);
        area = (EditText) findViewById(R.id.editText111);
        add = findViewById(R.id.btnadd);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        cropTypeSpinner = findViewById(R.id.cropTypeSpinner);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monthEntry = monthSpinner.getSelectedItem().toString();
                String yearEntry = yearSpinner.getSelectedItem().toString();
                String cropTypeEntry = cropTypeSpinner.getSelectedItem().toString();
                String productionEntry = production.getText().toString();
                String regionEntry = region.getText().toString();
                String areaEntry = area.getText().toString();

                if (!monthEntry.equals("Month") && !yearEntry.equals("Year") && !cropTypeEntry.equals("Crop Type") && productionEntry.length() != 0 && regionEntry.length() != 0 && areaEntry.length() != 0){
                    Map<String, String> harvestDetails = new HashMap<>();
                    harvestDetails.put("month", monthEntry);
                    harvestDetails.put("year", yearEntry);
                    harvestDetails.put("cropType", cropTypeEntry);
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
                    Toast.makeText(Udetails.this,"Fill all the Fields Properly",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}