package com.example.covid19.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;


public class Review implements Serializable, Parcelable {
    private Double rating;
    private String description;
    private Date date;
    private Double qualityPrice;
    private Double cleaning;
    private Double service;
    private User idUser;
    private Integer exte_id;

    public Review(){

    }


    protected Review(Parcel in) {
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        description = in.readString();
        if (in.readByte() == 0) {
            qualityPrice = null;
        } else {
            qualityPrice = in.readDouble();
        }
        if (in.readByte() == 0) {
            cleaning = null;
        } else {
            cleaning = in.readDouble();
        }
        if (in.readByte() == 0) {
            service = null;
        } else {
            service = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeString(description);
        if (qualityPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(qualityPrice);
        }
        if (cleaning == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(cleaning);
        }
        if (service == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(service);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getQualityPrice() {
        return qualityPrice;
    }

    public void setQualityPrice(Double qualityPrice) {
        this.qualityPrice = qualityPrice;
    }

    public Double getCleaning() {
        return cleaning;
    }

    public void setCleaning(Double cleaning) {
        this.cleaning = cleaning;
    }

    public Double getService() {
        return service;
    }

    public void setService(Double service) {
        this.service = service;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Integer getExte_id() {
        return exte_id;
    }

    public void setExte_id(Integer exte_id) {
        this.exte_id = exte_id;
    }
}
