package com.example.covid19.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Order implements Serializable, Parcelable {

    SortingCriteria sortingCriteria;

    protected Order(Parcel in) {
    }

    public Order() {
       sortingCriteria=SortingCriteria.HIGHER_RATING;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


    public SortingCriteria getSortingCriteria() {
        return sortingCriteria;
    }

    public void setSortingCriteria(SortingCriteria sortingCriteria) {
        this.sortingCriteria = sortingCriteria;
    }
}
