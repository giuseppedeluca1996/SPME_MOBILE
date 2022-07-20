package com.example.covid19.controller;

import android.app.Activity;
import android.content.Context;

import android.os.AsyncTask;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.example.covid19.FilterFragmentDirections;
import com.example.covid19.OrderFragmentDirections;
import com.example.covid19.R;
import com.example.covid19.SearchFragmentDirections;
import com.example.covid19.StructureListFragmentDirections;
import com.example.covid19.dao.DaoFactory;
import com.example.covid19.dao.ReviewDao;
import com.example.covid19.dao.StructureDao;
import com.example.covid19.model.Filter;
import com.example.covid19.model.Order;
import com.example.covid19.model.Structure;
import com.example.covid19.model.Type;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class SearchController {

    private static StructureDao structureDao;
    private static ReviewDao reviewDao;
    private static Context context;
    private static NavController navController;

    private static List<Structure> structures;
    private static Filter filter =  new Filter();
    private static Order order = new Order();
    private static String searchValue;
    private static LatLng lastPosition;

    public static Collection<Structure> getStructureAtDistance(LatLng startPosition, Double distance){
            return structureDao.getStructureAtDistance(BigDecimal.valueOf(startPosition.latitude),BigDecimal.valueOf(startPosition.longitude),BigDecimal.valueOf(distance));
    }

    public static void setContext(Context context) {
        SearchController.context = context;
        structureDao = Objects.requireNonNull(DaoFactory.getDaoFactory(context)).getStructureDao();
        reviewDao = Objects.requireNonNull(DaoFactory.getDaoFactory(context)).getReviewDao();
        navController =  Navigation.findNavController((Activity) context, R.id.fragment);

    }

    public static void  getStructureAroundYou(LatLng latLng, Type type) {
        filter.removeAllType();
        filter.addType(type);
        searchValue="";
        lastPosition=latLng;
        structures = structureDao.getStructureAroundYou(BigDecimal.valueOf(latLng.latitude),BigDecimal.valueOf(latLng.longitude),filter,order);
        AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
            @Override
            protected Void doInBackground(List<Structure>... lists) {
                for(Structure s : lists[0]){
                    s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                }
                return null;
            }
        };
        try {
            asyncTask.execute(structures).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        SearchFragmentDirections.ActionSearchFragmentToStructureList searchFragmentDirections =  SearchFragmentDirections.actionSearchFragmentToStructureList(Arrays.copyOf(structures.toArray(), structures.size(), Structure[].class));
        navController.navigate(searchFragmentDirections);
    }

    public static void  getStructureAroundYou(LatLng latLng) {
        filter.addAllType();
        searchValue="";
        lastPosition=latLng;
        structures = structureDao.getStructureAroundYou(BigDecimal.valueOf(latLng.latitude),BigDecimal.valueOf(latLng.longitude),filter,order);
        AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
            @Override
            protected Void doInBackground(List<Structure>... lists) {
                for(Structure s : lists[0]){
                    s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                }
                return null;
            }
        };
        try {
            asyncTask.execute(structures).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        SearchFragmentDirections.ActionSearchFragmentToStructureList searchFragmentDirections =  SearchFragmentDirections.actionSearchFragmentToStructureList(Arrays.copyOf(structures.toArray(), structures.size(), Structure[].class));
        navController.navigate(searchFragmentDirections);
    }

    public static void getStructureByText(String query, Type type) {
        filter.removeAllType();
        filter.addType(type);
        searchValue=query;
        structures =  structureDao.getStructureByText(query, filter,order);
        AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
            @Override
            protected Void doInBackground(List<Structure>... lists) {
                for(Structure s : lists[0]){
                    s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                }
                return null;
            }
        };
        try {
            asyncTask.execute(structures).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        SearchFragmentDirections.ActionSearchFragmentToStructureList searchFragmentDirections =  SearchFragmentDirections.actionSearchFragmentToStructureList(Arrays.copyOf(structures.toArray(), structures.size(), Structure[].class));
        navController.navigate(searchFragmentDirections);
    }

    public static void getStructureByText(String query) {
        filter.addAllType();
        searchValue=query;
        structures = structureDao.getStructureByText(query,filter,order);
        AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
            @Override
            protected Void doInBackground(List<Structure>... lists) {
                for(Structure s : lists[0]){
                    s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                }
                return null;
            }
        };
        try {
            asyncTask.execute(structures).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        SearchFragmentDirections.ActionSearchFragmentToStructureList searchFragmentDirections =  SearchFragmentDirections.actionSearchFragmentToStructureList(Arrays.copyOf(structures.toArray(), structures.size(), Structure[].class));
        navController.navigate(searchFragmentDirections);
    }

    public static List<String> getTips(String query) {
        return structureDao.getTips(query);
    }

    public static void showFilter(){
        StructureListFragmentDirections.ActionStructureListToFilterFragment actionStructureListToFilterFragment = StructureListFragmentDirections.actionStructureListToFilterFragment(filter);
        navController.navigate(actionStructureListToFilterFragment);
    }

    public static void showOrder(){
        StructureListFragmentDirections.ActionStructureListToOrderFragment actionStructureListToOrderFragment = StructureListFragmentDirections.actionStructureListToOrderFragment(order);
        navController.navigate(actionStructureListToOrderFragment);
    }

    public static void refreshFilter(final Filter filter) {
       SearchController.filter=filter;
       if(searchValue.isEmpty()){
          structures=structureDao.getStructureAroundYou(BigDecimal.valueOf(lastPosition.latitude),BigDecimal.valueOf(lastPosition.longitude),filter,order);
           AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
               @Override
               protected Void doInBackground(List<Structure>... lists) {
                   for(Structure s : lists[0]){
                       s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                   }
                   return null;
               }
           };
           try {
               asyncTask.execute(structures).get();
           } catch (ExecutionException | InterruptedException e) {
               e.printStackTrace();
           }
       }else {
           structures=structureDao.getStructureByText(searchValue, filter,order);
           AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
               @Override
               protected Void doInBackground(List<Structure>... lists) {
                   synchronized (lists[0]){
                       for(Structure s : lists[0]){
                           s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                       }
                   }
                   return null;
               }
           };
           try {
               asyncTask.execute(structures).get();
           } catch (ExecutionException | InterruptedException e) {
               e.printStackTrace();
           }
       }
        FilterFragmentDirections.ActionFilterFragmentToStructureList filterFragmentToStructureList =  FilterFragmentDirections.actionFilterFragmentToStructureList(Arrays.copyOf(structures.toArray(), structures.size(), Structure[].class));
        navController.navigate(filterFragmentToStructureList);
    }




    public static void refreshOrder(final Order order) {
        SearchController.order=order;
        if(searchValue.isEmpty()){
            structures=structureDao.getStructureAroundYou(BigDecimal.valueOf(lastPosition.latitude),BigDecimal.valueOf(lastPosition.longitude),filter,order);
            AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
                @Override
                protected Void doInBackground(List<Structure>... lists) {
                    for(Structure s : lists[0]){
                        s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                    }
                    return null;
                }
            };
            try {
                asyncTask.execute(structures).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            structures=structureDao.getStructureByText(searchValue, filter,order);
            AsyncTask<List<Structure>, Void,Void> asyncTask=new AsyncTask<List<Structure>, Void, Void>(){
                @Override
                protected Void doInBackground(List<Structure>... lists) {
                    synchronized (lists[0]){
                        for(Structure s : lists[0]){
                            s.setAvgRating(reviewDao.getAvgRating(s.getId()));
                        }
                    }
                    return null;
                }
            };
            try {
                asyncTask.execute(structures).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        switch (order.getSortingCriteria()){
            case HIGHER_RATING:{
                structures.sort(new Comparator<Structure>() {
                    @Override
                    public int compare(Structure o1, Structure o2) {

                        if(o1.getAvgRating()<o2.getAvgRating()){
                            return 1;
                        } if(o1.getAvgRating()>o2.getAvgRating()) {
                            return -1;
                        }
                        return 0;
                    }
                });
            }break;
            case SMALLEST_DISTANCE:{

            }break;
            case GRATER_DISTANCE:{

            }break;
            case MINOR_RATING:{
                structures.sort(new Comparator<Structure>() {
                    @Override
                    public int compare(Structure o1, Structure o2) {

                        if(o1.getAvgRating()<o2.getAvgRating()){
                            return -1;
                        } if(o1.getAvgRating()>o2.getAvgRating()) {
                            return 1;
                        }
                        return 0;
                    }
                });
            }break;
            default:{

            }
        }

        OrderFragmentDirections.ActionOrderFragmentToStructureList orderFragmentToStructureList =  OrderFragmentDirections.actionOrderFragmentToStructureList(Arrays.copyOf(structures.toArray(), structures.size(), Structure[].class));
        navController.navigate(orderFragmentToStructureList);
    }
}
