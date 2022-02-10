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
import com.example.inventorymanagement.Models.Vendors;
import com.example.inventorymanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VendorFragment extends Fragment {

    FloatingActionButton vendor_floating_btn;
    String url = "http://192.168.0.123/inventoryApp/vendorList.php";
    private RecyclerView recyclerView;
    private VendorListAdapter adapter;
    private List<Vendors> vendorsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vendor, container, false);

        recyclerView = rootView.findViewById(R.id.vendorsListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        vendorsList = new ArrayList<>();

        retrieveVendors();

        vendor_floating_btn = rootView.findViewById(R.id.vendor_floating_button);
        vendor_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddVendorActivity.class);
                startActivity(i);
            }
        });


//        vendorsList = rootView.findViewById(R.id.vendorsListView);
//        vendorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                VendorModel model = vendors.get(position);
////                Intent i = new Intent(getContext(), VendorDetail.class);
////                startActivity(i);
//            }
//        });



        return rootView;
    }

    public void retrieveVendors() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);

                        String s_id = object.getString("vendor_id").trim();
                        String s_name = object.getString("vendor_name").trim();
//                        String s_contactno = object.getString("vendor_contactno").trim();
//                        String s_email = object.getString("vendor_email").trim();
//                        String s_address = object.getString("vendor_address").trim();
//                        String s_note = object.getString("note").trim();

                        Vendors vendors = new Vendors();
                        vendors.setId(s_id);
                        vendors.setName(s_name);
                        vendorsList.add(vendors);

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
                adapter = new VendorListAdapter(getContext(), vendorsList);
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