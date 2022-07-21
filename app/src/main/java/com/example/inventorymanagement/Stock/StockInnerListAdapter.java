package com.example.inventorymanagement.Stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Customers;
import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.Models.Stocks;
import com.example.inventorymanagement.Models.Vendors;
import com.example.inventorymanagement.R;

import java.util.List;

public class StockInnerListAdapter extends RecyclerView.Adapter<StockInnerListAdapter.StockHolder> {
    private Context context;
    private List<Stocks> stocks;
    private List<Products> products;
    private List<Customers> customers;
    private List<Vendors> vendors;
    private List<Locations> locations;
    private OnStockListener listener;

    public StockInnerListAdapter(Context context, List<Stocks> stocks, List<Locations> locations, List<Customers> customers,
                                 List<Vendors> vendors, List<Products> products, OnStockListener listener) {
        this.context = context;
        this.stocks = stocks;
        this.locations = locations;
        this.products = products;
        this.vendors = vendors;
        this.customers = customers;
        this.listener = listener;

    }


    @NonNull
    @Override
    public StockInnerListAdapter.StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stock_inner_list_adapter, parent, false);

        return new StockInnerListAdapter.StockHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull StockInnerListAdapter.StockHolder holder, int position) {
        Stocks stock = stocks.get(position);

        holder.e_stock_type.setText(stock.getStock_type());
        holder.e_quantity.setText(stock.getQuantity());
        holder.e_note.setText(stock.getNote());

        if (stock.getStock_type().equals("Stock In")){
            holder.imv.setImageResource(R.drawable.ic_baseline_vertical_align_bottom_24);
        }else if (stock.getStock_type().equals("Stock Out")){
            holder.imv.setImageResource(R.drawable.ic_baseline_vertical_align_top_24);
        }else if (stock.getStock_type().equals("Audit")){
            holder.imv.setImageResource(R.drawable.two_arrows);
        }else if (stock.getStock_type().equals("Move")){
            holder.imv.setImageResource(R.drawable.move);
        }

        Products product = products.get(position);
        holder.e_productName.setText(product.getDesign());

        Locations location = locations.get(position);
        holder.e_locationName.setText(location.getName());

        Customers customer = customers.get(position);
        holder.e_customerName.setText(customer.getName());

        Vendors vendor = vendors.get(position);
        holder.e_vendorName.setText(vendor.getName());

    }


    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public interface OnStockListener {
        void onStockClick(View v, int position);
    }

    public class StockHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView e_stock_type, e_locationName, e_productName, e_quantity, e_vendorName, e_customerName, e_note;
        ImageView imv;
        OnStockListener onStockListener;

        public StockHolder(@NonNull View itemView, OnStockListener onStockListener) {
            super(itemView);
            e_stock_type = itemView.findViewById(R.id.stock_type);
            e_locationName = itemView.findViewById(R.id.location);
            e_productName = itemView.findViewById(R.id.product);
            e_quantity = itemView.findViewById(R.id.quantity);
            e_vendorName = itemView.findViewById(R.id.client);
            e_customerName = itemView.findViewById(R.id.client);
            e_note = itemView.findViewById(R.id.note);
            imv = itemView.findViewById(R.id.imv);

            this.onStockListener = onStockListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onStockClick(view, getBindingAdapterPosition());
        }
    }
}