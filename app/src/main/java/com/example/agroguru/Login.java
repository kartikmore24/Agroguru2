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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText username,password;
    Button login;
    AGdatabase mDatabaseHelper;

    ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Sign In");
        progressDialog.setMessage("Signing In to your Account");

        auth = FirebaseAuth.getInstance();

        username = findViewById(R.id.ed1);
        password = findViewById(R.id.ed2);
        login = findViewById(R.id.btnlogin);
        mDatabaseHelper = new AGdatabase(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username.getText().toString();
                String Password = password.getText().toString();
                if (email.length() != 0 && password.length() != 0) {
                    progressDialog.show();
                    auth.signInWithEmailAndPassword
                            (email, Password).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(Login.this, Agropanel.class);
                                        finish();
                                        startActivity(i);
                                    }
                                    else {
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(Login.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(Login.this, Agropanel.class);
            finish();
            startActivity(intent);
        }
    }


    //==Registration
    public void reg(View view) {
        Intent i = new Intent(Login.this, Registration.class);
        finish();
        startActivity(i);
    }
}