package com.example.inventorymanagement.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Suppliers;
import com.example.inventorymanagement.R;

import java.util.List;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.SuppliersHolder> {

    Context context;
    List<Suppliers> supplierList;

    public SupplierListAdapter(Context context, List<Suppliers> supplierList) {
        this.context = context;
        this.supplierList = supplierList;
    }


    @NonNull
    @Override
    public SuppliersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_supplier_list_adapter, parent, false);

        return new SupplierListAdapter.SuppliersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuppliersHolder holder, int position) {
        Suppliers suppliers = supplierList.get(position);
        holder.e_supplierId.setText(suppliers.getId());
        holder.e_supplierName.setText(suppliers.getName());

    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    public class SuppliersHolder extends RecyclerView.ViewHolder {
        TextView e_supplierId, e_supplierName;
        public SuppliersHolder(@NonNull View itemView) {
            super(itemView);
            e_supplierId = itemView.findViewById(R.id.supplier_id);
            e_supplierName = itemView.findViewById(R.id.supplier_name);
        }
    }
}