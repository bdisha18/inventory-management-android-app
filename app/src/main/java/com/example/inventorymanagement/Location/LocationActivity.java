package com.example.inventorymanagement.Location;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationActivity extends AppCompatActivity  {

    ImageView backBtn;
    TextView bottom_delete_link, bottom_edit_link;
    Button bottom_dialog_cancel_btn;
    private FloatingActionButton FloatingBtn;
    private RecyclerView recyclerView;
    private List<Locations> locations;
    private LocationListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        recyclerView = findViewById(R.id.locationListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(LocationActivity.this));

       locations = new ArrayList<Locations>();
        setTitle("Locations");


        retrieveLocation();

//        backBtn = findViewById(R.id.back_button);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });



        FloatingBtn = findViewById(R.id.floating_button);
        FloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCustomDialog();
            }
        });
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
                adapter = new LocationListAdapter(LocationActivity.this, locations);
                recyclerView.setAdapter(adapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LocationActivity.this);
        requestQueue.add(request);
    }

    private void bottomCustomDialog(final int position) {
        final AlertDialog.Builder d = new AlertDialog.Builder(LocationActivity.this);
        View mView =getLayoutInflater().inflate(R.layout.category_bottom_dialog_box, null);

        d.setView(mView);
        final Locations location = locations.get(position);

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
//                editDisplayCustomDialog(location.getId(), location.getName());
                dialog.dismiss();
            }

        });

        dialog.show();
    }


    // update category of dialog box
//    private void editDisplayCustomDialog(final String id, String locationName) {
//        final AlertDialog.Builder d = new AlertDialog.Builder(this);
//        View mView =getLayoutInflater().inflate(R.layout.location_dialog_box, null);
//        final EditText editLocation = mView.findViewById(R.id.locationEditText);
//        final Button cancel = mView.findViewById(R.id.cancelBtn);
//        final Button update = mView.findViewById(R.id.saveBtn);
//
//        editLocation.setText(locationName);
//
//        d.setView(mView);
//
//        final AlertDialog dialog = d.create();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//
//        // change width and height of dialog
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int displayWidth = displayMetrics.widthPixels;
//        int displayHeight = displayMetrics.heightPixels;
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        int dialogWindowWidth = (int) (displayWidth * 0.8f);
//        int dialogWindowHeight = (int) (displayHeight * 0.4f);
//        layoutParams.width = dialogWindowWidth;
//        layoutParams.height = dialogWindowHeight;
//        dialog.getWindow().setAttributes(layoutParams);
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Get Data
//                Toast.makeText(LocationActivity.this, "hello", Toast.LENGTH_SHORT).show();
//                String s_editlocation = editLocation.getText().toString();
//
//                if (TextUtils.isEmpty(s_editlocation)) {
//                    Toast.makeText(LocationActivity.this, "Field cannot be Empty", Toast.LENGTH_SHORT).show();
//                } else {
//                    Locations model = new Locations(id, s_editlocation );
////                    databaseReference.child(id).child("category").setValue(model.getCategory());
////                    databaseReference.child(id).child("type").setValue(model.getType());
//                    dialog.dismiss();
//                    Toast.makeText(LocationActivity.this, "Location Updated Successfully", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//
//        dialog.show();
//    }

    // Save Category Dialog Box

    private void displayCustomDialog() {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.location_dialog_box, null);
        EditText editLocation = mView.findViewById(R.id.locationEditText);
        Button cancel = mView.findViewById(R.id.cancelBtn);
        Button save = mView.findViewById(R.id.saveBtn);

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
                String location = editLocation.getText().toString().trim();

                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(LocationActivity.this, "Field cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    //save
                    dialog.dismiss();

                    String url = "http://10.0.2.2/inventoryApp/addLocation.php";
                    Toast.makeText(LocationActivity.this, "hello", Toast.LENGTH_SHORT).show();

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(LocationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LocationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("location_name",location);


                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(LocationActivity.this);
                    requestQueue.add(request);
                }
                editLocation.setText(null);

            }

        });

        dialog.show();
    }

//    @Override
//    public void onItemClick(int position) {
//        bottomCustomDialog(position);
//    }
}
