package com.example.maedin.mohagee.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.widget.Button;

import com.example.maedin.mohagee.R;

public class Theme_fragment_resturant extends Fragment implements View.OnClickListener {
    View view;
    Button korea_resturant, china_resturant, japan_resturant, snack_resturant, west_resturant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        String type = getArguments().getString("Type");
        switch (type)
        {
            case "resturant":
                view = inflater.inflate(R.layout.theme_fragment_layout1, container, false);
                korea_resturant = (Button)view.findViewById(R.id.koreafood_button);
                korea_resturant.setOnClickListener(this);

                china_resturant = (Button)view.findViewById(R.id.chinafood_button);
                china_resturant.setOnClickListener(this);

                japan_resturant = (Button)view.findViewById(R.id.japanfood_button);
                japan_resturant.setOnClickListener(this);

                snack_resturant = (Button)view.findViewById(R.id.snack_button);
                snack_resturant.setOnClickListener(this);

                west_resturant = (Button)view.findViewById(R.id.westfood_button);
                west_resturant.setOnClickListener(this);
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
            case R.id.koreafood_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                }
                else
                {
                    b.setSelected(false);
                }

                break;

            case R.id.chinafood_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                }
                else
                {
                    b.setSelected(false);
                }

                break;

            case R.id.westfood_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                }
                else
                {
                    b.setSelected(false);
                }

                break;

            case R.id.snack_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                }
                else
                {
                    b.setSelected(false);
                }

                break;

            case R.id.japanfood_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                }
                else
                {
                    b.setSelected(false);
                }

                break;
                
        }
    }

}
