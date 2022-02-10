package com.example.inventorymanagement.Products;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private Context activity;
    private ArrayList<Products> products;

    public ProductListAdapter(Context activity, ArrayList<Products> products) {
        this.activity = activity;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        Products model = products.get(position);
        holder.txtproductName.setText(model.getDesign());
        holder.txtbarcode.setText(model.getBarcode());
//        holder.txtQuantity.setText(model.getQuantity());
//        holder.image.setImageURI(products.get(position).getImage()).toString();
//        Picasso.get().load(model.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txtproductName, txtbarcode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.product_image);
            txtproductName = itemView.findViewById(R.id.product_design);
            txtbarcode = itemView.findViewById(R.id.product_barcode);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ProductDetails.class);
                    v.getContext().startActivity(i);

                }
            });

        }
    }
}