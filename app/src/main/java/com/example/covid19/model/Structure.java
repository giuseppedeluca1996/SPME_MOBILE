package com.example.covid19.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Structure implements Serializable, Parcelable {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private String imageLink;
    @Expose
    private String site;
    @Expose
    private String email;
    @Expose
    private String state;
    @Expose
    private String city;
    @Expose
    private String phone;
    @Expose
    private Type type;
    @Expose
    private Double priceMin;
    @Expose
    private Double priceMax;
    @Expose
    private BigDecimal latitude;
    @Expose
    private BigDecimal longitude;
    @Expose
    private Date closingHours;
    @Expose
    private Date openingHours;
    @Expose(deserialize = false , serialize = false)
    private Double avgRating;

    public Structure() {
    }

    protected Structure(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        address = in.readString();
        imageLink = in.readString();
        site = in.readString();
        email = in.readString();
        state = in.readString();
        city = in.readString();
        phone = in.readString();
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
        if (in.readByte() == 0) {
            avgRating = null;
        } else {
            avgRating = in.readDouble();
        }
    }

    public static final Creator<Structure> CREATOR = new Creator<Structure>() {
        @Override
        public Structure createFromParcel(Parcel in) {
            return new Structure(in);
        }

        @Override
        public Structure[] newArray(int size) {
            return new Structure[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(Date closingHours) {
        this.closingHours = closingHours;
    }

    public Date getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Date openingHours) {
        this.openingHours = openingHours;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Structure structure = (Structure) o;

        if (id != null ? !id.equals(structure.id) : structure.id != null) return false;
        if (name != null ? !name.equals(structure.name) : structure.name != null) return false;
        if (address != null ? !address.equals(structure.address) : structure.address != null)
            return false;
        if (imageLink != null ? !imageLink.equals(structure.imageLink) : structure.imageLink != null)
            return false;
        if (site != null ? !site.equals(structure.site) : structure.site != null) return false;
        if (email != null ? !email.equals(structure.email) : structure.email != null) return false;
        if (state != null ? !state.equals(structure.state) : structure.state != null) return false;
        if (city != null ? !city.equals(structure.city) : structure.city != null) return false;
        if (phone != null ? !phone.equals(structure.phone) : structure.phone != null) return false;
        if (type != structure.type) return false;
        if (priceMin != null ? !priceMin.equals(structure.priceMin) : structure.priceMin != null)
            return false;
        if (priceMax != null ? !priceMax.equals(structure.priceMax) : structure.priceMax != null)
            return false;
        if (latitude != null ? !latitude.equals(structure.latitude) : structure.latitude != null)
            return false;
        if (longitude != null ? !longitude.equals(structure.longitude) : structure.longitude != null)
            return false;
        if (closingHours != null ? !closingHours.equals(structure.closingHours) : structure.closingHours != null)
            return false;
        return openingHours != null ? openingHours.equals(structure.openingHours) : structure.openingHours == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (imageLink != null ? imageLink.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (priceMin != null ? priceMin.hashCode() : 0);
        result = 31 * result + (priceMax != null ? priceMax.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (closingHours != null ? closingHours.hashCode() : 0);
        result = 31 * result + (openingHours != null ? openingHours.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(imageLink);
        dest.writeString(site);
        dest.writeString(email);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(phone);
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
        if (avgRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(avgRating);
        }
    }
}
