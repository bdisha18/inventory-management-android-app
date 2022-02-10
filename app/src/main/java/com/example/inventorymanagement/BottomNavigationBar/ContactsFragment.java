package com.example.inventorymanagement.BottomNavigationBar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.inventorymanagement.Contacts.SupplierFragment;
import com.example.inventorymanagement.Contacts.VendorFragment;
import com.example.inventorymanagement.R;


public class ContactsFragment extends Fragment {

    TextView suppliersBtn, vendorBtn;
    Fragment supplier_fragment, vendor_fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        suppliersBtn = rootView.findViewById(R.id.supplier_fragment);
        vendorBtn = rootView.findViewById(R.id.vendor_fragment);

        getChildFragmentManager().beginTransaction().replace(R.id.contacts_fragment, new SupplierFragment()).commit();

        suppliersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplier_fragment = new SupplierFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.contacts_fragment, supplier_fragment).commit();
            }
        });

        vendorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendor_fragment = new VendorFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.contacts_fragment, vendor_fragment ).commit();
            }
        });

        return rootView;

    }
}