package com.example.inventorymanagement.Products;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.Models.ProductLocations;
import com.example.inventorymanagement.R;

import java.util.List;

public class ProductDetailLocationListAdapter extends RecyclerView.Adapter<ProductDetailLocationListAdapter.LocationHolder> {

    private Activity context;
    private List<Locations> locations;
    private List<ProductLocations> productLocations;
    private OnLocationListener listener;



    public ProductDetailLocationListAdapter(Activity context, List<Locations> locations, List<ProductLocations> productLocations, OnLocationListener listener) {
        this.context = context;
        this.locations = locations;
        this.productLocations = productLocations;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProductDetailLocationListAdapter.LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_detail_location_list_adapter, parent, false);

        return new ProductDetailLocationListAdapter.LocationHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailLocationListAdapter.LocationHolder holder, int position) {
        Locations location = locations.get(position);
        holder.e_location.setText(location.getName());
        ProductLocations productLocation = productLocations.get(position);
        holder.e_quantity.setText(productLocation.getQuantity());

    }


    @Override
    public int getItemCount() {
        return locations.size();
    }

    public interface OnLocationListener {
        void onLocationClick(View v, int position);
    }

    public class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView e_location, e_quantity;
        OnLocationListener onLocationListener;

        public LocationHolder(@NonNull View itemView, OnLocationListener onLocationListener) {
            super(itemView);
            e_location = itemView.findViewById(R.id.tv_location);
                e_quantity = itemView.findViewById(R.id.tv_quantity);

            this.onLocationListener = onLocationListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onLocationClick(view, getBindingAdapterPosition());
        }
    }
}