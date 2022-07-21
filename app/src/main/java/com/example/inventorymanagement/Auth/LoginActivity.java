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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.DashboardActivity;
import com.example.inventorymanagement.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn, otpButton;
    TextView registerUser, forgot_password;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.e_email);
        password = findViewById(R.id.e_password);

        forgot_password = findViewById(R.id.forgotpassword_link);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        registerUser = findViewById(R.id.signup_link);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

//        otpButton = findViewById(R.id.otp_button);
//        otpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(LoginActivity.this, OtpGenerate.class);
//                startActivity(i);
//            }
//        });


        loginBtn = findViewById(R.id.signin_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              loginBtn();
            }
        });
    }

    private void loginBtn(){
        String txtEmail = email.getText().toString().trim();
        final String txtPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
            Toast.makeText(getApplicationContext(), "Enter Credentials!", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://10.0.2.2/inventoryApp/loginUser.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    email.setText("");
                    password.setText("");
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    i.putExtra("email", txtEmail);
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this,
                            "not login",
                            Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", txtEmail);
                    params.put("password", txtPassword);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }

    }
}