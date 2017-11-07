package org.lulz.jrat.model.impl;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class RatSighting implements Parcelable {
    private Date date;
    private GeoPoint location;
    private String locationType;
    private String zip;
    private String address;
    private String city;
    private String borough;

    public RatSighting() {
        // Needed for Firebase
    }

    public RatSighting(Date date, GeoPoint location, String locationType,
                       String zip, String address, String city, String borough) {
        this.date = date;
        this.location = location;
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
    }

    protected RatSighting(Parcel in) {
        date = new Date(in.readLong());
        location = new GeoPoint(in.readDouble(), in.readDouble());
        locationType = in.readString();
        zip = in.readString();
        address = in.readString();
        city = in.readString();
        borough = in.readString();
    }

    public static final Creator<RatSighting> CREATOR = new Creator<RatSighting>() {
        @Override
        public RatSighting createFromParcel(Parcel in) {
            return new RatSighting(in);
        }

        @Override
        public RatSighting[] newArray(int size) {
            return new RatSighting[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (date == null) {
            parcel.writeLong(0);
        } else {
            parcel.writeLong(date.getTime());
        }
        if (location == null) {
            parcel.writeDouble(0);
            parcel.writeDouble(0);
        } else {
            parcel.writeDouble(location.getLatitude());
            parcel.writeDouble(location.getLongitude());
        }
        parcel.writeString(locationType);
        parcel.writeString(zip);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(borough);
    }
}
