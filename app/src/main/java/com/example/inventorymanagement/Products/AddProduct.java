package com.example.inventorymanagement.Products;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.Models.Categories;
import com.example.inventorymanagement.Models.ProductCategory;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.R;
import com.example.inventorymanagement.RandomString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProduct extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 0;
    private static final int PICK_IMAGE_REQUEST = 1;

    TextView saveBtn;
    ImageView categoryBtn, backBtn;
    //    EditText categoryTxt;
    EditText valueText;
    TextView txtValue;
    ProductCategoryListAdapter adapter;
    String s_product, s_barcode, s_image, s_sale, s_purchase, quantity;
    EditText product_name, purchase_price, sale_price, txt_barcode;
//    Boolean isOpen = false;
//    RelativeLayout categoryList;

    String url = "http://10.0.2.2/inventoryApp/categoryList.php";
    private RecyclerView recyclerView;
    private List<Categories> categories;
    private List<ProductCategory> values;
    ProductCategory procat;
    ImageView imv;
    private int year, month, day;
    Uri imageUri;
    ImageView scan_barcodeBtn, edit_barcodeBtn;
    String result;
    private List<Products> products;
    private int number = 0, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        setTitle("New Item");

//        txtValue = findViewById(R.id.txt_value);
        purchase_price = findViewById(R.id.cost_price);
        sale_price = findViewById(R.id.sale_price);
        product_name = findViewById(R.id.name);
        txt_barcode = findViewById(R.id.barcode);
//        s_barcode = getIntent().getExtras().getString("barcode");
//        if (s_barcode != null) {
        generateBarcode();
            txt_barcode.setText(s_barcode);
//        }

        imv = findViewById(R.id.upload_image);

        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openToChooseImage();
            }
        });


        edit_barcodeBtn = findViewById(R.id.edit_barcode);
        edit_barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayCustomDialog();
            }
        });

        scan_barcodeBtn = findViewById(R.id.scan_barcode);
        scan_barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddProduct.this, ScannedBarcode.class);
                startActivityForResult(i, 0);
            }
        });


        recyclerView = findViewById(R.id.categoryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddProduct.this));

        retrieveCategory();
        categories = new ArrayList<Categories>();
        categoryBtn = findViewById(R.id.add_category_btn);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddProduct.this, CategoryMain.class);
                startActivity(i);
            }
        });
        saveBtn = findViewById(R.id.add_product_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_sale = sale_price.getText().toString();
                s_purchase = purchase_price.getText().toString();
                s_product = product_name.getText().toString();
                s_image = imv.toString();


                Bundle b = new Bundle();
                b.putString("sale_price", s_sale);
                b.putString("purchase_price", s_purchase);
                b.putString("product_name", s_product);
                b.putString("image", s_image);
                    saveProduct(b);
//                QuantityDialogBox(b);


            }
        });

        values = new ArrayList<ProductCategory>();
        valueText = findViewById(R.id.e_value);




    }

    private void saveProduct(Bundle b) {
        generateBarcode();
        String productName = b.getString("product_name");
        String purchasePrice = b.getString("purchase_price");
        String salePrice = b.getString("sale_price");
        String image = b.getString("image");

                if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(s_barcode)) {
                    Toast.makeText(AddProduct.this, "Filds cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    //save
                    Categories category = new Categories();
//                    String key = category.getCategory();
//                    String s_value = valueText.getText().toString();

//                    String products_id = db_product.push().getKey();
//                    Products pro = new Products(products_id, s_product, s_image, s_barcode, quantity, s_sale, s_purchase);
//                    db_product.child(products_id).setValue(pro);
//                    db_product.child(products_id).child(key).setValue(s_value);
                    //save

                    String url1 = "http://10.0.2.2/inventoryApp/addProducts.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent i = new Intent(AddProduct.this, ProductDetails.class);
                            i.putExtra("product_name", productName);
                            i.putExtra("purchase_price", purchasePrice);
                            i.putExtra("sale_price", salePrice);
                            i.putExtra("barcode", s_barcode);
                            i.putExtra("image", "eferf");
                            startActivity(i);
                            Toast.makeText(AddProduct.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddProduct.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("product_name", productName);
                            params.put("product_image", "fgdg");
                            params.put("product_code", "24244");
                            params.put("purchase_price", purchasePrice);
                            params.put("sale_price", salePrice);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(AddProduct.this);
                    requestQueue.add(request);
                }
                product_name.setText(null);
                sale_price.setText(null);
                purchase_price.setText(null);

            }


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




//        backBtn = findViewById(R.id.back_button);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


    private void displayCustomDialog() {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.barcode_dialog_box, null);

        EditText edit_barcode = mView.findViewById(R.id.edit_barcode);
        Button saveBtn = mView.findViewById(R.id.saveBtn);
        Button cancelBtn = mView.findViewById(R.id.cancelBtn);
        edit_barcode.setText(s_barcode);

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
        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        int dialogWindowHeight = (int) (displayHeight * 0.4f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

//        edit_barcode.setText(s_barcode);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data
//                txt_barcode.setText("");
                result= edit_barcode.getText().toString();

                if (TextUtils.isEmpty(s_barcode)) {
                    Toast.makeText(AddProduct.this, "Barcode Number cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    //save
                    dialog.dismiss();

                    txt_barcode.setText(result);
                    Toast.makeText(AddProduct.this, "Barcode updated Successfully", Toast.LENGTH_SHORT).show();
                }
                edit_barcode.setText(null);
            }

        });

        dialog.show();
    }


    private void openToChooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage() {
        if (imageUri != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
        }
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

                        category.setCategory(s_name);
                        category.setType(s_type);
                        categories.add(category);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                adapter = new ProductCategoryListAdapter(AddProduct.this, categories, values);
                recyclerView.setAdapter(adapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddProduct.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(AddProduct.this);
        requestQueue.add(request);
    }

    public void generateBarcode() {
        RandomString rand = new RandomString();
        s_barcode = rand.generateNumber(10);
    }



}