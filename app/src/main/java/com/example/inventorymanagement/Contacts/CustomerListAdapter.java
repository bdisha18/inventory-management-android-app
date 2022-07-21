package com.example.inventorymanagement.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Customers;
import com.example.inventorymanagement.R;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomersHolder> {

    Context context;
    List<Customers> customerList;
    OnCustomerListener listener;

    public CustomerListAdapter(Context context, List<Customers> customerList, OnCustomerListener listener) {
        this.context = context;
        this.customerList = customerList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CustomersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_customer_list_adapter, parent, false);

        return new CustomerListAdapter.CustomersHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomersHolder holder, int position) {
        Customers customers = customerList.get(position);
        holder.e_customerName.setText(customers.getName());

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public interface OnCustomerListener {
        void onCustomerClick(View v, int position);
    }

    public class CustomersHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView e_customerName;
        OnCustomerListener onCustomerListener;
        public CustomersHolder(@NonNull View itemView, OnCustomerListener onCustomerListener) {
            super(itemView);
            e_customerName = itemView.findViewById(R.id.customer_name);


            this.onCustomerListener =onCustomerListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            listener.onCustomerClick(view, getBindingAdapterPosition());
        }
    }
}