package com.example.inventorymanagement.Contacts;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddSupplierActivity extends AppCompatActivity {

    Button saveBtn;
    EditText supplier_name, contact_no, email, note, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        supplier_name = findViewById(R.id.supplier_name);
        contact_no = findViewById(R.id.contact_number);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        note = findViewById(R.id.note);


        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void insertData() {
        String s_supplier_name = supplier_name.getText().toString();
        String s_contact_no = contact_no.getText().toString();
        String s_email = email.getText().toString();
        String s_address = address.getText().toString();
        String s_note = note.getText().toString();

        if (TextUtils.isEmpty(s_supplier_name) || (TextUtils.isEmpty(s_contact_no))) {
            Toast.makeText(AddSupplierActivity.this, "Field cannot be Empty", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://192.168.0.123/inventoryApp/addSuppliers.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AddSupplierActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddSupplierActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("supplier_name", s_supplier_name);
                    params.put("supplier_contactno", s_contact_no);
                    params.put("supplier_email", s_email);
                    params.put("supplier_address", s_address);
                    params.put("note", s_note);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AddSupplierActivity.this);
            requestQueue.add(request);
        }
        supplier_name.setText(null);
        contact_no.setText(null);
        email.setText(null);
        address.setText(null);
        note.setText(null);
    }
}