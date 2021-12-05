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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText email, name,mob,adharno,place,pin,pass;
    Button register;
    AGdatabase mDatabaseHelper;
    ProgressDialog progressDialog;

    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(Registration.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Your account is being created");

        email = (EditText) findViewById(R.id.editTextEmail);
        name = (EditText) findViewById(R.id.editText1);
        mob = (EditText) findViewById(R.id.editText2);
        adharno = (EditText) findViewById(R.id.editText3);
        place = (EditText) findViewById(R.id.editText4);
        pin = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText6);
        register = findViewById(R.id.btnreg);

        mDatabaseHelper = new AGdatabase(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailEntry = email.getText().toString();
                String nameEntry = name.getText().toString();
                String mobEntry = mob.getText().toString();
                String adharnoEntry = adharno.getText().toString();
                String placeEntry = place.getText().toString();
                String pinEntry = pin.getText().toString();
                String passwordEntry = pass.getText().toString();

                if (emailEntry.length() != 0 && passwordEntry.length() != 0 && nameEntry.length() != 0 && mobEntry.length() != 0 && adharnoEntry.length() != 0 && placeEntry.length() != 0 && pinEntry.length() != 0) {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(emailEntry, passwordEntry).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Map<String, String> user = new HashMap<>();
                                user.put("email", emailEntry);
                                user.put("name", nameEntry);
                                user.put("mobileNumber", mobEntry);
                                user.put("aadharNumber", adharnoEntry);
                                user.put("place", placeEntry);
                                user.put("pincode", pinEntry);
                                String id = task.getResult().getUser().getUid();
                                database.collection("users").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Registration.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(Registration.this, Login.class);
                                        finish();
                                        startActivity(i);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Registration.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                            else {
                                Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Registration.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void AddData(String nameEntry,String mobEntry,String adharnoEntry,String placeEntry,String pinEntry,String passEntry){
        boolean insertData = mDatabaseHelper.addData(nameEntry,mobEntry,adharnoEntry,placeEntry,pinEntry,passEntry);
        if (insertData == true){
            Toast.makeText(Registration.this,"Registration successful 👍",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Registration.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
    //==AGROPANEL
    public void backtologin(View view) {
        Intent i = new Intent(Registration.this, Login.class);
        finish();
        startActivity(i);
    }
}