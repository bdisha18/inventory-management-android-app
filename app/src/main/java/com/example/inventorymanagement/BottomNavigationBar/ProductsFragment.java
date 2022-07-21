package com.example.inventorymanagement.BottomNavigationBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.Products.AddProduct;
import com.example.inventorymanagement.Products.ProductDetails;
import com.example.inventorymanagement.Products.ProductListAdapter;
import com.example.inventorymanagement.Products.ScannedBarcode;
import com.example.inventorymanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProductsFragment extends Fragment  {

    String s_barcode;
    ImageView scan_barcodeBtn;
    String url = "http://10.0.2.2/inventoryApp/productList.php";
    private ImageView productBtn;
    private RecyclerView recyclerView;
    private ArrayList<Products> products;
    private ProductListAdapter adapter;
    private ProductListAdapter.OnProductListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView = rootView.findViewById(R.id.productListView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        setOnClickListener();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        products = new ArrayList<Products>();
        retrieveProducts();

//        txtProduct = rootView.findViewById(R.id.ad_product);
//
//
//        addBtn = rootView.findViewById(R.id.floating_button);
        productBtn = rootView.findViewById(R.id.product_button);
//

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
//                generateBarcode();
                Intent i = new Intent(getActivity(), AddProduct.class);
//                i.putExtra("barcode", s_barcode);
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
        return rootView;
    }

    private void retrieveProducts() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        String s_id = object.getString("product_id").trim();
                        String s_name = object.getString("product_name").trim();
                        String s_image = object.getString("product_image").trim();
                        String s_barcode = object.getString("product_code").trim();
                        String s_purchase_price = object.getString("purchase_price").trim();
                        String s_sale_price = object.getString("sale_price").trim();
                        String s_quantity = object.getString("quantity").trim();

                        Products product = new Products();

                        product.setId(s_id);
                        product.setDesign(s_name);
                        product.setImage(s_image);
                        product.setBarcode(s_barcode);
                        product.setPurchasePrice(s_purchase_price);
                        product.setSalePrice(s_sale_price);
                        product.setQuantity(s_quantity);
                        products.add(product);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                adapter = new ProductListAdapter(getContext(), products, listener);
                recyclerView.setAdapter(adapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    private void setOnClickListener() {
        listener = new ProductListAdapter.OnProductListener() {
            @Override
            public void onProductClick(View v, int position) {

                Intent i = new Intent(getContext(), ProductDetails.class);
                i.putExtra("product_id", products.get(position).getId());
                i.putExtra("product_name", products.get(position).getDesign());
                i.putExtra("sale_price", products.get(position).getSalePrice());
                i.putExtra("purchase_price", products.get(position).getPurchasePrice());
                i.putExtra("barcode", products.get(position).getBarcode());
                i.putExtra("image", products.get(position).getImage());
                startActivity(i);

            }
        };
    }
}
