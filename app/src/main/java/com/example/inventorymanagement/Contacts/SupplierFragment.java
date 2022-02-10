package com.example.inventorymanagement.Contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventorymanagement.Models.Suppliers;
import com.example.inventorymanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SupplierFragment extends Fragment {

    FloatingActionButton supplierFloatinBtn;
    RecyclerView recyclerView;
    String url = "http://192.168.0.123/inventoryApp/supplierList.php";
    private SupplierListAdapter adapter;
    private List<Suppliers> suppliersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_supplier, container, false);

        recyclerView = rootView.findViewById(R.id.suppliersListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        suppliersList = new ArrayList<>();

        retrieveVendors();
//        suppliersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//////                SupplierModel model = suppliers.get(position);
////                Intent i = new Intent(getContext(), SupplierDetail.class);
////                startActivity(i);
//            }
//        });

        supplierFloatinBtn = rootView.findViewById(R.id.supplier_floating_button);
        supplierFloatinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddSupplierActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    private void retrieveVendors() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_id = object.getString("supplier_id").trim();
                        String s_name = object.getString("supplier_name").trim();

                        Suppliers suppliers = new Suppliers();
                        suppliers.setId(s_id);
                        suppliers.setName(s_name);
                        suppliersList.add(suppliers);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                adapter = new SupplierListAdapter(getContext(), suppliersList);
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