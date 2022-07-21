package com.example.inventorymanagement.Products;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.Models.ProductCategory;
import com.example.inventorymanagement.Models.ProductLocations;
import com.example.inventorymanagement.R;
import com.example.inventorymanagement.Stock.StockIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {
    String product_id;
    String product_name;
    String sale_price;
    String purchase_price;
    String s_barcode;
    String s_image;
    ImageView imv;
    TextView barcode, productName, quantity, salePrice, purchasePrice;
    List<ProductCategory> productCategories;
    List<Locations> locations;
    List<ProductLocations> productLocations;
    String url = "http://10.0.2.2/inventoryApp/productDetailsCategoryList.php";
    String locationUrl = "http://10.0.2.2/inventoryApp/productDetailsLocationList.php";
    TextView txt_stockin;
    EditText e_quantity;
    int add;
    String s_add, s_subtract;
    TextView txt_quantity;
    private RecyclerView categoryView, locationView;
    private ProductDetailCategoryListAdapter categoryListAdapter;
    private ProductDetailLocationListAdapter locationListAdapter;
    private ProductDetailLocationListAdapter.OnLocationListener listener;
    private int number = 0, count, s_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        setTitle("Product Details");

        imv = findViewById(R.id.product_imv);
        barcode = findViewById(R.id.barcode);
        productName = findViewById(R.id.product_name);
        quantity = findViewById(R.id.product_qty);
        salePrice = findViewById(R.id.sale_price);
        purchasePrice = findViewById(R.id.purchase_price);

        categoryView = findViewById(R.id.categoryListView);
        locationView = findViewById(R.id.locationList);

        Bundle extras = getIntent().getExtras();
        product_id = extras.getString("product_id");
        product_name = extras.getString("product_name");
        sale_price = extras.getString("sale_price");
        purchase_price = extras.getString("purchase_price");
        s_barcode = extras.getString("barcode");

        productName.setText(product_name);
        salePrice.setText(sale_price);
        purchasePrice.setText(purchase_price);
        barcode.setText(s_barcode);


        categoryView.setHasFixedSize(true);
        categoryView.setLayoutManager(new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL, false));
        productCategories = new ArrayList<ProductCategory>();

        locationView.setHasFixedSize(true);
        locationView.setLayoutManager(new LinearLayoutManager(ProductDetails.this));
        locations = new ArrayList<Locations>();
        productLocations = new ArrayList<ProductLocations>();

        retrieveCategory();
        retrieveLocation();
        setOnClickListener();
    }


    private void retrieveCategory() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_categoryValue = object.getString("category_value").trim();
                        String s_product_id = object.getString("product_id").trim();

                        ProductCategory productCategory = new ProductCategory();

                        if (s_product_id.equals(product_id)) {
                            productCategory.setCategory_value(s_categoryValue);
                        }
                        productCategories.add(productCategory);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                categoryListAdapter = new ProductDetailCategoryListAdapter(ProductDetails.this, productCategories);
                categoryView.setAdapter(categoryListAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDetails.this);
        requestQueue.add(request);
    }

    private void retrieveLocation() {

        JsonArrayRequest request = new JsonArrayRequest(locationUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);


                        String s_location = object.getString("location_name").trim();
                        String s_location_id = object.getString("location_id").trim();
                        String s_quantity = object.getString("quantity").trim();

                        ProductLocations productLocation = new ProductLocations();
                        Locations location = new Locations();
                        location.setName(s_location);
                        productLocation.setLocation_id(s_location_id);
                        productLocation.setQuantity(s_quantity);

                        locations.add(location);
                        productLocations.add(productLocation);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                locationListAdapter = new ProductDetailLocationListAdapter(ProductDetails.this, locations, productLocations, listener);
                locationView.setAdapter(locationListAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDetails.this);
        requestQueue.add(request);
    }

    private void StockInOutMoveDialog(Bundle b) {
        final AlertDialog.Builder d = new AlertDialog.Builder(ProductDetails.this);
        View mView =getLayoutInflater().inflate(R.layout.stockinout_dialog_box, null);

        d.setView(mView);

        final AlertDialog dialog = d.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // change width and height of dialog
        String quantity = b.getString("quantity");
        String location_name = b.getString("location_name");
        String location_id = b.getString("location_id");

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        dialog.getWindow().setLayout(layoutParams.MATCH_PARENT, layoutParams.WRAP_CONTENT);

        txt_stockin = mView.findViewById(R.id.stock_in);
        txt_stockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tv_stockIn = txt_stockin.getText().toString();

                Bundle b = new Bundle();
                b.putString("stock_type", tv_stockIn);
                b.putString("quantity", quantity);
                b.putString("location_name", location_name);
                b.putString("location_id", location_id);
                QuantityDialogBox(b);
                dialog.dismiss();
            }
        });

        TextView txt_stockout = mView.findViewById(R.id.stock_out);
        txt_stockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tv_stockOut = txt_stockout.getText().toString();
                Bundle b = new Bundle();
                b.putString("quantity", quantity);
                b.putString("location_name", location_name);
                b.putString("location_id", location_id);
                b.putString("stock_type", tv_stockOut);
                QuantityDialogBox(b);
                dialog.dismiss();
            }
        });

