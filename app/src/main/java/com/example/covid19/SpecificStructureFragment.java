package com.example.covid19;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.covid19.model.Review;
import com.example.covid19.model.ReviewListAdapter;
import com.example.covid19.model.Structure;
import com.example.covid19.security.AuthManagerFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class SpecificStructureFragment extends Fragment {


    private RecyclerView reviewsRecyclerView;
    private Structure structure;
    private Review[] reviews;
    private ImageView image;
    private TextView nameStructureSpecificStructure;
    private TextView phone;
    private TextView email;
    private TextView site;
    private TextView openClose;
    private RatingBar ratingBar;
    private NavController navController;
    private TextView review;
    private Button button;
    private FloatingActionButton addReview;
    private Button openOnMaps;

    public SpecificStructureFragment() {
        // Required empty public constructor
    }


    public static SpecificStructureFragment newInstance(String param1, String param2) {
        SpecificStructureFragment fragment = new SpecificStructureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            SpecificStructureFragmentArgs args = SpecificStructureFragmentArgs.fromBundle(getArguments());
            structure=args.getStructure();
            reviews=args.getReviews();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_specific_structure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(requireActivity(),R.id.fragment);


        image=view.findViewById(R.id.structureImage);
        image.setTag(structure.getImageLink());
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
        new DownloadImagesTask().execute(image);
        image.setScaleType(ImageView.ScaleType.FIT_XY);

        nameStructureSpecificStructure=view.findViewById(R.id.nameStructureSpecificStructure);
        nameStructureSpecificStructure.setText(structure.getName());
        phone=view.findViewById(R.id.phoneSpecificStructure);
        phone.setText(structure.getPhone());
        email=view.findViewById(R.id.emailtexViewSpecificStructure);
        email.setText(structure.getEmail());
        site=view.findViewById(R.id.siteWebTextViewSpecificStructure);
        site.setText(structure.getSite());
        openClose=view.findViewById(R.id.OpenClosetextViewSpecificStructure);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        openClose.setText(sdf.format(structure.getOpeningHours()) + " - " + sdf.format(structure.getClosingHours()));
        Double avg;
        Double sum=0D;
        for(Review r : reviews){
            sum=sum+r.getRating();
        }
        avg=sum/reviews.length;
        ratingBar=view.findViewById(R.id.ratingBarSpecificStructure);
        ratingBar.setRating(avg.floatValue());

        reviewsRecyclerView =view.findViewById(R.id.reviewListRecycleView);
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(requireContext(), reviews);
        reviewsRecyclerView.setAdapter(reviewListAdapter);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        review=view.findViewById(R.id.infoTextView2);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecificStructureFragmentDirections.ActionSpecificStructureFragmentToExtendedReviewFragment a= SpecificStructureFragmentDirections.actionSpecificStructureFragmentToExtendedReviewFragment(reviews, structure);
                navController.navigate(a);
            }
        });
        button=view.findViewById(R.id.buttonOpenReviewSpecificStructure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecificStructureFragmentDirections.ActionSpecificStructureFragmentToExtendedReviewFragment a= SpecificStructureFragmentDirections.actionSpecificStructureFragmentToExtendedReviewFragment(reviews, structure);
                navController.navigate(a);
            }
        });

        addReview=view.findViewById(R.id.floatingActionButtonSpecificStructure);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.requireNonNull(AuthManagerFactory.getAuthManagerFactory(requireContext())).getAuthManager().getAuthenticationString() != null){
                    SpecificStructureFragmentDirections.ActionSpecificStructureFragmentToWriteReviewFragment a = SpecificStructureFragmentDirections.actionSpecificStructureFragmentToWriteReviewFragment(structure);
                    navController.navigate(a);
                }else {
                    getDialogValueBack(getContext());
                }
            }
        });

        openOnMaps=view.findViewById(R.id.mapButtonSpecificStructure2);
        openOnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager manager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE );
                if( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                    showGPSDisabledAlertToUser();
                    return;
                }
                SpecificStructureFragmentDirections.ActionSpecificStructureFragmentToMapsFragment actionSpecificStructureFragmentToMapsFragment=SpecificStructureFragmentDirections.actionSpecificStructureFragmentToMapsFragment(structure);
                navController.navigate(actionSpecificStructureFragmentToMapsFragment);
            }
        });


    }

    public void getDialogValueBack(Context context) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("You must be logged in to write a review!");
        alert.setPositiveButton("Sing In", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                Intent singInactivity = new Intent( getContext(),SignInActivity.class);
                startActivity(singInactivity);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.dismiss();

            }
        });
        alert.show();
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