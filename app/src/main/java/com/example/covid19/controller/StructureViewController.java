package com.example.covid19.controller;

import android.content.Context;

import com.example.covid19.dao.DaoFactory;
import com.example.covid19.dao.ReviewDao;
import com.example.covid19.model.Review;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StructureViewController {

    private static ReviewDao reviewDao;
    private static Context context;

    public static void setContext(Context context) {
        StructureViewController.context = context;
        reviewDao = Objects.requireNonNull(DaoFactory.getDaoFactory(context)).getReviewDao();
    }


    public static Review[] getReviews(Integer id) {
        List<Review> reviewList=reviewDao.getAllByIdStructure(id);
        if(reviewList!=null){
           return Arrays.copyOf(reviewList.toArray(),reviewList.size(),Review[].class);
        }
        return null;
    }
}
