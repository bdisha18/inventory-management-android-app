package com.example.inventorymanagement.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.R;

import java.util.HashMap;
import java.util.Map;

public class Register2Activity extends AppCompatActivity {

    EditText txtCompanyName, txtPassword;
    Button backBtn, SignUpBtn;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        txtCompanyName = findViewById(R.id.e_companyname);
        txtPassword = findViewById(R.id.e_password);
        SignUpBtn = findViewById(R.id.signup_btn);
//        StrictMode.enableDefaults(); // This is enable thread policy to call internet service with one or more application at same time.


        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register2Activity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void AddUser() {
        Intent i = getIntent();
        final String contact = i.getStringExtra("ContactNo");
        final String address = i.getStringExtra("Address");
        final String email = i.getStringExtra("Email");
        final String full_name = i.getStringExtra("FullName");
        final String pincode = i.getStringExtra("Pincode");

        final String company_name = txtCompanyName.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();


        if (TextUtils.isEmpty(company_name) || TextUtils.isEmpty(password)) {
            Toast.makeText(Register2Activity.this, "Fill up all details!", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            txtPassword.setError("Password should be atleast of 6 letters.");
        } else {
            String url = "http://10.0.2.2:8080/inventoryApp/addUser.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Register2Activity.this, response, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Register2Activity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Register2Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("full_name", full_name);
                    params.put("company_name", company_name);
                    params.put("email", email);
                    params.put("contact", contact);
                    params.put("address", address);
                    params.put("pincode", pincode);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Register2Activity.this);
            requestQueue.add(request);
        }
    }
}