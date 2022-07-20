package com.example.covid19;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.covid19.controller.SearchController;
import com.example.covid19.model.Order;
import com.example.covid19.model.SortingCriteria;


public class OrderFragment extends Fragment {



    private RadioGroup radioGroup;
    private RadioButton radioButtonHigherRating;
    private RadioButton radioButtonGreaterDistance;
    private RadioButton radioButtonSmallestDistance;
    private RadioButton radioButtonMinorRating;
    private Order order;
    public OrderFragment() {
    }


    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrderFragmentArgs args = OrderFragmentArgs.fromBundle(getArguments());
        order=args.getOrder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = view.findViewById(R.id.radioGroup);
        radioButtonGreaterDistance=view.findViewById(R.id.thirdRadio);
        radioButtonSmallestDistance=view.findViewById(R.id.fourthRadio);
        radioButtonMinorRating=view.findViewById(R.id.secondRadio);
        radioButtonHigherRating=view.findViewById(R.id.firstRadio);

        switch (order.getSortingCriteria()){
            case MINOR_RATING:{
                radioButtonMinorRating.setChecked(true);
            }break;
            case GRATER_DISTANCE:{
                radioButtonGreaterDistance.setChecked(true);
            }break;
            case SMALLEST_DISTANCE:{
                radioButtonSmallestDistance.setChecked(true);
            }break;
            case HIGHER_RATING:{
                radioButtonHigherRating.setChecked(true);
            } break;
            default:{

            }
        }

        Button buttonOrder = view.findViewById(R.id.clickOrder);
        buttonOrder.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                order.setSortingCriteria(SortingCriteria.valueOf(((RadioButton)view.findViewById(selectedId)).getText().toString().replaceAll(" ","_").toUpperCase()));
                SearchController.refreshOrder(order);
            }
        });

    }



}

