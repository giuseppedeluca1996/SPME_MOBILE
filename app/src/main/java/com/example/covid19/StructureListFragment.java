 package com.example.covid19;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covid19.controller.SearchController;
import com.example.covid19.model.Review;
import com.example.covid19.model.Structure;
import com.example.covid19.model.StructureListAdapter;

import java.util.List;


 public class StructureListFragment extends Fragment {


    private RecyclerView structureRecycleView;
    private Button filterButton;
    private Button orderButton;
    private Boolean isEmpty;
    private TextView searchEmpty;

    private Structure[] structures;

    public StructureListFragment() {
        // Required empty public constructor
    }


    public static StructureListFragment newInstance(String param1, String param2) {
        StructureListFragment fragment = new StructureListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            StructureListFragmentArgs args = StructureListFragmentArgs.fromBundle(getArguments());
            structures=args.getStructures();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_structure_list, container, false);
    }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);

         filterButton=view.findViewById(R.id.filterStructureList);
         orderButton=view.findViewById(R.id.orderStructureList);
         filterButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SearchController.showFilter();
             }
         });
         orderButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SearchController.showOrder();
             }
         });
         structureRecycleView=view.findViewById(R.id.structureListRecycleView);
         StructureListAdapter structureListAdapter= new StructureListAdapter(requireContext(), structures);
         structureRecycleView.setAdapter(structureListAdapter);
         structureRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
         searchEmpty=view.findViewById(R.id.searchEmpty);
         if(structures.length==0){
             searchEmpty.setVisibility(View.VISIBLE);
         }else {
             searchEmpty.setVisibility(View.INVISIBLE);
         }
    }
 }