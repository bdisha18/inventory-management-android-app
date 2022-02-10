package com.example.inventorymanagement.Products;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.inventorymanagement.Models.Categories;
import com.example.inventorymanagement.Models.ProductCategory;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryActivity extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 0;
    Button saveBtn;
    ImageView categoryBtn, backBtn;
    //    EditText categoryTxt;
    EditText valueText;
    TextView txtValue;
    ProductCategoryListAdapter adapter;
    String s_product, s_barcode, s_image, s_sale, s_purchase, quantity;
    EditText purchase_price, sale_price;
    int s_quantity;
    Boolean isOpen = false;
    RelativeLayout categoryList;
    EditText e_quantity;
    String url = "http://192.168.0.123/inventoryApp/categoryList.php";
    private RecyclerView recyclerView;
    private List<Categories> categories;
    private List<ProductCategory> values;
    private int year, month, day;
    private int number=0, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

//        txtValue = findViewById(R.id.txt_value);
        purchase_price = findViewById(R.id.purchase_price);
        sale_price = findViewById(R.id.sale_price);
//        categoryList=findViewById(R.id.categoryAdd);
//
//        categoryList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isOpen) {
//                    recyclerView.setVisibility(View.INVISIBLE);
//                    isOpen = false;
//                } else {
//                    recyclerView.setVisibility(View.VISIBLE);
//                    isOpen = true;
//                }
//            }
//        });


        recyclerView = findViewById(R.id.categoryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductCategoryActivity.this));

        retrieveCategory();

//            categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    model = categories.get(position);
//                    if ((model.getType()).contentEquals("Number")) {
//                        String s_value1 = valueText.getText().toString();
//                        Toast.makeText(ProductCategory.this, s_value1, Toast.LENGTH_SHORT).show();
//                        valueText.setText(s_value1);
//
//                    } if (model.getType().contentEquals("Date")) {
//                        final Calendar c = Calendar.getInstance();
//                        year = c.get(Calendar.YEAR);
//                        month = c.get(Calendar.MONTH);
//                        day = c.get(Calendar.DAY_OF_MONTH);
//
//
//                        DatePickerDialog datePickerDialog = new DatePickerDialog(ProductCategory.this,
//                                new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                                c.set(Calendar.YEAR, year);
//                                c.set(Calendar.MONTH, month);
//                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                                String myFormat = "MM/dd/yy"; //In which you need put here
//                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                                valueText.setText(sdf.format(c.getTime()));
//                            }
//                                }, year, month, day);
//                        datePickerDialog.show();
//                    }else {
////                      String s_value3 = getIntent().getExtras().getString("selected_text");
//                        Intent i = new Intent(ProductCategory.this, CategoryValue.class);
//                        i.putExtra("category_id", model.getId());
//                        startActivity(i);
//                    }
//                }
//            });

        categories = new ArrayList<Categories>();
//        values = new ArrayList<CategoryValueModel>();



//        backBtn = findViewById(R.id.back_button);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


        categoryBtn = findViewById(R.id.add_category_btn);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductCategoryActivity.this, CategoryMain.class);
                startActivity(i);
            }
        });
        saveBtn = findViewById(R.id.add_product_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QuantityDialogBox();


            }
        });

    }

    private void retrieveCategory() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_name = object.getString("category_name").trim();
                        String s_type = object.getString("category_type").trim();

                        Categories category = new Categories();
//                        category.setId(s_id);
                        category.setCategory(s_name);
                        category.setType(s_type);
                        categories.add(category);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                adapter = new ProductCategoryListAdapter(ProductCategoryActivity.this, categories);
                recyclerView.setAdapter(adapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductCategoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ProductCategoryActivity.this);
        requestQueue.add(request);
    }



    public void QuantityDialogBox() {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.quantity_dialog_box, null);

        e_quantity = mView.findViewById(R.id.quantity_edit);
        final TextView txt_product_name = mView.findViewById(R.id.dialog_productName);
        final ImageView addQuantity = mView.findViewById(R.id.add_quantity);
        final ImageView removeQuantity = mView.findViewById(R.id.remove_quantity);
        final TextView txt_quantity= mView.findViewById(R.id.inventory_quantity);


        Button cancel = mView.findViewById(R.id.cancelBtn);
        Button add = mView.findViewById(R.id.addBtn);

        s_product = getIntent().getStringExtra("product_name");
        s_barcode = getIntent().getStringExtra("barcode");
//        final int i = Integer.parseInt(e_quantity.getText().toString());

        s_image = getIntent().getStringExtra("image");
        quantity = String.valueOf(count);
        txt_product_name.setText(s_product);
        txt_quantity.setText(s_quantity);
//        final int value = Integer.parseInt(e_quantity.getText().toString().trim());
//        count = value;


        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = combine();
                if (count > 0) {
                    count = count + 1;
                    e_quantity.setText("" + count);
                    txt_quantity.setText("" + count);
//                }

//                else {
//                    txt_quantity.setText("" +String.valueOf(i+1));
//                    e_quantity.setText("" +String.valueOf(i+1));
                }

            }

        });

        removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = combine();
                if(count > 0) {
                    count = count - 1;
                    e_quantity.setText("" + count);
                    txt_quantity.setText("" + count);
                }
//                else {
//                    txt_quantity.setText("" + String.valueOf(i-1));
//                    e_quantity.setText("" + String.valueOf(i-1));
//                }
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
            public void onClick(View v) {


                if (TextUtils.isEmpty(s_product) || TextUtils.isEmpty(s_barcode)) {
                    Toast.makeText(ProductCategoryActivity.this, "Filds cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    //save
//                    String key = model.getCategory();
//                    String s_value = valueText.getText().toString();
                    s_sale = sale_price.getText().toString();
                    s_purchase = purchase_price.getText().toString();

//                    String products_id = db_product.push().getKey();
//                    Products pro = new Products(products_id, s_product, s_image, s_barcode, quantity, s_sale, s_purchase);
//                    db_product.child(products_id).setValue(pro);
//                    db_product.child(products_id).child(key).setValue(s_value);
                    Intent i = new Intent(ProductCategoryActivity.this, Products.class);
                    startActivity(i);

                    Toast.makeText(ProductCategoryActivity.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }

        });

        dialog.show();

    }

    private int combine() {
        s_quantity = Integer.parseInt(e_quantity.getText().toString());
        int new_number = s_quantity + number;
        return new_number;
    }

}