package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.google.android.gms.maps.MapView;

public class Theme_fragment_culture extends Fragment implements View.OnClickListener{
    View view;

    Button exhibition, theater, cinema;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        String type = getArguments().getString("Type");
        switch (type)
        {
            case "culture":
                view = inflater.inflate(R.layout.theme_fragment_layout_culture,container, false);
                exhibition = (Button)view.findViewById(R.id.Exhibition_button);
                exhibition.setOnClickListener(this);

                theater = (Button)view.findViewById(R.id.theater_button);
                theater.setOnClickListener(this);

                cinema = (Button)view.findViewById(R.id.movie_button);
                cinema.setOnClickListener(this);

                break;

        }
        return view;
    }

    @Override
    public void onClick(View v)
    {
        Button b;
        b = v.findViewById(v.getId());

        switch (v.getId())
        {
            case R.id.Exhibition_button:

                if(!b.isSelected()) {
                    b.setSelected(true);
                    theater.setSelected(false);
                    cinema.setSelected(false);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.theater_button:

                if(!b.isSelected()) {
                    b.setSelected(true);
                    exhibition.setSelected(false);
                    cinema.setSelected(false);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.movie_button:

                if(!b.isSelected()) {
                    b.setSelected(true);
                    theater.setSelected(false);
                    exhibition.setSelected(false);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;
        }
    }

}
