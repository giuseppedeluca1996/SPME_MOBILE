package com.example.covid19.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;

import java.text.SimpleDateFormat;

public class ExtendedReviewListAdapter extends RecyclerView.Adapter<ExtendedReviewListAdapter.MyViewHolder> {

    private Review[] reviews;
    private Context context;

    public ExtendedReviewListAdapter(Context ct, Review[] reviews){
        this.context=ct;
        this.reviews=reviews;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.extended_review_list,parent,false);
        return new ExtendedReviewListAdapter.MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(reviews[position].getIdUser().getPreferencesView()) {
            holder.username.setText(reviews[position].getIdUser().getUsername());
        }else {
            holder.username.setText(reviews[position].getIdUser().getName().concat(" " + reviews[position].getIdUser().getSurname()));
        }
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        holder.date.setText(sdf.format(reviews[position].getDate()));
        holder.description.setText(reviews[position].getDescription());
        holder.ratReview.setRating(reviews[position].getRating().floatValue());
        holder.ratingServices.setRating(reviews[position].getService().floatValue());
        holder.ratingQualityPrice.setRating(reviews[position].getQualityPrice().floatValue());
        holder.ratinCleaning.setRating(reviews[position].getCleaning().floatValue());



    }

    @Override
    public int getItemCount() {
        return reviews.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username,date,description;
        RatingBar ratReview,ratingServices,ratingQualityPrice,ratinCleaning;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTextView);
            date = itemView.findViewById(R.id.dateTextView);
            description = itemView.findViewById(R.id.descTextView);
            ratReview = itemView.findViewById(R.id.ratReview);
            ratingServices = itemView.findViewById(R.id.serviceRating);
            ratinCleaning = itemView.findViewById(R.id.cleaningRating);
            ratingQualityPrice = itemView.findViewById(R.id.qualityPriceRating);

        }
    }
}
