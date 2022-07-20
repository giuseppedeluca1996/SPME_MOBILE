package com.example.covid19.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.covid19.R;

import java.util.List;

public class TipsListAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<String> value;



    public TipsListAdapter(Activity context, List<String>value) {
        super(context, R.layout.search_tips, value);
        this.context = context;
        this.value = value;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.search_tips, null, true);
        TextView nameTextField = (TextView) rowView.findViewById(R.id.list_view_row);
        nameTextField.setText(value.get(position));
        return rowView;

    }

}