//        TextView txt_stockmove = mView.findViewById(R.id.stock_move);
//        txt_stockmove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String tv_stockMove = txt_stockmove.getText().toString();
//                Bundle b = new Bundle();
//                b.putString("quantity", quantity);
//                b.putString("location_name", location_name);
//                b.putString("location_id", location_id);
//                b.putString("stock_type", tv_stockMove);
//                QuantityDialogBox(b);
//                dialog.dismiss();
//            }
//        });

        TextView txt_audit = mView.findViewById(R.id.audit);
        txt_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tv_audit = txt_audit.getText().toString();
                Bundle b = new Bundle();
                b.putString("quantity", quantity);
                b.putString("location_name", location_name);
                b.putString("location_id", location_id);
                b.putString("stock_type", tv_audit);
                QuantityDialogBox(b);
                dialog.dismiss();
            }
        });

        ImageView cancelBtn = mView.findViewById(R.id.cancel_option);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void QuantityDialogBox(Bundle b) {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.quantity_dialog_box, null);

        e_quantity = mView.findViewById(R.id.quantity_edit);
        final TextView txt_product_name = mView.findViewById(R.id.dialog_productName);
        final ImageView addQuantity = mView.findViewById(R.id.add_quantity);
        final ImageView removeQuantity = mView.findViewById(R.id.remove_quantity);
        txt_quantity = mView.findViewById(R.id.inventory_quantity);
        final TextView txt_quantity_total = mView.findViewById(R.id.inventory_quantity_total);

        Button cancel = mView.findViewById(R.id.cancelBtn);
        Button add = mView.findViewById(R.id.addBtn);

        String productName = b.getString("product_name");
        String locationName = b.getString("location_name");
        String locationId = b.getString("location_id");
        String quantity = b.getString("quantity");
        String stock = b.getString("stock_type");

        txt_product_name.setText(productName);
        txt_quantity.setText(String.valueOf(quantity));

        txt_quantity_total.setText(String.valueOf(quantity));

        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stock.equals("Stock In")){
                Log.d("gfvsfg", stock);

                int add = Integer.parseInt(quantity) + Integer.parseInt(s.toString());
                s_add = String.valueOf(add);
                txt_quantity.setText(s_add);
                } else {
                    int subtract = Integer.parseInt(quantity) - Integer.parseInt(s.toString());
                    s_subtract = String.valueOf(subtract);
                    txt_quantity.setText(s_subtract);
                }
            }
        };

        e_quantity.addTextChangedListener(inputTextWatcher);


        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = combine();
                if (count > 0) {
                    count = count + 1;
                    e_quantity.setText("" + count);

                    int text = Integer.parseInt(txt_quantity.getText().toString());
                    txt_quantity.setText("" + text);
                } else {
                    e_quantity.setText(0);
                }

            }

        });

        removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = combine();
                if (count > 0) {
                    count = count - 1;
                    e_quantity.setText("" + count);
                    int text = Integer.parseInt(txt_quantity.getText().toString());
                    txt_quantity.setText("" + text);
                } else {
                    e_quantity.setText(0);
                }
            }
        });


        d.setView(mView);

        final AlertDialog dialog = d.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // change width and height of dialog

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.8f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity = e_quantity.getText().toString();
                dialog.dismiss();

                Intent i = new Intent(ProductDetails.this, StockIn.class);
                i.putExtra("location_name", locationName);
                i.putExtra("client_type", "customer");
                i.putExtra("location_id", locationId);
                i.putExtra("product_id", product_id);
                i.putExtra("quantity", quantity);
                i.putExtra("stock_type", stock);
                startActivity(i);

            }
        });
    }

    private int combine() {
        s_quantity = Integer.parseInt(e_quantity.getText().toString());
        return s_quantity;
    }


    private void setOnClickListener() {
        listener = new ProductDetailLocationListAdapter.OnLocationListener() {
            @Override
            public void onLocationClick(View v, int position) {
                Bundle b = new Bundle();
                b.putString("product_name", product_name);
                b.putString("location_id", productLocations.get(position).getLocation_id());
                b.putString("location_name", locations.get(position).getName());
                b.putString("quantity", productLocations.get(position).getQuantity());

                StockInOutMoveDialog(b);
            }
        };
    }

}
