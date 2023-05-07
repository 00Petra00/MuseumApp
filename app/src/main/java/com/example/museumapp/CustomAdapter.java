package com.example.museumapp;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    MainActivity mainActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(MainActivity mainActivity, List<Model> modelList) {
        this.mainActivity = mainActivity;
        this.modelList = modelList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String id = modelList.get(position).getId();
                String title = modelList.get(position).getTitle();
                String date = modelList.get(position).getDate();
                String image = modelList.get(position).getImageURL();
                String description = modelList.get(position).getDescription();
                String price = modelList.get(position).getPrice();
//                Toast.makeText(mainActivity, description, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(mainActivity, DetailsActivity.class);
                myIntent.putExtra("id", id);
                myIntent.putExtra("title", title);
                myIntent.putExtra("date", date);
                myIntent.putExtra("imageURL", image);
                myIntent.putExtra("description", description);
                myIntent.putExtra("price", price);
                mainActivity.startActivity(myIntent);
                mainActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getTitle();
                            String date = modelList.get(position).getDate();
                            String image = modelList.get(position).getImageURL();
                            String description = modelList.get(position).getDescription();
                            String price = modelList.get(position).getPrice();

                            Intent intent = new Intent(mainActivity, AddExhibitionActivity.class);
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);
                            intent.putExtra("pDate", date);
                            intent.putExtra("pImage", image);
                            intent.putExtra("pDescription", description);
                            intent.putExtra("pPrice", price);
                            mainActivity.startActivity(intent);
                        }
                        if(i ==1){
                            mainActivity.deleteData(position);

                        }
                    }
                }).create().show();

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {

        viewHolder.mTitleTv.setText(modelList.get(i).getTitle());
        viewHolder.mDateTv.setText(modelList.get(i).getDate());
        if(!modelList.get(i).getImageURL().isEmpty()){
        Picasso.get().load(modelList.get(i).getImageURL().trim()).into(viewHolder.mImageIV);}
        else {
        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiMwvEZn-Bjr6epZ7jT0dtETNRh9hW67tVmw&usqp=CAU").into(viewHolder.mImageIV);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
