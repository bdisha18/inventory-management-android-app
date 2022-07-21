package com.example.inventorymanagement;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Customers;
import com.example.inventorymanagement.Models.Vendors;

import java.util.List;

public class SelectedItemListAdapter extends RecyclerView.Adapter<SelectedItemListAdapter.SelectedItemHolder> {


    private Activity context;
    private List<Vendors> vendors;
    private List<Customers> customers;

    public SelectedItemListAdapter(Activity context, List<Vendors> vendors, List<Customers> customers) {
        this.context = context;
        this.customers = customers;
        this.vendors = vendors;
    }


    @NonNull
    @Override
    public SelectedItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_selected_item_list_adapter, parent, false);

        return new SelectedItemListAdapter.SelectedItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemListAdapter.SelectedItemHolder holder, int position) {
        Vendors vendor = vendors.get(position);
        holder.e_vendorName.setText(vendor.getName());
        Customers customer = customers.get(position);
        holder.e_customerName.setText(customer.getName());

    }


    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class SelectedItemHolder extends RecyclerView.ViewHolder{
        TextView e_vendorName, e_customerName;

        public SelectedItemHolder(@NonNull View itemView) {
            super(itemView);
            e_vendorName = itemView.findViewById(R.id.textView);
            e_customerName = itemView.findViewById(R.id.textView);

        }
    }
}