package com.example.inventorymanagement.Products;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.inventorymanagement.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannedBarcode extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);
        surfaceView = findViewById(R.id.surfaceView);
        createCameraSource();

    }

        private void createCameraSource() {

            Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

            barcodeDetector = new BarcodeDetector.Builder(this)
                    .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                    .build();

            final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                    .setRequestedPreviewSize(1920, 1080)
                    .setAutoFocusEnabled(true) //you should add this feature
                    .build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(ScannedBarcode.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            cameraSource.start(surfaceView.getHolder());
                        } else {
                            ActivityCompat.requestPermissions(ScannedBarcode.this, new
                                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });


            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {
                    Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
                }


                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    if (barcodes.size() != 0) {
                        Intent i = new Intent(ScannedBarcode.this, AddProduct.class);
                        i.putExtra("barcode", barcodes.valueAt(0).displayValue);
                        setResult(CommonStatusCodes.SUCCESS, i);
                        finish();
//
//                                if (barcodes.size() != 0) {
//                                    Log.d("My QR Code", barcodes.valueAt(0).displayValue);
//                                }
//
//                                intentData = txtBarcodeValue.getText().toString();
//                                txtBarcodeValue.setText(intentData);
//                                btnAction.setText("Add barcode Number");


//                                if (barcodes.valueAt(0).email != null) {
//                                    txtBarcodeValue.removeCallbacks(null);
//                                    intentData = barcodes.valueAt(0).email.address;
//                                    txtBarcodeValue.setText(intentData);
//                                    isEmail = true;
//                                    btnAction.setText("ADD CONTENT TO THE MAIL");
//                                } else {
//                                    isEmail = false;
//                                    btnAction.setText("LAUNCH URL");
//                                    intentData = barcodes.valueAt(0).displayValue;
//                                    txtBarcodeValue.setText(intentData);
//
//                                }
                    }
                }
            });
    }
}