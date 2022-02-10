package com.example.inventorymanagement.BottomNavigationBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.Products.AddProducts;
import com.example.inventorymanagement.Products.ProductListAdapter;
import com.example.inventorymanagement.Products.ScannedBarcode;
import com.example.inventorymanagement.R;
import com.example.inventorymanagement.RandomString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ProductsFragment extends Fragment {

    String s_barcode;
    ImageView scan_barcodeBtn;
    private FloatingActionButton productBtn;
    private RecyclerView recyclerView;
    private ArrayList<Products> products;
    private ProductListAdapter productsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = rootView.findViewById(R.id.productListView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        products = new ArrayList<Products>();


//        txtProduct = rootView.findViewById(R.id.ad_product);
//        txtSize = rootView.findViewById(R.id.add_size);
//        txtColour = rootView.findViewById(R.id.add_colour);
//        txtCategory = rootView.findViewById(R.id.add_category);
//
//        addBtn = rootView.findViewById(R.id.floating_button);
        productBtn = rootView.findViewById(R.id.product_button);
//        sizeBtn = rootView.findViewById(R.id.size_button);
//        colourBtn = rootView.findViewById(R.id.colour_button);
//        categoryBtn = rootView.findViewById(R.id.category_button);

//        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.floating_button_open);
//        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.floating_button_close);
//
//
//        isOpen = false;
//
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isOpen) {
//                    productBtn.setAnimation(fabClose);
//                    sizeBtn.setAnimation(fabClose);
//                    colourBtn.setAnimation(fabClose);
//                    categoryBtn.setAnimation(fabClose);
//
//                    txtColour.setVisibility(View.INVISIBLE);
//                    txtSize.setVisibility(View.INVISIBLE);
//                    txtProduct.setVisibility(View.INVISIBLE);
//                    txtCategory.setVisibility(View.INVISIBLE);
//
//                    isOpen = false;
//
//                } else {
//                    productBtn.setAnimation(fabOpen);
//                    sizeBtn.setAnimation(fabOpen);
//                    colourBtn.setAnimation(fabOpen);
//                    categoryBtn.setAnimation(fabOpen);
//
//                    txtColour.setVisibility(View.VISIBLE);
//                    txtSize.setVisibility(View.VISIBLE);
//                    txtProduct.setVisibility(View.VISIBLE);
//                    txtCategory.setVisibility(View.VISIBLE);
//
//                    isOpen = true;
//                }
//            }
//        });

        productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateBarcode();
                Intent i = new Intent(getActivity(), AddProducts.class);
                i.putExtra("barcode", s_barcode);
                startActivity(i);
            }
        });

        scan_barcodeBtn = rootView.findViewById(R.id.barcode_scanner);
        scan_barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScannedBarcode.class);
                startActivityForResult(i, 0);
            }
        });



//        sizeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), SizeMain.class);
//                startActivity(i);
//            }
//        });
//
//        colourBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), ColourMain.class);
//                startActivity(i);
//            }
//        });
//
//        categoryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), CategoryMain.class);
//                startActivity(i);
//            }
//        });
        return rootView;
    }

    public void generateBarcode() {
        RandomString rand = new RandomString();
        s_barcode = rand.generateNumber(10);
    }

//    @Override
//    public void onStart() {
//
//        super.onStart();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                products.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    ProductModel product = postSnapshot.getValue(ProductModel.class);
//                    products.add(product);
//                }
//                productsList = new ProductsList(getContext(), products);
//                recyclerView.setAdapter(productsList);
//                productsList.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    }
