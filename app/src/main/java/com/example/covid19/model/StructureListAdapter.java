package com.example.covid19.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;
import com.example.covid19.StructureListFragmentDirections;
import com.example.covid19.controller.SearchController;
import com.example.covid19.controller.StructureViewController;


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


public class StructureListAdapter extends RecyclerView.Adapter<StructureListAdapter.MyViewHolder> {

    private Structure[] structures;
    private Context context;


    public StructureListAdapter(Context context, Structure[] structures) {
        this.context=context;
        this.structures=structures;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.structure_list, parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.nameStructure.setText(structures[position].getName());
            holder.addressStructure.setText(structures[position].getAddress());
            holder.ratingStructure.setRating(structures[position].getAvgRating().floatValue());
            holder.imageStructure.setTag(structures[position].getImageLink());
            class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {
                ImageView imageView = null;
                @Override
                protected Bitmap doInBackground(ImageView... imageViews) {
                    this.imageView = imageViews[0];
                    try {
                        return download_Image((String) imageView.getTag());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    imageView.setImageBitmap(result);
                }

                private Bitmap download_Image(String url) throws IOException {
                    return  BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
                }
            }
            new DownloadImagesTask().execute(holder.imageStructure);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  NavController navController =  Navigation.findNavController((Activity) context, R.id.fragment);
                  StructureListFragmentDirections.ActionStructureListToSpecificStructureFragment actionStructureListToSpecificStructureFragment=StructureListFragmentDirections.actionStructureListToSpecificStructureFragment(structures[position], StructureViewController.getReviews(structures[position].getId()));
                  navController.navigate(actionStructureListToSpecificStructureFragment);
                }
            });
    }

    @Override
    public int getItemCount() {
        return structures.length;
    }

    public class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nameStructure;
        TextView addressStructure;
        RatingBar ratingStructure;
        ImageView imageStructure;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameStructure=itemView.findViewById(R.id.name_list_structure);
            addressStructure=itemView.findViewById(R.id.addres_list_structure);
            ratingStructure=itemView.findViewById(R.id.ratingBar_list_structure);
            imageStructure=itemView.findViewById(R.id.image_list_structure);

        }


    }
}
