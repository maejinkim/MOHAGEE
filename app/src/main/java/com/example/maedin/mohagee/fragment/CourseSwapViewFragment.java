package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maedin.mohagee.R;

import org.w3c.dom.Text;

public class CourseSwapViewFragment extends Fragment {

    View view;
    TextView txtStart;
    TextView txtEnd;
    TextView txtNum;


    String start;
    String end;
    int num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_swapview,container,false);

        if (getArguments() != null) {
            start = getArguments().getString("START");
            end = getArguments().getString("END");
            num = getArguments().getInt("N");
        }

        txtStart = view.findViewById(R.id.course_swapview_start);
        txtEnd = view.findViewById(R.id.course_swapview_end);
        txtNum = view.findViewById(R.id.course_swapview_num);
        txtStart.setText(start);
        txtEnd.setText(end);
        txtNum.setText(Integer.toString(num));

        return view;
    }
}
