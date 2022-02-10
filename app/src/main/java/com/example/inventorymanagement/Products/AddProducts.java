package com.example.inventorymanagement.Products;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagement.R;

public class AddProducts extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button continueBtn;
    EditText productName;
    TextView txtbarcode;
    ImageView imv;
    Uri imageUri;
    String s_image, s_barcode;

    ImageView scan_barcodeBtn, edit_barcodeBtn;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

//        size_spinner = findViewById(R.id.spinner_size);

        productName = findViewById(R.id.add_design);
        txtbarcode = findViewById(R.id.barcode);
        s_barcode = getIntent().getStringExtra("barcode");
        if(s_barcode != null){
            txtbarcode.setText(s_barcode);
        }

        imv =findViewById(R.id.upload_image);

        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openToChooseImage();
            }
        });


        // Spinner List
//        sizeList = new ArrayList<String>();
//        colourList = new ArrayList<String>();
//        categoryList = new ArrayList<String>();

        // SpinnerData();
//        db_product = FirebaseDatabase.getInstance().getReference("Products");
//        databaseReference = FirebaseDatabase.getInstance().getReference();

//        databaseReference.child("Size").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String s_size = ds.child("size").getValue(String.class);
//                    sizeList.add(s_size);
//                }
//                // Adapter
//                adapter = new ArrayAdapter<String>(AddProducts.this, android.R.layout.simple_spinner_item, sizeList);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                size_spinner.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        databaseReference.child("Colours").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String s_colour = ds.child("colour").getValue(String.class);
//                    colourList.add(s_colour);
//                }
//                adapter = new ArrayAdapter<String>(AddProducts.this, android.R.layout.simple_spinner_item, colourList);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                colour_spinner.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        databaseReference.child("Categories").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String s_category = ds.child("category").getValue(String.class);
//                    categoryList.add(s_category);
//                }
//                adapter = new ArrayAdapter<String>(AddProducts.this, android.R.layout.simple_spinner_item, categoryList);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                category_spinner.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        edit_barcodeBtn = findViewById(R.id.edit_barcode);
        edit_barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayCustomDialog();
            }
        });

        scan_barcodeBtn = findViewById(R.id.scan_barcode);
        scan_barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddProducts.this, ScannedBarcode.class);
                startActivityForResult(i, 0);
            }
        });




        continueBtn = findViewById(R.id.add_product_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

                String s_design = productName.getText().toString();
                if (TextUtils.isEmpty(s_design)) {
                    Toast.makeText(AddProducts.this, "Product Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(AddProducts.this, ProductCategoryActivity.class);
                    i.putExtra("product_name", s_design);
                    i.putExtra("barcode", s_barcode);
                    i.putExtra("image", s_image);
                    startActivity(i);
                }
            }
        });
    }



    private void displayCustomDialog() {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.barcode_dialog_box, null);

        final EditText edit_barcodenumber = mView.findViewById(R.id.edit_barcode_number);
        Button saveBtn = mView.findViewById(R.id.saveBtn);
        Button cancelBtn = mView.findViewById(R.id.cancelBtn);

        d.setView(mView);

        final AlertDialog dialog = d.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // change width and height of dialog

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.8f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        edit_barcodenumber.setText(s_barcode);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data
                txtbarcode.setText("");
                result= edit_barcodenumber.getText().toString();

                if (TextUtils.isEmpty(s_barcode)) {
                    Toast.makeText(AddProducts.this, "Barcode Number cannot be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    //save
                    dialog.dismiss();

                    txtbarcode.setText(result);
                    Toast.makeText(AddProducts.this, "Barcode updated Successfully", Toast.LENGTH_SHORT).show();
                }
                edit_barcodenumber.setText(null);
            }

        });

        dialog.show();
    }


    private void openToChooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage() {
        if (imageUri != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

//            final StorageReference sRef = storageReference.child(System.currentTimeMillis()
//                    + "." + getFileExtension(imageUri));
//
//
//            sRef.putFile(imageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    s_image = uri.toString();
//                                    Toast.makeText(AddProducts.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(AddProducts.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
//                        }
//                    });
//        }else {
//            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null) {
//            imageUri = data.getData();
////            imv.setImageURI(imageUri);
//            Picasso.get().load(imageUri).into(imv);
//
//            if (requestCode == 0){
//                if (resultCode == CommonStatusCodes.SUCCESS) {
//                    if (data != null) {
//                        Barcode barcode = data.getParcelableExtra("barcode");
//                        txtbarcode.setText(barcode.displayValue);
//                    } else {
//                        Toast.makeText(this, "No Barcode Detected", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }else {
//                super.onActivityResult(requestCode, resultCode, data);
//            }

        }
    }
}