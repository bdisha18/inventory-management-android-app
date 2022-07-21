package com.example.inventorymanagement.Products;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Categories;
import com.example.inventorymanagement.Models.ProductCategory;
import com.example.inventorymanagement.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class ProductCategoryListAdapter extends RecyclerView.Adapter<ProductCategoryListAdapter.CategoriesHolder> implements DatePickerDialog.OnDateSetListener{

    String s_value;
    private Activity context;
    private List<Categories> categories;
    Categories category;
    ProductCategory products;
    String currentdateString;
    private List<ProductCategory> productCategory;
    private int day,month,year;


    public ProductCategoryListAdapter(Activity context, List<Categories> categories, List<ProductCategory> productCategory) {
        this.context = context;
        this.categories = categories;
        this.productCategory = productCategory;
    }


    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_category_list_adapater, parent, false);
        return new ProductCategoryListAdapter.CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        category = categories.get(position);
//        products = productCategory.get(position);
        holder.e_categoryName.setText(category.getCategory());
//        holder.e_categoryValue.setText(products.getCategory_value());
//        Log.d("dsd", productCategory.get(position).getCategory_value());
//        String id = productCategory.get(position).getCategory_id();

//        holder.e_categoryValue.setText(productCategory.get(position).getCategory_value());
        holder.e_categoryValue.setText("");
        holder.e_categoryValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productCategory.get(holder.getAbsoluteAdapterPosition()).setCategory_value(holder.e_categoryValue.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });





//        s_value = holder.e_categoryValue.getText().toString().trim();

            if ((category.getType()).equals("number")) {
                holder.e_categoryValue.setHint("Input Number");
            } else if ((category.getType()).equals("text")) {
                holder.e_categoryValue.setHint("Input Text");
            } else {
                holder.e_categoryValue.setHint("Input Date");
                holder.e_categoryValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("hello", "hverh");
                        DialogFragment datePicker = new com.example.inventorymanagement.DatePicker();
                        datePicker.show(datePicker.getParentFragmentManager(), "date picker");
                    }
                });

            }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        currentdateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    }

    public class CategoriesHolder extends RecyclerView.ViewHolder {
        TextView e_categoryName;
        EditText e_categoryValue;
        Button saveBtn;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            e_categoryName = itemView.findViewById(R.id.product_category_text);
            e_categoryValue = itemView.findViewById(R.id.e_value);
            saveBtn = itemView.findViewById(R.id.add_product_btn);

            e_categoryValue.setText(currentdateString);





        }

    }
}