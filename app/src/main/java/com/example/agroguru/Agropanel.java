package com.example.agroguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class Agropanel extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_agropanel);

        auth = FirebaseAuth.getInstance();
    }
    public void predict(View view) {
        Intent i = new Intent(Agropanel.this, Prediction.class);
        startActivity(i);
    }
    public void analise(View view) {
        Intent i = new Intent(Agropanel.this, Analysis.class);
        startActivity(i);
    }
    public void add(View view) {
        Intent i = new Intent(Agropanel.this, Udetails.class);
        startActivity(i);
    }
    public void details(View view) {
        Intent i = new Intent(Agropanel.this, Ydetails.class);
        startActivity(i);
    }
    public void blogs(View view) {
        Intent i = new Intent(Agropanel.this, Blogs.class);
        startActivity(i);
    }
    public void gov(View view) {
        Intent i = new Intent(Agropanel.this, Schemes.class);
        startActivity(i);
    }
    public void logout(View view) {
        auth.signOut();
        Intent intent = new Intent(Agropanel.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
    public void videos(View view){
        Intent i = new Intent(Agropanel.this, Videos.class);
        startActivity(i);
    }
}