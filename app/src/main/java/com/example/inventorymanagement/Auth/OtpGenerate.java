package com.example.inventorymanagement.Auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagement.R;

public class OtpGenerate extends AppCompatActivity {
    EditText e_phone;
    Button sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate);

//        e_phone= findViewById(R.id.generate_otp);
//        sendOtp= findViewById(R.id.otp_button);
//
//        sendOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String s_phone = e_phone.getText().toString().trim();
//
//                if(s_phone.isEmpty() || s_phone.length() < 10){
//                    e_phone.setError("Enter a valid phone number!!");
//                    e_phone.requestFocus();
//                    return;
//                }
//
//                Intent intent = new Intent(OtpGenerate.this, OtpVerification.class);
//                intent.putExtra("phone", s_phone);
//                startActivity(intent);
//            }
//        });
    }
}