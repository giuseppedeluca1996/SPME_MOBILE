package com.example.covid19;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.covid19.controller.HomePageController;
import com.example.covid19.model.Type;

public class HomePageFragment extends Fragment implements View.OnClickListener {

    private ImageView imageViewHomePageFragment;
    private ImageView imageViewMapHomePaqeFragment;

    private ImageButton hotelButton;
    private ImageButton attractionButton;
    private ImageButton restaurantButton;
    private Button whereDoYouWantGo;





    public HomePageFragment() {
    }

    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewHomePageFragment=view.findViewById(R.id.imageViewHomePageFragment);
        imageViewHomePageFragment.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewMapHomePaqeFragment=view.findViewById(R.id.imageViewMapHomePaqeFragment);
        imageViewMapHomePaqeFragment.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewMapHomePaqeFragment.setOnClickListener(this);
        hotelButton=view.findViewById(R.id.imageButtonHotel);
        attractionButton=view.findViewById(R.id.imageButtonAttraction);
        restaurantButton=view.findViewById(R.id.imageButtonRestaurant);
        whereDoYouWantGo=view.findViewById(R.id.whereDoYouWantGoButton);
        hotelButton.setOnClickListener(this);
        attractionButton.setOnClickListener(this);
        restaurantButton.setOnClickListener(this);
        whereDoYouWantGo.setOnClickListener(this);
    }


    public void onClick(View view){
        final NavController navController =  Navigation.findNavController(view);
        switch (view.getId()){
            case R.id.imageButtonHotel:{
                HomePageFragmentDirections.ActionHomepageToSearchFragment actionHomepageToSearchFragment = HomePageFragmentDirections.actionHomepageToSearchFragment(Type.HOTEL);
                navController.navigate(actionHomepageToSearchFragment);
            }break;
            case R.id.imageButtonAttraction:{
                HomePageFragmentDirections.ActionHomepageToSearchFragment actionHomepageToSearchFragment = HomePageFragmentDirections.actionHomepageToSearchFragment(Type.ATTRACTION);
                navController.navigate(actionHomepageToSearchFragment);
            }break;
            case R.id.imageButtonRestaurant:{
                HomePageFragmentDirections.ActionHomepageToSearchFragment actionHomepageToSearchFragment = HomePageFragmentDirections.actionHomepageToSearchFragment(Type.RESTAURANT);
                navController.navigate(actionHomepageToSearchFragment);
            }break;
            case R.id.imageViewMapHomePaqeFragment:{
               if(HomePageController.showMap()){
                   if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                           && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                       ActivityCompat.requestPermissions( requireActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},10001 );
                       return;
                   }
                   HomePageFragmentDirections.ActionHomePageToMapsFragment actionHomePageToMapsFragment = HomePageFragmentDirections.actionHomePageToMapsFragment(null);
                   Navigation.findNavController(view).navigate(actionHomePageToMapsFragment);
                }else {
                   showGPSDisabledAlertToUser();
               }
            }break;
            case R.id.whereDoYouWantGoButton:{
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( requireActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},10001);
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                HomePageFragmentDirections.ActionHomepageToSearchFragment actionHomepageToSearchFragment = HomePageFragmentDirections.actionHomepageToSearchFragment(Type.NOT_DEFINE);
                navController.navigate(actionHomepageToSearchFragment);
            }break;
            default:{

            }
        }
    }

    private  void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
