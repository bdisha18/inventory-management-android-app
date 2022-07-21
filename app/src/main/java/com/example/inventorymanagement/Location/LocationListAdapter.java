package com.example.inventorymanagement.Location;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.Models.Locations;
import com.example.inventorymanagement.R;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationsHolder>{
    private Activity context;
    private List<Locations> locations;
//    RecyclerViewClickInterface recyclerViewClickInterface;

    public LocationListAdapter(Activity context, List<Locations> locations) {
        this.context = context;
        this.locations = locations;
//        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }


    @NonNull
    @Override
    public LocationListAdapter.LocationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_location_list_adapter, parent, false);

        return new LocationListAdapter.LocationsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsHolder holder, int position) {
        Locations location = locations.get(position);
        holder.e_locationName.setText(location.getName());

    }


    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class LocationsHolder extends RecyclerView.ViewHolder{
        TextView e_locationName;

        public LocationsHolder(@NonNull View itemView) {
            super(itemView);
            e_locationName = itemView.findViewById(R.id.location);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    recyclerViewClickInterface.onItemClick(getAbsoluteAdapterPosition());
//
//                }
//            });

        }
    }
}
