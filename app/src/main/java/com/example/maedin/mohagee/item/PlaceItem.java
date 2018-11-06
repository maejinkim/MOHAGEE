package com.example.maedin.mohagee.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PlaceItem implements Parcelable {

    private String id;
    private String name;
    private String category;
    private String theme;
    private String address;
    private String star;


    public PlaceItem()
    {

    }

    public PlaceItem(Parcel in)
    {
        id = in.readString();
        name =in.readString();
        category = in.readString();
        theme = in.readString();
    }

    public void setData(String id, String name, String category, String theme)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.theme = theme;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getTheme() {
        return theme;
    }

    public static final Creator<PlaceItem> CREATOR = new Creator<PlaceItem>() {
        @Override
        public PlaceItem createFromParcel(Parcel in) {
            return new PlaceItem(in);
        }

        @Override
        public PlaceItem[] newArray(int size) {
            return new PlaceItem[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(theme);
    }
}
