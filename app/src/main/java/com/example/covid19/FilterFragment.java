package com.example.covid19;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.covid19.controller.SearchController;
import com.example.covid19.model.Filter;
import com.example.covid19.model.Type;

import org.jetbrains.annotations.NotNull;

import me.bendik.simplerangeview.SimpleRangeView;


public class FilterFragment extends Fragment {


    private  SimpleRangeView budgetBar;
    private RatingBar starRating;
    private Button clickFilter;
    private TextView value;
    private Filter filter;
    private CheckBox hotelCheckBox;
    private CheckBox restaurantCheckBox;
    private CheckBox attractionCheckBox;


    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments() != null){
            FilterFragmentArgs args = FilterFragmentArgs.fromBundle(getArguments());
            filter=args.getFilter();
        }
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        starRating = view.findViewById(R.id.ratingBar);
        clickFilter = view.findViewById(R.id.clickFilter);
        budgetBar = view.findViewById(R.id.budgetBar);
        value = view.findViewById(R.id.budgetValue);
        hotelCheckBox = view.findViewById(R.id.hotelcheckBox);
        attractionCheckBox = view.findViewById(R.id.attractionCheckBox);
        restaurantCheckBox = view.findViewById(R.id.restaurantCheckBox);





        clickFilter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                filter.setPriceMin((double)budgetBar.getStart());
                filter.setPriceMax((double)budgetBar.getEnd());
                filter.removeAllType();
                if( hotelCheckBox.isChecked()){
                    filter.addType(Type.HOTEL);
                }
                if( attractionCheckBox.isChecked()){
                    filter.addType(Type.ATTRACTION);
                }
                if( restaurantCheckBox.isChecked()){
                    filter.addType(Type.RESTAURANT);
                }
                filter.setRating((double)starRating.getRating());
                SearchController.refreshFilter(filter);
            }
        });


        budgetBar.setOnChangeRangeListener(new SimpleRangeView.OnChangeRangeListener() {
            @Override
            public void onRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i, int i1) {
                value.setText(budgetBar.getStart() + "€ - " + budgetBar.getEnd() + "€");

            }
        });

        budgetBar.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                value.setText(i+"€");
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                value.setText(i+"€");

            }
        });

        budgetBar.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return String.valueOf(i);
            }
        });


        if(filter.getTypes().contains(Type.HOTEL)){
            hotelCheckBox.setChecked(true);
        }
        if(filter.getTypes().contains(Type.ATTRACTION)){
            attractionCheckBox.setChecked(true);
        }
        if(filter.getTypes().contains(Type.RESTAURANT)){
            restaurantCheckBox.setChecked(true);
        }
        starRating.setRating(filter.getRating().floatValue());
        budgetBar.setStart(filter.getPriceMin().intValue());
        budgetBar.setEnd(filter.getPriceMax().intValue());
        value.setText(budgetBar.getStart() + "€ - " + budgetBar.getEnd() + "€");
    }


}