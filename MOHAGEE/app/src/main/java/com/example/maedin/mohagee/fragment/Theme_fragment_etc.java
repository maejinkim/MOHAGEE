package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;

public class Theme_fragment_etc extends Fragment implements View.OnClickListener{
    View view;
    Button park,shopping;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        String type = getArguments().getString("Type");
        switch (type)
        {
            case "etc":
                view = inflater.inflate(R.layout.theme_fragment_layout_etc,container, false);
                park = (Button)view.findViewById(R.id.park_button);
                park.setOnClickListener(this);

                shopping = (Button)view.findViewById(R.id.shopping_button);
                shopping.setOnClickListener(this);

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
            case R.id.park_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    shopping.setSelected(false);
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

            case R.id.shopping_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    park.setSelected(false);
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
