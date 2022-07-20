package com.example.covid19;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covid19.model.ExtendedReviewListAdapter;
import com.example.covid19.model.Review;
import com.example.covid19.model.Structure;
import com.example.covid19.security.AuthManagerFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExtendedReviewFragment extends Fragment {


    private Review[] reviews;
    private Structure structure;
    private RecyclerView extendedReviewsRecyclerView;
    private FloatingActionButton addReview;
    private NavController navController;

    public ExtendedReviewFragment() {
        // Required empty public constructor
    }

    public static ExtendedReviewFragment newInstance(String param1, String param2) {
        ExtendedReviewFragment fragment = new ExtendedReviewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            ExtendedReviewFragmentArgs args = ExtendedReviewFragmentArgs.fromBundle(getArguments());
            reviews=args.getReviews();
            structure=args.getStructure();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_extended_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(getActivity(),R.id.fragment);
        extendedReviewsRecyclerView=view.findViewById(R.id.extendedReviewRecyclerView);
        addReview=view.findViewById(R.id.floating_action_button);
        ExtendedReviewListAdapter extendedReviewListAdapter= new ExtendedReviewListAdapter(requireContext(), reviews);
        extendedReviewsRecyclerView.setAdapter(extendedReviewListAdapter);
        extendedReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AuthManagerFactory.getAuthManagerFactory(requireContext()).getAuthManager().getAuthenticationString() != null){
                    ExtendedReviewFragmentDirections.ActionExtendedReviewFragmentToWriteReviewFragment a = ExtendedReviewFragmentDirections.actionExtendedReviewFragmentToWriteReviewFragment(structure);
                    navController.navigate(a);
                }else {
                    getDialogValueBack(getContext());
                }
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

}