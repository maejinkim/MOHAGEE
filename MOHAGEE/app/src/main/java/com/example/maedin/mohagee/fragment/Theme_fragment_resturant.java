package com.example.maedin.mohagee.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;

import java.util.ArrayList;

public class Theme_fragment_resturant extends Fragment implements View.OnClickListener {

    Integer count =0;
    View view;
    Button korea_resturant, china_resturant, japan_resturant, snack_resturant, west_resturant;
    Button deli_button, cheap_button, clean_button, kind_button, view_button, dif_button, many_button;

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

                deli_button = (Button)view.findViewById(R.id.delicious_button);
                deli_button.setOnClickListener(this);

                cheap_button = (Button)view.findViewById(R.id.chip_button);
                cheap_button.setOnClickListener(this);

                clean_button = (Button)view.findViewById(R.id.clean_button);
                clean_button.setOnClickListener(this);

                kind_button = (Button)view.findViewById(R.id.kind_button);
                kind_button.setOnClickListener(this);

                view_button = (Button)view.findViewById(R.id.background_button_resturant);
                view_button.setOnClickListener(this);

                dif_button = (Button)view.findViewById(R.id.different_button_resturant);
                dif_button.setOnClickListener(this);

                many_button = (Button)view.findViewById(R.id.many_button);
                many_button.setOnClickListener(this);

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
                    china_resturant.setSelected(false);
                    japan_resturant.setSelected(false);
                    west_resturant.setSelected(false);
                    snack_resturant.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class("");
                }

                break;

            case R.id.chinafood_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    korea_resturant.setSelected(false);
                    japan_resturant.setSelected(false);
                    west_resturant.setSelected(false);
                    snack_resturant.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }

                break;

            case R.id.westfood_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    china_resturant.setSelected(false);
                    japan_resturant.setSelected(false);
                    korea_resturant.setSelected(false);
                    snack_resturant.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }

                break;

            case R.id.snack_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    china_resturant.setSelected(false);
                    japan_resturant.setSelected(false);
                    west_resturant.setSelected(false);
                    korea_resturant.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }

                break;

            case R.id.japanfood_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    china_resturant.setSelected(false);
                    korea_resturant.setSelected(false);
                    west_resturant.setSelected(false);
                    snack_resturant.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).setSmall_class(b.getText().toString());
                }

                break;

            case R.id.delicious_button:
                if(!b.isSelected()) {
                    if(count == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    b.setSelected(true);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                    count++;
                }
                else
                {
                    b.setSelected(false);
                    count--;
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }
                break;

            case R.id.chip_button:
                if(!b.isSelected()) {
                    if(count == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    b.setSelected(true);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                    count++;
                }
                else
                {
                    b.setSelected(false);
                    count--;
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }
                break;

            case R.id.clean_button:
                if(!b.isSelected()) {
                    if(count == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    b.setSelected(true);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                    count++;
                }
                else
                {
                    b.setSelected(false);
                    count--;
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }
                break;


            case R.id.kind_button:
                if(!b.isSelected()) {
                    if(count == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    b.setSelected(true);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                    count++;
                }
                else
                {
                    b.setSelected(false);
                    count--;
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }
                break;

            case R.id.background_button_resturant:
                if(!b.isSelected()) {
                    if(count == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    b.setSelected(true);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                    count++;
                }
                else
                {
                    b.setSelected(false);
                    count--;
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }
                break;

            case R.id.different_button_resturant:
                if(!b.isSelected()) {
                    if(count == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    b.setSelected(true);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                    count++;
                }
                else
                {
                    b.setSelected(false);
                    count--;
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }
                break;

            case R.id.many_button:
                if(!b.isSelected()) {
                    if(count == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    b.setSelected(true);

                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                    count++;
                }
                else
                {
                    b.setSelected(false);
                    count--;
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }
                break;
        }
    }

}
