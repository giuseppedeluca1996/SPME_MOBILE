package com.example.covid19;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Context.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.covid19.controller.SearchController;
import com.example.covid19.model.TipsListAdapter;
import com.example.covid19.model.Type;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchFragment extends Fragment {



    List<String> values = new ArrayList<>();
    private SearchView searchView;
    private Button searchButton;
    private Button aroundYouButton;
    private ImageButton backButton;
    private ImageView aroundYouImg;
    private ListView listView;
    private long lastSearchTime = 0;
    private  LocationManager manager;
    private Type type;


    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        manager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        type=null;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TipsListAdapter whatever = new TipsListAdapter(getActivity(), values);
        listView = view.findViewById(R.id.listView);
        aroundYouImg = view.findViewById(R.id.aroundYouImage);
        searchView = view.findViewById(R.id.search);
        backButton = view.findViewById(R.id.backButton);
        aroundYouButton = view.findViewById(R.id.aroundYouBtn);



        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            aroundYouImg.setAlpha(0.3F);
            aroundYouButton.setAlpha(0.3F);
            aroundYouImg.setClickable(false);
            aroundYouButton.setClickable(false);
        }

        view.findViewById(R.id.searchFragmentParent).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(INPUT_METHOD_SERVICE);
                if (requireActivity().getCurrentFocus() != null && requireActivity().getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
                }
                return true;
            }
        });

        final Button footer = new Button(getContext());
        footer.setVisibility(View.INVISIBLE);
        footer.setGravity(Gravity.LEFT + 15);
        footer.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        footer.setHeight(50);
        footer.setTextSize(14);
        footer.offsetLeftAndRight(50);
        footer.setBackgroundColor(Color.TRANSPARENT);
        footer.setTextColor(Color.parseColor("#1589FF"));
        footer.setAllCaps(false);
        footer.setText("Search");
        footer.setPaddingRelative(20,0,0,0);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == Type.NOT_DEFINE)
                    SearchController.getStructureByText(searchView.getQuery().toString());
                else
                    SearchController.getStructureByText(searchView.getQuery().toString(),type);
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
            }
        });

        listView.setAdapter(whatever);
        listView.addFooterView(footer);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type == Type.NOT_DEFINE)
                    SearchController.getStructureByText(parent.getItemAtPosition(position).toString());
                else
                    SearchController.getStructureByText(parent.getItemAtPosition(position).toString(),type);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(type == Type.NOT_DEFINE)
                    SearchController.getStructureByText(query);
                else
                    SearchController.getStructureByText(query,type);

                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                long actualSearchTime = (Calendar.getInstance()).getTimeInMillis();
                if (newText.length() >= 3 && actualSearchTime > lastSearchTime + 300) {
                    values.clear();
                    lastSearchTime = actualSearchTime;
                    aroundYouButton.setVisibility(View.INVISIBLE);
                    aroundYouImg.setVisibility(View.INVISIBLE);

                    List<String> ris=SearchController.getTips(newText);
                    if(ris != null)
                         values.addAll(ris);

                    whatever.notifyDataSetChanged();
                    footer.setVisibility(View.VISIBLE);
                } else if (newText.length() < 3) {
                    values.clear();
                    whatever.notifyDataSetChanged();
                    footer.setVisibility(View.INVISIBLE);
                    aroundYouButton.setVisibility(View.VISIBLE);
                    aroundYouImg.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_homePagegment);
            }
        });

        aroundYouButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    showGPSDisabledAlertToUser();
                    return;
                } else {
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location locationGPS = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                       // Location locationGPS2 = manager.getCurrentLocation(LocationManager.GPS_PROVIDER, null, ContextCompat.getMainExecutor(this),null);

                        LatLng latLng = new LatLng(locationGPS.getLatitude(), locationGPS.getLongitude());
                        if (type != Type.NOT_DEFINE)
                            SearchController.getStructureAroundYou(latLng, type);
                        else
                            SearchController.getStructureAroundYou(latLng);
                    }
                }
            }
        });

        if(getArguments() != null){
            SearchFragmentArgs args = SearchFragmentArgs.fromBundle(getArguments());
            type=args.getType();

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