package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;

import java.util.ArrayList;
import java.util.Queue;

public class Theme_fragment_cafe extends Fragment implements View.OnClickListener{
    View view;
    Button feel_button, view_button, vintage_button, brunch_button, different_button, quiet_button, qute_button;


    String small_class = "";
    ArrayList<Button> theme_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        String type = getArguments().getString("Type");
        switch (type) {
            case "cafe":
                view = inflater.inflate(R.layout.theme_fragment_layout_cafe, container, false);
                feel_button = (Button) view.findViewById(R.id.feel_button);
                feel_button.setOnClickListener(this);

                view_button = (Button) view.findViewById(R.id.background_button);
                view_button.setOnClickListener(this);

                vintage_button = (Button) view.findViewById(R.id.vintage_button);
                vintage_button.setOnClickListener(this);

                brunch_button = (Button) view.findViewById(R.id.brunch_button);
                brunch_button.setOnClickListener(this);

                different_button = (Button) view.findViewById(R.id.different_button);
                different_button.setOnClickListener(this);

                quiet_button = (Button) view.findViewById(R.id.quite_button);
                quiet_button.setOnClickListener(this);

                qute_button = (Button) view.findViewById(R.id.qute_button);
                qute_button.setOnClickListener(this);

                theme_list = new ArrayList<>();
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
            case R.id.feel_button:

                if(!b.isSelected()) {
                    if(theme_list.size() == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    b.setSelected(true);
                    theme_list.add(b);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    for(int i =0 ; i<theme_list.size() ; i++)
                    {
                        if(theme_list.get(i).getId() == v.getId())
                        {
                            theme_list.remove(i);
                            break;
                        }
                    }
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.background_button:

                if(!b.isSelected()) {
                    if(theme_list.size() == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    b.setSelected(true);
                    theme_list.add(b);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    for(int i =0 ; i<theme_list.size() ; i++)
                    {
                        if(theme_list.get(i).getId() == v.getId())
                        {
                            theme_list.remove(i);
                            break;
                        }
                    }
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.vintage_button:

                if(!b.isSelected()) {
                    if(theme_list.size() == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    b.setSelected(true);
                    theme_list.add(b);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    for(int i =0 ; i<theme_list.size() ; i++)
                    {
                        if(theme_list.get(i).getId() == v.getId())
                        {
                            theme_list.remove(i);
                            break;
                        }
                    }
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.brunch_button:

                if(!b.isSelected()) {
                    if(theme_list.size() == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    b.setSelected(true);
                    theme_list.add(b);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    for(int i =0 ; i<theme_list.size() ; i++)
                    {
                        if(theme_list.get(i).getId() == v.getId())
                        {
                            theme_list.remove(i);
                            break;
                        }
                    }
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.different_button:

                if(!b.isSelected()) {
                    if(theme_list.size() == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    b.setSelected(true);
                    theme_list.add(b);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    for(int i =0 ; i<theme_list.size() ; i++)
                    {
                        if(theme_list.get(i).getId() == v.getId())
                        {
                            theme_list.remove(i);
                            break;
                        }
                    }
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.quite_button:

                if(!b.isSelected()) {
                    if(theme_list.size() == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    b.setSelected(true);
                    theme_list.add(b);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    for(int i =0 ; i<theme_list.size() ; i++)
                    {
                        if(theme_list.get(i).getId() == v.getId())
                        {
                            theme_list.remove(i);
                            break;
                        }
                    }
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

            case R.id.qute_button:

                if(!b.isSelected()) {
                    if(theme_list.size() == 2)
                    {
                        Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "더 이상 테마를 선택 할 수 없습니다!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    b.setSelected(true);
                    theme_list.add(b);
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).addtheme(b.getText().toString());
                }
                else
                {
                    b.setSelected(false);
                    for(int i =0 ; i<theme_list.size() ; i++)
                    {
                        if(theme_list.get(i).getId() == v.getId())
                        {
                            theme_list.remove(i);
                            break;
                        }
                    }
                    ((SearchFragment)((MainActivity) getActivity()).getSupportFragmentManager()
                            .findFragmentByTag("search_fragment")).deletetheme(b.getText().toString());
                }

                break;

        }


    }
}
