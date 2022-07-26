package com.example.inventorymanagement;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.inventorymanagement.BottomNavigationBar.ContactsFragment;
import com.example.inventorymanagement.BottomNavigationBar.HomeFragment;
import com.example.inventorymanagement.BottomNavigationBar.ProductsFragment;
import com.example.inventorymanagement.BottomNavigationBar.StockFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String email = getIntent().getStringExtra("email");
        Bundle b = new Bundle();
         b.putString("email", email);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setItemIconTintList(null);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_fragment, fragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectorFragment = new HomeFragment();
                        break;
                    case R.id.navigation_products:
                        selectorFragment = new ProductsFragment();
                        break;
                    case R.id.navigation_stocks:
                        selectorFragment = new StockFragment();
                        break;
                    case R.id.navigation_contacts:
                        selectorFragment = new ContactsFragment();
                        break;
                }

                if (selectorFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_fragment, selectorFragment).commit();
                }

                return true;
            }
        });
    }
}