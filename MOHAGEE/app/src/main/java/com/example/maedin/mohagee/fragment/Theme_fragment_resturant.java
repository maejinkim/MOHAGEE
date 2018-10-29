package com.example.maedin.mohagee.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;

import com.example.maedin.mohagee.R;

public class Theme_fragment_resturant extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        String type = getArguments().getString("Type");
        switch (type)
        {
            case "cafe":
                view = inflater.inflate(R.layout.theme_fragment_layout_cafe, container, false);
                break;

            case "resturant":
                view = inflater.inflate(R.layout.theme_fragment_layout1, container, false);
                break;

        }

        return view;
    }
}
