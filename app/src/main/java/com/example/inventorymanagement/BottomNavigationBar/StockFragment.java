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

import androidx.fragment.app.Fragment;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.Stock.AddStockActivity;

public class StockFragment extends Fragment {

    ImageView add_stock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock, container, false);

        add_stock = rootView.findViewById(R.id.add_stocks);
        add_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockInOutMoveDialog();
            }
        });
        return rootView;
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
                Intent i = new Intent(getContext(), AddStockActivity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });

        TextView txt_stockout = mView.findViewById(R.id.stock_out);
        txt_stockout.setOnClickListener(new View.OnClickListener() {
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
}