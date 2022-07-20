package com.example.covid19;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19.controller.WriteReviewController;

import com.example.covid19.model.Review;
import com.example.covid19.model.Structure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class WriteReviewFragment extends Fragment {

    private Structure structure;
    private TextView  nameStructureTextWriteReview;
    private RatingBar reviewRatingAddReview;
    private EditText  descriptionAddReview;
    private RatingBar qualityPriceRatingAddReview;
    private RatingBar serviceRatingAddReview;
    private RatingBar cleaningRatingAddReview;
    private CheckBox  checkBoxAddReview;
    private Button publicReviewButton;

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    public static WriteReviewFragment newInstance(String param1, String param2) {
        WriteReviewFragment fragment = new WriteReviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            WriteReviewFragmentArgs args = WriteReviewFragmentArgs.fromBundle(getArguments());
            structure=args.getStructure();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_write_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.writeReviewParent).setOnTouchListener(new View.OnTouchListener() {
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


        nameStructureTextWriteReview=view.findViewById(R.id.nameStructureTextWriteReview);
        reviewRatingAddReview=view.findViewById(R.id.reviewRatingAddReview);
        descriptionAddReview=view.findViewById(R.id.descriptionAddReview);
        qualityPriceRatingAddReview=view.findViewById(R.id.qualityPriceRatingAddReview);
        serviceRatingAddReview=view.findViewById(R.id.serviceRatingAddReview);
        cleaningRatingAddReview=view.findViewById(R.id.cleaningRatingAddReview);
        checkBoxAddReview=view.findViewById(R.id.checkBoxAddReview);
        publicReviewButton=view.findViewById(R.id.publicReviewButton);

        publicReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reviewRatingAddReview.getRating() == 0D || descriptionAddReview.getText().length()<30){

                  if(reviewRatingAddReview.getRating() == 0D){
                      Toast toast = Toast.makeText(getContext(), "Rating it cannot be empty!", Toast.LENGTH_SHORT);
                      toast.show();
                  }

                  if(descriptionAddReview.getText().length()<30){
                        Toast toast = Toast.makeText(getContext(), "Description must contain at least 30 characters!", Toast.LENGTH_SHORT);
                        toast.show();
                  }
                  return;
                }else {
                    Review review = new Review();
                    review.setRating((double) reviewRatingAddReview.getRating());
                    review.setCleaning((double) cleaningRatingAddReview.getRating());
                    review.setService((double)  serviceRatingAddReview.getRating());
                    review.setQualityPrice((double) qualityPriceRatingAddReview.getRating());
                    review.setDescription(descriptionAddReview.getText().toString());
                    review.setExte_id(structure.getId());
                    if(WriteReviewController.publicReview(review, structure)){
                        Toast toast = Toast.makeText(getContext(), "Review published", Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(getContext(), "Structure already reviewed!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });



        nameStructureTextWriteReview.setText(structure.getName());


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                    alert.setTitle("Back pressed");
                    alert.setMessage("if you go back, all the data entered will be lose!\nAre you sure?");
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().onBackPressed();
                        }
                    });

                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                    return true;
                }
                return false;
            }
        });

    }
}