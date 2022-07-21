package com.example.inventorymanagement.Products;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.inventorymanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryMain extends AppCompatActivity implements RecyclerViewClickInterface {

    ImageView backBtn;
    String url = "http://10.0.2.2/inventoryApp/categoryList.php";
    TextView bottom_delete_link, bottom_edit_link;
    Button bottom_dialog_cancel_btn;
    Spinner categoryType;
    String type;
    String[] s_type =  {"Select Input Type ", "Date", "Text", "Number"};
    private ImageView categoryBtn;
    private RecyclerView recyclerView;
    private List<Categories> categories;
    private CategoryListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);
        setTitle("Item Category");

        recyclerView = findViewById(R.id.locationListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryMain.this));

        categories = new ArrayList<Categories>();

        retrieveCategory();

//        backBtn = findViewById(R.id.back_button);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });



        categoryBtn = findViewById(R.id.category_floating_button);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCustomDialog();
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

//                        String s_id = object.getString("category_id").trim();
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
                adapter = new CategoryListAdapter(CategoryMain.this, categories);
                recyclerView.setAdapter(adapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategoryMain.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryMain.this);
        requestQueue.add(request);
    }

    private void bottomCustomDialog(final int position) {
        final AlertDialog.Builder d = new AlertDialog.Builder(CategoryMain.this);
        View mView =getLayoutInflater().inflate(R.layout.category_bottom_dialog_box, null);

        d.setView(mView);
        final Categories category = categories.get(position);

        final AlertDialog dialog = d.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // change width and height of dialog

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int displayWidth = displayMetrics.widthPixels;
//        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        dialog.getWindow().setLayout(layoutParams.MATCH_PARENT, layoutParams.WRAP_CONTENT);
//        int dialogWindowWidth = (int) (displayWidth * 0.95f);
//        int dialogWindowHeight = (int) (displayHeight * 0.5f);
//        layoutParams.width = dialogWindowWidth;
//        layoutParams.height = dialogWindowHeight;
//        dialog.getWindow().setAttributes(layoutParams);


        bottom_delete_link = mView.findViewById(R.id.delete);
        bottom_delete_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                databaseReference.child(category.getId()).removeValue();
                dialog.dismiss();
            }
        });

        bottom_dialog_cancel_btn = mView.findViewById(R.id.cancel_option);
        bottom_dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bottom_edit_link = mView.findViewById(R.id.edit);
        bottom_edit_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data
                editDisplayCustomDialog(category.getId(), category.getCategory(), category.getType() );
                dialog.dismiss();
            }

        });

        dialog.show();
    }


    // update category of dialog box
    private void editDisplayCustomDialog(final String id, String categoryName, String categorytype) {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.category_dialog_box, null);
        final EditText editCategory = mView.findViewById(R.id.categoryEditText);
        final Spinner categoryType = mView.findViewById(R.id.spinner_type);
        final Button cancel = mView.findViewById(R.id.cancelBtn);
        final Button update = mView.findViewById(R.id.saveBtn);

        editCategory.setText(categoryName);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s_type){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                }else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryType.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();

        int type_position = dataAdapter.getPosition(categorytype);
        categoryType.setSelection(type_position);

        categoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = categoryType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data
                Toast.makeText(CategoryMain.this, "hello", Toast.LENGTH_SHORT).show();
                String s_editcategory = editCategory.getText().toString();
                String s_categoryType = categoryType.getSelectedItem().toString();

                if (TextUtils.isEmpty(s_editcategory)) {
                    Toast.makeText(CategoryMain.this, "Field cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Categories model = new Categories(id, s_editcategory, s_categoryType );
//                    databaseReference.child(id).child("category").setValue(model.getCategory());
//                    databaseReference.child(id).child("type").setValue(model.getType());
                    dialog.dismiss();
                    Toast.makeText(CategoryMain.this, "Category Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }

        });

        dialog.show();
    }

    // Save Category Dialog Box

    private void displayCustomDialog() {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.category_dialog_box, null);
        final EditText editCategory = mView.findViewById(R.id.categoryEditText);
        categoryType = mView.findViewById(R.id.spinner_type);
        Button cancel = mView.findViewById(R.id.cancelBtn);
        Button save = mView.findViewById(R.id.saveBtn);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s_type) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                }else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryType.setAdapter(dataAdapter);



        categoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                type = categoryType.getSelectedItem().toString();
                type = (String) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        int dialogWindowHeight = (int) (displayHeight * 0.4f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data
                String category = editCategory.getText().toString().trim();

                if (TextUtils.isEmpty(category) || TextUtils.isEmpty(type)) {
                    Toast.makeText(CategoryMain.this, "Field cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    //save
                    dialog.dismiss();

                    String url = "http://10.0.2.2/inventoryApp/addCategory.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(CategoryMain.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CategoryMain.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("category_name", category);
                            params.put("category_type", type);


                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(CategoryMain.this);
                    requestQueue.add(request);
                }
                editCategory.setText(null);

            }

        });

        dialog.show();
    }

    @Override
    public void onItemClick(int position) {
        bottomCustomDialog(position);
    }

    
}