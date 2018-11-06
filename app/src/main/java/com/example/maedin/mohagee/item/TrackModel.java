package com.example.maedin.mohagee.item;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class TrackModel {
    String description;
    double distance;
    double time;
    int turnType;
    ArrayList<LatLng> latLng;

    public TrackModel() {
        latLng = new ArrayList<>();
    }

    public void addLatLng(LatLng m_LatLng) {
        latLng.add(m_LatLng);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getTurnType() {
        return turnType;
    }

    public void setTurnType(int turnType) {
        this.turnType = turnType;
    }

    public ArrayList<LatLng> getLatLng() {
        return latLng;
    }

    public void setLatLng(ArrayList<LatLng> latLng) {
        this.latLng = latLng;
    }
}
