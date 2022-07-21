package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.inventorymanagement.Contacts.CustomerFragment;
import com.example.inventorymanagement.Contacts.VendorFragment;
import com.example.inventorymanagement.Location.LocationActivity;
import com.example.inventorymanagement.Models.Categories;
import com.example.inventorymanagement.Products.CategoryMain;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ImageView profileImg;
    RelativeLayout customer_rl, location_rl, vendor_rl, category_rl;
    TextView user_name,category_count, location_count, vendor_count, customer_count;
    ArrayList<Categories> categoriesList;
    LinearLayout layout;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userEmail = getIntent().getStringExtra("email");


        user_name = findViewById(R.id.user_name);
        category_count = findViewById(R.id.category_count);
        location_count = findViewById(R.id.location_count);
        vendor_count = findViewById(R.id.vendors_count);
        customer_count = findViewById(R.id.customers_count);

        profileImg = findViewById(R.id.imv);

        customer_rl = findViewById(R.id.customer_layout);
        location_rl = findViewById(R.id.location_layout);
        vendor_rl = findViewById(R.id.vendor_layout);
        category_rl = findViewById(R.id.category_layout);

        layout = findViewById(R.id.frameLayout);
        categoriesList = new ArrayList<Categories>();


//        user_name.setText();

        customer_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, CustomerFragment.class);
                startActivity(i);
            }
        });

        location_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, LocationActivity.class);
                startActivity(i);
            }
        });

        vendor_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.GONE);
                Fragment fragment = new VendorFragment();
                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment).commit();

            }
        });

        category_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, CategoryMain.class);
                startActivity(i);
            }
        });

    }
}