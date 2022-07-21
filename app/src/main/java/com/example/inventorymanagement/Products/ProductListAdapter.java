package com.example.inventorymanagement.Products;

import android.content.Context;
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
    private OnProductListener listener;
//    private int checkedPosition = 0;

    public ProductListAdapter(Context activity, ArrayList<Products> products, OnProductListener listener) {
        this.activity = activity;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_list_adapter, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        Products model = products.get(position);
        holder.txtproductName.setText(model.getDesign());
        holder.txtbarcode.setText(model.getBarcode());
        holder.txtquantity.setText(model.getQuantity());

//        holder.image.setImageURI(products.get(position)).toString();
//        Picasso.get().load(model.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnProductListener {
        void onProductClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image, checkImv;
        TextView txtproductName, txtbarcode, txtquantity;
        OnProductListener onProductListener;

        public ViewHolder(@NonNull View itemView, OnProductListener onProductListener) {
            super(itemView);

            image = itemView.findViewById(R.id.product_image);
            txtproductName = itemView.findViewById(R.id.product_design);
            txtbarcode = itemView.findViewById(R.id.product_barcode);
            txtquantity = itemView.findViewById(R.id.product_quantity);
//            checkImv = itemView.findViewById(R.id.checkImage);

            this.onProductListener = onProductListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            listener.onProductClick(view, getBindingAdapterPosition());
        }
    }
}