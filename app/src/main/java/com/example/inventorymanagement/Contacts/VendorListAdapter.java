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
    OnVendorListener listener;

    public VendorListAdapter(Context context, List<Vendors> vendorsList, OnVendorListener listener) {
        this.context = context;
        this.vendorsList = vendorsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VendorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vendor_list_adapter, parent, false);

        return new VendorsHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorsHolder holder, int position) {
        Vendors vendors = vendorsList.get(position);
        holder.e_vendorName.setText(vendors.getName());
    }

    public interface OnVendorListener {
        void onVendorClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return vendorsList.size();
    }

    public class VendorsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView e_vendorName;
        OnVendorListener onVendorListener;
        public VendorsHolder(@NonNull View itemView, OnVendorListener onVendorListener) {
            super(itemView);
            e_vendorName = itemView.findViewById(R.id.vendor_name);
            this.onVendorListener = onVendorListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            listener.onVendorClick(view, getBindingAdapterPosition());

        }
    }
}