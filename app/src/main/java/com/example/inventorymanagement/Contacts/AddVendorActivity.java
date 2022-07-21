package com.example.inventorymanagement.Contacts;

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
import com.example.inventorymanagement.R;

import java.util.HashMap;
import java.util.Map;

public class AddVendorActivity extends AppCompatActivity {

    Button add_contentBtn;
    TextView saveBtn;
    EditText vendor_name, contact_no, email,address , note, city, state, pincode ;
    TextView tv_pincode, tv_address, tv_state, tv_city, tv_content1, tv_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor);
        setTitle("Add New Vendor");

        vendor_name = findViewById(R.id.vendor_name);
        contact_no = findViewById(R.id.vendor_contact);
        email = findViewById(R.id.email);
        note = findViewById(R.id.note);
        address = findViewById(R.id.address);
        state= findViewById(R.id.state);
        pincode= findViewById(R.id.zipcode);
        city = findViewById(R.id.city);
        add_contentBtn = findViewById(R.id.addressBtn);
        tv_address = findViewById(R.id.tv_address);
        tv_city = findViewById(R.id.tv_city);
        tv_state = findViewById(R.id.tv_state);
        tv_pincode = findViewById(R.id.tv_zipcode);
        tv_content = findViewById(R.id.tv_address_content);
        tv_content1 = findViewById(R.id.tv_address_content1);
        add_contentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address.setVisibility((address.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                state.setVisibility((state.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                city.setVisibility((city.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                pincode.setVisibility((pincode.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                tv_address.setVisibility((tv_address.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                tv_pincode.setVisibility((tv_pincode.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                tv_state.setVisibility((tv_state.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                tv_city.setVisibility((tv_city.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                tv_content.setVisibility((tv_content.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
                tv_content1.setVisibility((tv_content1.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);
            }
        });


        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void insertData() {
        String s_vendor_name = vendor_name.getText().toString();
        String s_contact_no = contact_no.getText().toString();
        String s_email = email.getText().toString();
        String s_address = address.getText().toString();
        String s_note = note.getText().toString();
        String s_city = city.getText().toString();
        String s_state = state.getText().toString();
        String s_pincode = pincode.getText().toString();
        if (TextUtils.isEmpty(s_vendor_name) || (TextUtils.isEmpty(s_contact_no))) {
            Toast.makeText(AddVendorActivity.this, "Field cannot be Empty", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://10.0.2.2/inventoryApp/addVendors.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AddVendorActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddVendorActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("vendor_name", s_vendor_name);
                    params.put("vendor_contact", s_contact_no);
                    params.put("email", s_email);
                    params.put("address", s_address);
                    params.put("note", s_note);
                    params.put("city", s_city);
                    params.put("state", s_state);
                    params.put("pincode", s_pincode);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AddVendorActivity.this);
            requestQueue.add(request);
        }
        vendor_name.setText(null);
        contact_no.setText(null);
        email.setText(null);
        address.setText(null);
        note.setText(null);
        city.setText(null);
        state.setText(null);
        pincode.setText(null);
    }
}