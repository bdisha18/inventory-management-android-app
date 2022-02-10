package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagement.Auth.LoginActivity;
import com.example.inventorymanagement.Auth.RegisterActivity;

public class Authentication extends AppCompatActivity {

    Button signInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        signInBtn=findViewById(R.id.signIn);
        signUpBtn=findViewById(R.id.signUp);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Authentication.this, LoginActivity.class);
                startActivity(i1);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Authentication.this, RegisterActivity.class);
                startActivity(i2);
            }
        });
    }
}