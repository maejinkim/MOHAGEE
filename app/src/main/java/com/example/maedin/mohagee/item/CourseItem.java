package com.example.maedin.mohagee.item;

import java.util.ArrayList;

public class CourseItem {

    private ArrayList<PlaceItem> placeList;

    public CourseItem()
    {
        placeList = new ArrayList<>();
    }

    public CourseItem(ArrayList<PlaceItem> placeList)
    {
        this.placeList = placeList;
    }

    public void addPlace(PlaceItem place)
    {
        placeList.add(place);
    }

    public void delete(PlaceItem place)
    {
        placeList.remove(place);
    }

    public ArrayList<PlaceItem> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(ArrayList<PlaceItem> placeList) {
        this.placeList = placeList;
    }
}
