package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.fragment.map_fragment;
import com.google.android.gms.maps.SupportMapFragment;

public class HomeFragment extends Fragment {

    View view;
    Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frgment_home, container, false);
        button = (Button)view.findViewById(R.id.map_button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("test", "onclick enter");
                switch(v.getId())
                {
                    case R.id.map_button:
                        Log.d("test", "button enter");
                        ((MainActivity)getActivity()).changeFragment(new map_fragment());

                }
            }
        });
        return view;
    }

}
