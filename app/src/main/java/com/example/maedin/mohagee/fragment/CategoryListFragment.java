package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.CategoryListAdapter;
import com.example.maedin.mohagee.adapter.CategoryResultAdapter;
import com.example.maedin.mohagee.adapter.CourseListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.CourseItem;
import com.example.maedin.mohagee.item.PlaceItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryListFragment extends Fragment {

    ArrayList<PlaceItem> placeList = null;

    TextView catNum;

    private ListView listView;
    private CategoryResultAdapter adapter;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category_list, container, false);
        if (placeList == null)
        {
            placeList = new ArrayList<>();

            Bundle bundle = getArguments();
            ArrayList<PlaceItem> list = bundle.getParcelableArrayList("list");
            placeList.addAll(list);
        }

        listView = (ListView) view.findViewById(R.id.cat_listview);
        adapter = new CategoryResultAdapter(placeList);
        listView.setAdapter(adapter);

        catNum = (TextView) view.findViewById(R.id.txt_cat_num);
        catNum.setText(Integer.toString(placeList.size()));

        return view;
    }
}
