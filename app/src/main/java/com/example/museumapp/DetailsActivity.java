package com.example.museumapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel_id01";
    private static final int NOTIFICATION_ID = 1;
    TextView mTitleEt, mDateEt, mDescriptionEt, mPrice;
    ImageView mImageEt;
    Button mcancelBtn, mbuyBtn;
    String pId, pTitle, pImage, pDate, pDescription, pPrice;

    NotificationCompat.Builder mBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details");
        mTitleEt = findViewById(R.id.dTitle);
        mImageEt = findViewById(R.id.didIVExhibition);
        mDateEt = findViewById(R.id.dDate);
        mDescriptionEt = findViewById(R.id.dDescription);
        mPrice = findViewById(R.id.dPrice);
        mcancelBtn = findViewById(R.id.idCancelBtn);
        mbuyBtn = findViewById(R.id.idBuyBtn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Details");
            pId = bundle.getString("id");
            pTitle = bundle.getString("title");
            pImage = bundle.getString("imageURL");
            pDate = bundle.getString("date");
            pDescription = bundle.getString("description");
            pPrice = bundle.getString("price");

            mTitleEt.setText(pTitle);
            if (!pImage.isEmpty()) {
                Picasso.get().load(pImage).into(mImageEt);
            } else {
                Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiMwvEZn-Bjr6epZ7jT0dtETNRh9hW67tVmw&usqp=CAU").into(mImageEt);
            }
            mDateEt.setText(pDate);
            mDescriptionEt.setText(pDescription);
            mPrice.setText(pPrice + "Ft");

        }

        mbuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, "Succesful purchase", Toast.LENGTH_SHORT).show();
                //                mNotificationManager.notify(1, mBuilder.build());
                showNotification();
            }
        });
        mcancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }

    private void showNotification() {
        createNotificationChannel();
        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_logo);
        mBuilder.setContentTitle("Ticket purchase");
        mBuilder.setContentText("Your ticket purchase was succesful");

        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());

        
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "My Notification";
            String description = "My notification description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}