package com.example.inventorymanagement.Contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.inventorymanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CustomerFragment extends Fragment {

    FloatingActionButton customerFloatinBtn;
    RecyclerView recyclerView;
    TextView customer_count, tv_date;
    String url = "http://10.0.2.2/inventoryApp/customerList.php";
    private CustomerListAdapter adapter;
    private List<Customers> customersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);

//        customer_count = rootView.findViewById(R.id.total_customers);
//        customer_count.setText(String.valueOf(customersList.size()));

//        tv_date = rootView.findViewById(R.id.dateAdded);


        recyclerView = rootView.findViewById(R.id.customersListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        customersList = new ArrayList<>();

        retrieveCustomers();
//        retrieveUpdatedDate();
//        suppliersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//////                SupplierModel model = suppliers.get(position);
////                Intent i = new Intent(getContext(), SupplierDetail.class);
////                startActivity(i);
//            }
//        });

        customerFloatinBtn = rootView.findViewById(R.id.customer_floating_button);
        customerFloatinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddCustomerActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

//    private void retrieveUpdatedDate() {
//        String url = "http://10.0.2.2/inventoryApp/customerUpdatedDate.php";
//        Customers customers = new Customers();
//        String s_updated_date = customers.getUpdated_at().toString().trim();
//        tv_date.setText(s_updated_date);
//
//    }

    private void retrieveCustomers() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_id = object.getString("customer_id").trim();
                        String s_name = object.getString("customer_name").trim();

                        Customers customers = new Customers();
                        customers.setId(s_id);
                        customers.setName(s_name);
                        customersList.add(customers);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                adapter = new CustomerListAdapter(getContext(), customersList);
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

}