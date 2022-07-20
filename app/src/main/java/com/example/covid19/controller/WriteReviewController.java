package com.example.covid19.controller;

import android.app.Activity;
import android.content.Context;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covid19.R;
import com.example.covid19.WriteReviewFragmentDirections;
import com.example.covid19.dao.DaoFactory;
import com.example.covid19.dao.ReviewDao;
import com.example.covid19.model.Review;
import com.example.covid19.model.Structure;

import java.util.Objects;

public class WriteReviewController {

    private static Context context;
    private static ReviewDao reviewDao;
    private static NavController navController;

    public static void setContext(Context context) {
        WriteReviewController.context = context;
        reviewDao = Objects.requireNonNull(DaoFactory.getDaoFactory(context)).getReviewDao();
        navController= Navigation.findNavController((Activity)context, R.id.fragment);
    }

    public static Boolean  publicReview(Review review, Structure structure){
       if(reviewDao.save(review)){
           WriteReviewFragmentDirections.ActionWriteReviewFragmentToSpecificStructureFragment actionWriteReviewFragmentToSpecificStructureFragment=WriteReviewFragmentDirections.actionWriteReviewFragmentToSpecificStructureFragment(structure, Objects.requireNonNull(StructureViewController.getReviews(review.getExte_id())));
           navController.navigate(actionWriteReviewFragmentToSpecificStructureFragment);
           return true;
       }else {
           return false;
       }
    }
}
