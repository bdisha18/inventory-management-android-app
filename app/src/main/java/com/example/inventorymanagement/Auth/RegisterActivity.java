package com.example.inventorymanagement.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagement.R;

public class RegisterActivity extends AppCompatActivity {

    private Button continueBtn;
    private EditText txtContact, txtAddress, txtEmail, txtFullname, txtPincode;
    private TextView loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtContact = findViewById(R.id.e_contactno);
        txtEmail = findViewById(R.id.e_email);
        txtFullname = findViewById(R.id.e_fullname);
        txtAddress = findViewById(R.id.e_address);
        txtPincode = findViewById(R.id.e_pincode);
        continueBtn = findViewById(R.id.signup_btn);

        loginUser = findViewById(R.id.signin_link);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }

    private void RegisterUser() {
        final String contact_no = txtContact.getText().toString().trim();
        final String address = txtAddress.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String full_name = txtFullname.getText().toString().trim();
        final String pincode = txtPincode.getText().toString().trim();

        if (TextUtils.isEmpty(full_name) || TextUtils.isEmpty(contact_no)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(pincode) || TextUtils.isEmpty(address)) {
            Toast.makeText(RegisterActivity.this, "Fill up all the details!", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(RegisterActivity.this, Register2Activity.class);
            i.putExtra("ContactNo", contact_no);
            i.putExtra("Address", address);
            i.putExtra("Email", email);
            i.putExtra("FullName", full_name);
            i.putExtra("Pincode", pincode);
            startActivity(i);
        }

    }
}