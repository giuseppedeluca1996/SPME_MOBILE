package com.example.covid19.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Filter implements Serializable, Parcelable {

    private Set<Type> types;
    private Double rating;
    private Double priceMin;
    private Double priceMax;
    private List<String> services;
    private Double distance;

    public Filter() {
        rating=5D;
        priceMin=0D;
        priceMax=10000D;
        services=new ArrayList<>();
        types=new ArraySet<>();
        addAllType();
        distance=50D;
    }

    protected Filter(Parcel in) {
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        if (in.readByte() == 0) {
            priceMin = null;
        } else {
            priceMin = in.readDouble();
        }
        if (in.readByte() == 0) {
            priceMax = null;
        } else {
            priceMax = in.readDouble();
        }
        services = in.createStringArrayList();
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    public void removeType(Type type){
        types.remove(type);
    }

    public void addType(Type type){
        types.add(type);
    }

    public  void removeAllType(){
        types.clear();
    }

    public  void addAllType(){
        types.add(Type.HOTEL);
        types.add(Type.RESTAURANT);
        types.add(Type.ATTRACTION);
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        if (priceMin == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(priceMin);
        }
        if (priceMax == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(priceMax);
        }
        dest.writeStringList(services);
    }
}
