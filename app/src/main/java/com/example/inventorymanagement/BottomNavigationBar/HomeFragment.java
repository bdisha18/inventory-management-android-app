package com.example.inventorymanagement.BottomNavigationBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.inventorymanagement.Contacts.CustomerListAdapter;
import com.example.inventorymanagement.Contacts.VendorListAdapter;
import com.example.inventorymanagement.Models.Users;
import com.example.inventorymanagement.Products.ProductListAdapter;
import com.example.inventorymanagement.R;
import com.example.inventorymanagement.SettingsActivity;


public class HomeFragment extends Fragment {
TextView stocksCount, customersCount, vendorsCount, productsCount, date, userName;
VendorListAdapter vendor;
ImageView setting_imv;
CustomerListAdapter customer;
ProductListAdapter product;
String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        stocksCount = rootView.findViewById(R.id.total_stock);
        customersCount = rootView.findViewById(R.id.total_customers);
        vendorsCount = rootView.findViewById(R.id.total_vendors);
        productsCount = rootView.findViewById(R.id.total_products);
        date = rootView.findViewById(R.id.dateAdded);
        setting_imv = rootView.findViewById(R.id.setting);
        userName = rootView.findViewById(R.id.user_name);

        setting_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SettingsActivity.class);
                i.putExtra("email", userEmail);
                startActivity(i);
            }
        });

        if (getArguments() != null){
            userEmail = getArguments().getString("email");
        }
        Users user = new Users();
        if (userEmail != null){
            userName.setText(user.getName());
        }
//        String s_vendor = String.valueOf(vendor.getItemCount());
//        vendorsCount.setText(s_vendor);
//
//        String s_customer = String.valueOf(customer.getItemCount());
//        customersCount.setText(s_customer);
//
//        String s_product = String.valueOf(product.getItemCount());
//        productsCount.setText(s_product);

//        Date todayDate = Calendar.getInstance().getTime();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
//        String todayString = formatter.format(todayDate);
        return rootView;
    }
}