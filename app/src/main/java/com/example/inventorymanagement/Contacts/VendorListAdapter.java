package com.example.inventorymanagement.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Vendors;
import com.example.inventorymanagement.R;

import java.util.List;

public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.VendorsHolder> {
    Context context;
    List<Vendors> vendorsList;

    public VendorListAdapter(Context context, List<Vendors> vendorsList) {
        this.context = context;
        this.vendorsList = vendorsList;
    }

    @NonNull
    @Override
    public VendorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vendor_list_adapter, parent, false);

        return new VendorsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorsHolder holder, int position) {
        Vendors vendors = vendorsList.get(position);
        holder.e_vendorId.setText(vendors.getId());
        holder.e_vendorName.setText(vendors.getName());
    }

    @Override
    public int getItemCount() {
        return vendorsList.size();
    }

    public class VendorsHolder extends RecyclerView.ViewHolder{
        TextView e_vendorId, e_vendorName;
        public VendorsHolder(@NonNull View itemView) {
            super(itemView);
            e_vendorId = itemView.findViewById(R.id.vendor_id);
            e_vendorName = itemView.findViewById(R.id.vendor_name);
        }
    }
}