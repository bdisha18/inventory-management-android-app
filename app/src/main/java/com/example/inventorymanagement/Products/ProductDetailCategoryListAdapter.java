package com.example.inventorymanagement.Products;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.ProductCategory;
import com.example.inventorymanagement.R;

import java.util.List;

public class ProductDetailCategoryListAdapter extends RecyclerView.Adapter<ProductDetailCategoryListAdapter.CategoriesHolder>{

    private Activity context;
    private List<ProductCategory> productCategories;



    public ProductDetailCategoryListAdapter(Activity context, List<ProductCategory> productCategories) {
        this.context = context;
        this.productCategories = productCategories;
    }


    @NonNull
    @Override
    public ProductDetailCategoryListAdapter.CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_detail_category_list_adapter, parent, false);

        return new ProductDetailCategoryListAdapter.CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailCategoryListAdapter.CategoriesHolder holder, int position) {
        ProductCategory productCategory = productCategories.get(position);
        holder.e_categoryValue.setText(productCategory.getCategory_value());

    }


    @Override
    public int getItemCount() {
        return productCategories.size();
    }

    public class CategoriesHolder extends RecyclerView.ViewHolder{
        TextView e_categoryValue;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            e_categoryValue = itemView.findViewById(R.id.category_value);

        }
    }
}