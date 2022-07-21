package com.example.inventorymanagement.Stock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.R;
import com.example.inventorymanagement.SelectedItemActivity;
import com.example.inventorymanagement.TimePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StockIn extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText e_date, e_time, e_vendor, e_note;
    TextView tv_location, tv_product_name, tv_barcode, tv_item, tv_quantity, tv_client, tv_stockType;
    ImageView product_imv, imv_item_arrow;
    ConstraintLayout itemCl;
    String s_date, s_time, s_vendor, s_location, s_item, s_note, s_type;
    TextView saveBtn;
    String location_name, location_id, product_id, client_type, quantity, stock_type;
    private Calendar calendar;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in);
        e_date = findViewById(R.id.date_input);
        e_time = findViewById(R.id.time_input);
        e_vendor = findViewById(R.id.vendor);
        e_note = findViewById(R.id.e_note);

        imv_item_arrow = findViewById(R.id.imageView17);

        saveBtn = findViewById(R.id.add_product_btn);

        tv_location = findViewById(R.id.location);
        tv_barcode = findViewById(R.id.barcode);
        tv_product_name = findViewById(R.id.product_name);
        tv_quantity = findViewById(R.id.quantity);
        tv_item = findViewById(R.id.item);
        tv_client = findViewById(R.id.clients);
        tv_stockType = findViewById(R.id.stock_type);

        itemCl = findViewById(R.id.item_cl);

        location_name = getIntent().getStringExtra("location_name");
        location_id = getIntent().getStringExtra("location_id");
        client_type = getIntent().getStringExtra("client_type");
        product_id = getIntent().getStringExtra("product_id");
        quantity = getIntent().getStringExtra("quantity");
        stock_type = getIntent().getStringExtra("stock_type");

        if (location_name != null) {
            tv_location.setText(location_name);
        }
        Products product = new Products();
        if ((product_id != null)  &&  ((product_id).equals(product.getId())) ) {
            itemCl.setVisibility(View.VISIBLE);
            tv_item.setVisibility(View.GONE);
            imv_item_arrow.setVisibility(View.GONE);
            tv_barcode.setText(product.getBarcode());
            tv_product_name.setText(product.getDesign());
            tv_quantity.setText(product.getQuantity());
        }

        tv_quantity.setText(quantity);

        if (stock_type == "Stock In"){
            tv_client.setText("Customer");
            tv_stockType.setTextColor(this.getResources().getColor(R.color.sky_blue));
            tv_stockType.setText("Stock In");
            setTitle("Stock In");
        }else if (stock_type == "Stock Out"){
            tv_client.setText("Vendor");
            tv_stockType.setText("Stock Out");
            tv_stockType.setTextColor(this.getResources().getColor(R.color.orange));
            setTitle("Stock Out");
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        e_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        e_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment datePicker = new com.example.inventorymanagement.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        e_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StockIn.this, SelectedItemActivity.class);
                if (stock_type == "Stock In") {
                    i.putExtra("List", "Customers");
                }else {
                    i.putExtra("List", "Vendors");
                }
                startActivity(i);
            }
        });
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StockIn.this, SelectedItemActivity.class);
                i.putExtra("location", "location");
                startActivity(i);
            }
        });

        tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StockIn.this, SelectedItemActivity.class);
                i.putExtra("item", "item");
                startActivity(i);
            }
        });




    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        e_date.setText(year + "-" + month + "-" +day);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int i, int i1) {
        e_time.setText(i + ":" + i1 + ":" + "00");
    }

    private void insertData() {

        s_date = e_date.getText().toString().trim();
        s_time = e_time.getText().toString().trim();
        s_vendor = e_vendor.getText().toString().trim();
        s_note = e_note.getText().toString().trim();

        String url = "http://10.0.2.2/inventoryApp/addStocks.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(StockIn.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockIn.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("location_id", location_id);
                params.put("product_id", product_id);
                params.put("stock_type", stock_type);
                params.put("quantity", quantity);
                if (stock_type == "Stock In"){
                    params.put("client_type", "Customer");
                } else {
                    params.put("client_type", "Vendor");
                }
                params.put("vendor_id", "34");
                params.put("customer_id", "5");
                params.put("date", s_date);
                params.put("time", s_time);
                params.put("note", s_note);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(StockIn.this);
        requestQueue.add(request);

        e_date.setText(null);
        e_time.setText(null);
        e_note.setText(null);
        e_vendor.setText(null);
}

}