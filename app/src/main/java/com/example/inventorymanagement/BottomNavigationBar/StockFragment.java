package com.example.inventorymanagement.BottomNavigationBar;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.Models.Customers;
import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.Models.ParentStocks;
import com.example.inventorymanagement.Models.Products;
import com.example.inventorymanagement.Models.Stocks;
import com.example.inventorymanagement.Models.Vendors;
import com.example.inventorymanagement.Products.ProductDetails;
import com.example.inventorymanagement.R;
import com.example.inventorymanagement.Stock.AddStockActivity;
import com.example.inventorymanagement.Stock.StockIn;
import com.example.inventorymanagement.Stock.StockInnerListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StockFragment extends Fragment {

    ImageView add_stock;
    String stockUrl = "http://10.0.2.2/inventoryApp/stockList.php";
    private RecyclerView recyclerView;
    private ArrayList<Stocks> stocks;
    private ArrayList<ParentStocks> parentStocks;
    private ArrayList<Locations> locations;
    private ArrayList<Customers> customers;
    private ArrayList<Vendors> vendors;
    private ArrayList<Products> products;

    private StockInnerListAdapter adapter;

    private StockInnerListAdapter.OnStockListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock, container, false);
       setOnClickListener();
        recyclerView = rootView.findViewById(R.id.stock_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        stocks = new ArrayList<Stocks>();
        parentStocks = new ArrayList<ParentStocks>();
        locations = new ArrayList<Locations>();
        customers = new ArrayList<Customers>();
        vendors = new ArrayList<Vendors>();
        products = new ArrayList<Products>();

        retrieveStocks();

        add_stock = rootView.findViewById(R.id.add_stocks);
        add_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockInOutMoveDialog();
            }
        });
        return rootView;
    }



    private void retrieveStocks() {
        JsonArrayRequest request = new JsonArrayRequest(stockUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_location_name = object.getString("location_name").trim();
                        String s_product_name = object.getString("product_name").trim();
                        String s_stockType= object.getString("stock_type").trim();
                        String s_quantity = object.getString("quantity").trim();
                        String s_clientType = object.getString("client_type").trim();
                        String s_vendorName = object.getString("vendor_name").trim();
                        String s_customerName = object.getString("customer_name").trim();
                        String s_time= object.getString("time").trim();
                        String s_date = object.getString("date").trim();
                        String s_note = object.getString("note").trim();

                        Stocks stock = new Stocks();
                        stock.setQuantity(s_quantity);
                        stock.setClient_type(s_clientType);
                        stock.setStock_type(s_stockType);
                        stock.setNote(s_note);
                        stock.setDate(s_date);
                        stock.setTime(s_time);

                        Locations location = new Locations();
                        location.setName(s_location_name);

                        Products product = new Products();
                        product.setDesign(s_product_name);

                        Vendors vendor = new Vendors();
                        vendor.setName(s_vendorName);

                        Customers customer = new Customers();
                        customer.setName(s_customerName);


                        stocks.add(stock);
                        locations.add(location);
                        products.add(product);
                        customers.add(customer);
                        vendors.add(vendor);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
               adapter = new StockInnerListAdapter(getContext(), stocks, locations, customers, vendors, products, listener);
                recyclerView.setAdapter(adapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }


    private void StockInOutMoveDialog() {
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        View mView =getLayoutInflater().inflate(R.layout.stockinout_dialog_box, null);

        d.setView(mView);

        final AlertDialog dialog = d.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // change width and height of dialog

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        dialog.getWindow().setLayout(layoutParams.MATCH_PARENT, layoutParams.WRAP_CONTENT);

        TextView txt_stockin = mView.findViewById(R.id.stock_in);
        txt_stockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tv_stockIn = txt_stockin.getText().toString();
                Intent i = new Intent(getContext(), StockIn.class);
                i.putExtra("stock_type", tv_stockIn);
                startActivity(i);
                dialog.dismiss();
            }
        });

        TextView txt_stockout = mView.findViewById(R.id.stock_out);
        txt_stockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tv_stockIn = txt_stockout.getText().toString();
                Intent i = new Intent(getContext(), StockIn.class);
                i.putExtra("stock_type", tv_stockIn);
                startActivity(i);
                dialog.dismiss();
            }
        });

//        TextView txt_stockmove = mView.findViewById(R.id.stock_move);
//        txt_stockmove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), AddStockActivity.class);
//                startActivity(i);
//                dialog.dismiss();
//            }
//        });

        TextView txt_audit = mView.findViewById(R.id.audit);
        txt_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddStockActivity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });

        ImageView cancelBtn = mView.findViewById(R.id.cancel_option);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setOnClickListener() {

        listener = new StockInnerListAdapter.OnStockListener() {
            @Override
            public void onStockClick(View v, int position) {
               Intent i = new Intent(getContext(), ProductDetails.class);
               startActivity(i);

            }
        };
    }

}