package com.example.maedin.mohagee.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.maedin.mohagee.R;

public class InfoWindow {
    private Context context;
    private final View mWindow;



    public InfoWindow(Context ctx) {
        context = ctx;
        mWindow = LayoutInflater.from(ctx).inflate(R.layout.infowindow , null);
    }
    public Context getContext(){
        return context;
    }
}
