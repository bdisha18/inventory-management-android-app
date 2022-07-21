package com.example.inventorymanagement.Stock;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Customers;
import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.Models.ParentStocks;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.Models.Stocks;
import com.example.inventorymanagement.Models.Vendors;
import com.example.inventorymanagement.R;

import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.StockHolder> {
    StockInnerListAdapter adapter;
    private Context context;
    private List<Stocks> stocks;
    private List<ParentStocks> parentStocks;
    private List<Locations> locations;
    private List<Customers> customers;
    private List<Vendors> vendors;
    private List<Products> products;
//    StockInnerListAdapter.OnStockListener listener;

    public StockListAdapter(Context context, List<Stocks> stocks, List<ParentStocks> parentStocks) {
        this.context = context;
        this.stocks = stocks;
//        this.locations = locations;
//        this.products = products;
//        this.vendors = vendors;
//        this.customers = customers;
        this.parentStocks = parentStocks;
    }


    @NonNull
    @Override
    public StockListAdapter.StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stock_list_adapter, parent, false);

        return new StockListAdapter.StockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockListAdapter.StockHolder holder, int position) {
        ParentStocks parentStock = parentStocks.get(position);
        holder.e_date.setText(parentStock.getDate());
        Log.d("svaf", parentStock.getDate());

//        StockInnerListAdapter childAdapter =new StockInnerListAdapter(context, parentStocks.get(position).getChildModelStocks(), locations, customers, vendors, products, listener);
//        Log.d("vwerv", String.valueOf(childAdapter));
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        holder.recyclerView.setLayoutManager(layoutManager);
//        holder.recyclerView.setHasFixedSize(true);
//        holder.recyclerView.setAdapter(childAdapter);
//        childAdapter.notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return parentStocks.size();
    }

    public class StockHolder extends RecyclerView.ViewHolder{
        TextView e_date;
        RecyclerView recyclerView;

        public StockHolder(@NonNull View itemView) {
            super(itemView);
            e_date = itemView.findViewById(R.id.tv_date);
            recyclerView = itemView.findViewById(R.id.stock_inner_list);

        }
    }

}