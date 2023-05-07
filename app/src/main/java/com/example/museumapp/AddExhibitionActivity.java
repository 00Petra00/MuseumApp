package com.example.museumapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AddExhibitionActivity extends AppCompatActivity {

    EditText mTitleEt, mImageEt, mDateEt, mDescriptionEt, mPrice;
    Button mSaveBtn, mCancelBtn;
    ProgressDialog pd;
    FirebaseFirestore db;

    String pId, pTitle, pImage, pDate, pDescription, pPrice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibition);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Exhibition");
        mTitleEt = findViewById(R.id.titleEt);
        mImageEt = findViewById(R.id.imageEt);
        mDateEt = findViewById(R.id.dateEt);
        mDescriptionEt = findViewById(R.id.descriptionEt);
        mPrice = findViewById(R.id.priceEt);
        mSaveBtn = findViewById(R.id.saveBtn);
        mCancelBtn = findViewById(R.id.cancelBtn);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            actionBar.setTitle("Update Data");
            mSaveBtn.setText("Update");
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pImage = bundle.getString("pImage");
            pDate = bundle.getString("pDate");
            pDescription = bundle.getString("pDescription");
            pPrice = bundle.getString("pPrice");

            mTitleEt.setText(pTitle);
            mImageEt.setText(pImage);
            mDateEt.setText(pDate);
            mDescriptionEt.setText(pDescription);
            mPrice.setText(pPrice);

        } else {
            actionBar.setTitle("Add Exhibition");
            mSaveBtn.setText("Save");
        }

        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id = pId;
                    String title = mTitleEt.getText().toString().trim();
                    String imageURL = mImageEt.getText().toString().trim();
                    String date = mDateEt.getText().toString().trim();
                    String description = mDescriptionEt.getText().toString().trim();
                    String price = mPrice.getText().toString().trim();
                    updateData(id, title, imageURL, date, description, price);

                } else {
                    String title = mTitleEt.getText().toString().trim();
                    String imageURL = mImageEt.getText().toString().trim();
                    String date = mDateEt.getText().toString().trim();
                    String description = mDescriptionEt.getText().toString().trim();
                    String price = mPrice.getText().toString().trim();
                    uploadData(title, imageURL, date, description, price);
                }

            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddExhibitionActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    private void updateData(String id, String title, String imageURL, String date, String description, String price) {
        pd.setTitle("Updating Data...");
        pd.show();

        db.collection("Documents").document(id)
                .update("title", title, "imageURL", imageURL, "date", date, "description", description, "price", price)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(AddExhibitionActivity.this, "Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddExhibitionActivity.this, MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddExhibitionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void uploadData(String title, String imageURL, String date, String description, String price) {
        pd.setTitle("Adding Data to Firestore");
        pd.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("title", title);
        doc.put("imageURL", imageURL);
        doc.put("date", date);
        doc.put("description", description);
        doc.put("price", price);

        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(AddExhibitionActivity.this, "Uploaded..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddExhibitionActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddExhibitionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}