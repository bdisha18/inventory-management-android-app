package com.example.inventorymanagement.Products;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Categories;
import com.example.inventorymanagement.R;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoriesHolder> {

    private Activity context;
    private List<Categories> categories;


    public CategoryListAdapter(Activity context, List<Categories> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_list_adapter, parent, false);

        return new CategoryListAdapter.CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        Categories category = categories.get(position);
        holder.e_categoryName.setText(category.getCategory());
        holder.e_categoryType.setText(category.getType());

    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoriesHolder extends RecyclerView.ViewHolder {
        TextView e_categoryName, e_categoryType;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            e_categoryName = itemView.findViewById(R.id.category);
            e_categoryType = itemView.findViewById(R.id.category_type);
        }

   }
}