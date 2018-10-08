package com.example.maedin.mohagee.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.SignInActivity;

public class SearchFragment extends Fragment implements View.OnClickListener {

    View view;
    Button where_button, when_button, solo, with_friend, with_parent, doing_date, with_children, resturant, cafe, billiard;
    Button bowling, pc_room, room_escape, exhibition, theater, cinema, park, shopping;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);

        where_button = (Button)view.findViewById(R.id.where_button);
        where_button.setOnClickListener(this);

        when_button = (Button)view.findViewById(R.id.when_button);
        when_button.setOnClickListener(this);

        solo = (Button)view.findViewById(R.id.solo);
        solo.setOnClickListener(this);

        with_friend = (Button)view.findViewById(R.id.with_friend);
        with_friend.setOnClickListener(this);

        with_parent = (Button)view.findViewById(R.id.with_parent);
        with_parent.setOnClickListener(this);

        doing_date = (Button)view.findViewById(R.id.doing_date);
        doing_date.setOnClickListener(this);

        with_children = (Button)view.findViewById(R.id.with_children);
        with_children.setOnClickListener(this);

        resturant = (Button)view.findViewById(R.id.resturant);
        resturant.setOnClickListener(this);

        cafe = (Button)view.findViewById(R.id.cafe);
        cafe.setOnClickListener(this);

        billiard = (Button)view.findViewById(R.id.billiard);
        billiard.setOnClickListener(this);

        bowling = (Button)view.findViewById(R.id.bowling);
        bowling.setOnClickListener(this);

        pc_room = (Button)view.findViewById(R.id.pc_room);
        pc_room.setOnClickListener(this);

        room_escape = (Button)view.findViewById(R.id.room_escape);
        room_escape.setOnClickListener(this);

        exhibition = (Button)view.findViewById(R.id.exhibition);
        exhibition.setOnClickListener(this);

        theater = (Button)view.findViewById(R.id.theater);
        theater.setOnClickListener(this);

        cinema = (Button)view.findViewById(R.id.cinema);
        cinema.setOnClickListener(this);

        park = (Button)view.findViewById(R.id.park);
        park.setOnClickListener(this);

        shopping = (Button)view.findViewById(R.id.shopping);
        shopping.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v)
    {
        Button b;
        b = v.findViewById(v.getId());
        if(!b.isSelected()) {
            b.setSelected(true);
        }
        else
        {
            b.setSelected(false);
        }
        switch (v.getId())
        {
            case R.id.where_button:
            {
                break;
            }

            case R.id.when_button:
            {
                break;
            }
            case R.id.solo:
            {
                break;
            }
            case R.id.with_friend:
            {
                break;
            }
            case R.id.with_parent:
            {
                break;
            }
            case R.id.doing_date:
            {
                break;
            }
            case R.id.with_children:
            {
                break;
            }
            case R.id.resturant:
            {
                break;
            }
            case R.id.cafe:
            {
                break;
            }
            case R.id.billiard:
            {
                break;
            }
            case R.id.bowling:
            {
                break;
            }
            case R.id.pc_room:
            {
                break;
            }
            case R.id.room_escape:
            {
                break;
            }
            case R.id.exhibition:
            {
                break;
            }
            case R.id.theater:
            {
                break;
            }
            case R.id.cinema:
            {
                break;
            }
            case R.id.park:
            {
                break;
            }
            case R.id.shopping:
            {
                break;
            }

        }
    }
}
