package com.example.inventorymanagement.Auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagement.R;

public class ForgotPassword extends AppCompatActivity {

    private Button b1;
    private EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        b1=findViewById(R.id.reset_button);
        e1=findViewById(R.id.reset_email);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

//                auth.sendPasswordResetEmail(email)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(ForgotPassword.this, Login.class));
//                                } else {
//                                    String message = task.getException().getMessage();
//                                    Toast.makeText(ForgotPassword.this, "Error Ocuurred " + message, Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
            }
        });
    }
}