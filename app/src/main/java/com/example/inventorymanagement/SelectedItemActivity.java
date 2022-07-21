package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.Contacts.CustomerListAdapter;
import com.example.inventorymanagement.Contacts.VendorListAdapter;
import com.example.inventorymanagement.Location.LocationListAdapter;
import com.example.inventorymanagement.Models.Customers;
import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.Models.Vendors;
import com.example.inventorymanagement.Products.ProductListAdapter;
import com.example.inventorymanagement.Stock.StockIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedItemActivity extends AppCompatActivity {
ImageView backBtn, addBtn;
RecyclerView recyclerView;
List<Vendors> vendors;
TextView title;
List<Customers> customers;
List<Locations> locations;
ArrayList<Products> products;
String customerUrl = "http://10.0.2.2/inventoryApp/customerList.php";
String vendorUrl = "http://10.0.2.2/inventoryApp/vendorList.php";
ProductListAdapter.OnProductListener listener;
private CustomerListAdapter customerListAdapter;
private VendorListAdapter vendorListAdapter;
private LocationListAdapter locationListAdapter;
private ProductListAdapter productListAdapter;

CustomerListAdapter.OnCustomerListener customerListener;
VendorListAdapter.OnVendorListener vendorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        setOnProductClickListener();
        title = findViewById(R.id.list);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        String client = getIntent().getStringExtra("List");
        String location = getIntent().getStringExtra("location");
        String product = getIntent().getStringExtra("item");

        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectedItemActivity.this, LinearLayoutManager.VERTICAL, false));

        vendors = new ArrayList<Vendors>();
        customers = new ArrayList<Customers>();
        locations = new ArrayList<Locations>();
        products = new ArrayList<Products>();

        if (product != null && product.equals("item")){
            retrieveProducts();
        }

        if (location != null && location.equals("location")){
            retrieveLocation();
        }

        if (client != null && client.equals("Vendors")){
            title.setText("Vendors");
            retrieveVendors();
        }else if (client != null && client.equals("Customers")){
            title.setText("Customers");
            retrieveCustomers();
        }

    }

    private void retrieveProducts() {
        String url1 = "http://10.0.2.2/inventoryApp/productList.php";
        JsonArrayRequest request = new JsonArrayRequest(url1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        String s_id = object.getString("product_id").trim();
                        String s_name = object.getString("product_name").trim();
                        String s_image = object.getString("product_image").trim();
                        String s_barcode = object.getString("product_code").trim();
                        String s_purchase_price = object.getString("purchase_price").trim();
                        String s_sale_price = object.getString("sale_price").trim();
                        String s_quantity = object.getString("quantity").trim();

                        Products product = new Products();

                        product.setId(s_id);
                        product.setDesign(s_name);
                        product.setImage(s_image);
                        product.setBarcode(s_barcode);
                        product.setPurchasePrice(s_purchase_price);
                        product.setSalePrice(s_sale_price);
                        product.setQuantity(s_quantity);
                        products.add(product);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                productListAdapter = new ProductListAdapter(SelectedItemActivity.this, products, listener);
                recyclerView.setAdapter(productListAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectedItemActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SelectedItemActivity.this);
        requestQueue.add(request);
    }


    private void retrieveCustomers() {
        JsonArrayRequest request = new JsonArrayRequest(customerUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_customerName = object.getString("customer_name").trim();

                        Customers customer = new Customers();
                        customer.setName(s_customerName);

                        customers.add(customer);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                customerListAdapter = new CustomerListAdapter(SelectedItemActivity.this, customers, customerListener);
                recyclerView.setAdapter(customerListAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectedItemActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SelectedItemActivity.this);
        requestQueue.add(request);
    }

    private void retrieveVendors() {
        JsonArrayRequest request = new JsonArrayRequest(vendorUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_vendorName = object.getString("vendor_name").trim();

                        Vendors vendor = new Vendors();

                        vendor.setName(s_vendorName);
                        vendors.add(vendor);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                vendorListAdapter = new VendorListAdapter(SelectedItemActivity.this, vendors, vendorListener);
                recyclerView.setAdapter(vendorListAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectedItemActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SelectedItemActivity.this);
        requestQueue.add(request);
    }

    private void retrieveLocation() {
        String url = "http://10.0.2.2/inventoryApp/locationList.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_name = object.getString("location_name").trim();

                        Locations location = new Locations();
                        location.setName(s_name);
                        locations.add(location);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                locationListAdapter = new LocationListAdapter(SelectedItemActivity.this, locations);
                recyclerView.setAdapter(locationListAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectedItemActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SelectedItemActivity.this);
        requestQueue.add(request);
    }

    private void setOnProductClickListener() {
        listener = new ProductListAdapter.OnProductListener() {
            @Override
            public void onProductClick(View v, int position) {
                Intent i = new Intent(SelectedItemActivity.this, StockIn.class);
                i.putExtra("product_id", products.get(position).getId());
                i.putExtra("product_name", products.get(position).getDesign());
//                i.putExtra("sale_price", products.get(position).getSalePrice());
//                i.putExtra("purchase_price", products.get(position).getPurchasePrice());
                i.putExtra("barcode", products.get(position).getBarcode());
//                i.putExtra("image", products.get(position).getImage());
                i.putExtra("quantity", products.get(position).getQuantity());
                startActivity(i);

            }
        };
    }

}