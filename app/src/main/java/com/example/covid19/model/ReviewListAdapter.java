package com.example.covid19.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;

import java.text.SimpleDateFormat;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.MyViewHolder> {

    private Review[] reviews;
    private Context context;



    public ReviewListAdapter(Context ct, Review[] reviews){
        this.context=ct;
        this.reviews=reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_list,parent,false);
        return new MyViewHolder(view);
    }

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
        holder.description.setClickable(false);
        holder.description.setFocusable(false);
        holder.description.setEnabled(false);
        holder.rating.setRating(reviews[position].getRating().floatValue());
    }

    @Override
    public int getItemCount() {
        return reviews.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView date,username;
        RatingBar rating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameReviewAdapter);
            date = itemView.findViewById(R.id.dateTextViewReviewAdapter);
            description = itemView.findViewById(R.id.editTextTextMultiLineReviewAdapter);
            rating = itemView.findViewById(R.id.ratingReviewReviewAdapter);

        }
    }

}
