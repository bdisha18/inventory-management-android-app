package com.example.inventorymanagement.Products;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Categories;
import com.example.inventorymanagement.R;

import java.util.List;

public class ProductCategoryListAdapter extends RecyclerView.Adapter<ProductCategoryListAdapter.CategoriesHolder>{

    String s_value;
    private Activity context;
    private List<Categories> categories;


    public ProductCategoryListAdapter(Activity context, List<Categories> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_category_list_adapater, parent, false);
        return new ProductCategoryListAdapter.CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        Categories category = categories.get(position);
        holder.e_categoryName.setText(category.getCategory());
        s_value = holder.e_categoryValue.getText().toString().trim();

            if ((category.getType()).equals("number")) {
                holder.e_categoryValue.setHint("Input Number");
            } else if ((category.getType()).equals("text")) {
                holder.e_categoryValue.setHint("Input Text");
            } else {
                holder.e_categoryValue.setHint("Input Date");
            }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoriesHolder extends RecyclerView.ViewHolder {
        TextView e_categoryName;
        EditText e_categoryValue;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            e_categoryName = itemView.findViewById(R.id.product_category_text);
            e_categoryValue = itemView.findViewById(R.id.e_value);

        }

    }
}