package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.CourseListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.CourseItem;
import com.example.maedin.mohagee.item.PlaceItem;

import java.util.ArrayList;

public class CourseFragment extends Fragment {

    //ArrayList<CourseItem> courseList;
    ArrayList<PlaceItem> placeList = null;

    private ListView listView;
    private CourseListAdapter adapter;

    ViewPager swapView;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_course, container, false);

        if (placeList == null)
        {
            placeList = new ArrayList<>();
            CourseItem curr = ((App)getActivity().getApplication()).getPositionItem(0);
            placeList.addAll(curr.getPlaceList());
        }

        listView = (ListView) view.findViewById(R.id.course_detail_list);
        adapter = new CourseListAdapter(placeList);
        listView.setAdapter(adapter);

        swapView = (ViewPager) view.findViewById(R.id.swap_view);
        swapView.setAdapter(new pagerAdapter(getActivity().getSupportFragmentManager()));
        swapView.setCurrentItem(0);


        return view;
    }

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {

            if (position >= ((App)getActivity().getApplication()).getCourseListSize() || position < 0)
                return null;

            CourseItem curr = ((App)getActivity().getApplication()).getPositionItem(position);
            placeList.clear();
            placeList.addAll(curr.getPlaceList());

            Log.d("Log","getItem"+Integer.toString(position));

            CourseSwapViewFragment temp = new CourseSwapViewFragment();
            Bundle args = new Bundle();
            args.putString("START", placeList.get(0).getName());
            args.putString("END", placeList.get(placeList.size()-1).getName());
            args.putInt("N", placeList.size());
            temp.setArguments(args);

            return temp;
        }


        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);

            CourseItem curr = ((App)getActivity().getApplication()).getPositionItem(swapView.getCurrentItem());
            placeList.clear();
            placeList.addAll(curr.getPlaceList());
            adapter.notifyDataSetChanged();
        }


        @Override
        public int getCount()
        {
            int num = ((App)getActivity().getApplication()).getCourseListSize();
            return num;
        }
    }

}
