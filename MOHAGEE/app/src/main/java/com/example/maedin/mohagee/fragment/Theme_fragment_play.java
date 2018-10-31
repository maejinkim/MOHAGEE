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

public class Theme_fragment_play extends Fragment implements View.OnClickListener{
    View view;

    Button billiard, bowling, pc_room, room_escape, singing_room;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        String type = getArguments().getString("Type");
        switch (type)
        {
            case "play":
                view= inflater.inflate(R.layout.theme_fragment_layout_play, container, false);

                billiard = (Button)view.findViewById(R.id.billiard_button);
                billiard.setOnClickListener(this);

                bowling = (Button)view.findViewById(R.id.bowling_button);
                bowling.setOnClickListener(this);

                pc_room = (Button)view.findViewById(R.id.pc_room_button);
                pc_room.setOnClickListener(this);

                room_escape = (Button)view.findViewById(R.id.escape_room_button);
                room_escape.setOnClickListener(this);

                singing_room = (Button)view.findViewById(R.id.singing_room);
                singing_room.setOnClickListener(this);

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
            case R.id.billiard_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    bowling.setSelected(false);
                    pc_room.setSelected(false);
                    room_escape.setSelected(false);
                    singing_room.setSelected(false);
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

            case R.id.bowling_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    billiard.setSelected(false);
                    pc_room.setSelected(false);
                    room_escape.setSelected(false);
                    singing_room.setSelected(false);
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

            case R.id.escape_room_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    bowling.setSelected(false);
                    pc_room.setSelected(false);
                    billiard.setSelected(false);
                    singing_room.setSelected(false);
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

            case R.id.pc_room_button:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    bowling.setSelected(false);
                    billiard.setSelected(false);
                    room_escape.setSelected(false);
                    singing_room.setSelected(false);
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

            case R.id.singing_room:
                if(!b.isSelected()) {
                    b.setSelected(true);
                    bowling.setSelected(false);
                    pc_room.setSelected(false);
                    room_escape.setSelected(false);
                    billiard.setSelected(false);
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
