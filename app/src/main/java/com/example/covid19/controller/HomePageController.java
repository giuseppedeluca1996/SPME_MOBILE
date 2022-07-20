package com.example.covid19.controller;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;


import com.example.covid19.HomePageActivity;
import com.example.covid19.model.Structure;

import java.util.Collection;


public class HomePageController {

    private static  Context context;


    public static void showHomePage(Context context) {
        context.startActivity(new Intent(context, HomePageActivity.class));
    }


    public static Boolean showMap(){
        final LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
            return false;
        }else {
            return true;
        }
    }


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        HomePageController.context = context;
    }
}
