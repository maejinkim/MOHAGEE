package com.example.maedin.mohagee.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.maedin.mohagee.R;

import java.util.ArrayList;

public class CategoryListAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> mainList = null;
    private ArrayList<ArrayList<String>> smallList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public CategoryListAdapter(Context c, ArrayList<String> mainList,
                                 ArrayList<ArrayList<String>> smallList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.mainList = mainList;
        this.smallList = smallList;
    }

    // 그룹 포지션을 반환한다.
    @Override
    public String getGroup(int groupPosition) {
        return mainList.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return mainList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.listview_main_category, parent, false);
            viewHolder.main_name = (TextView) v.findViewById(R.id.txt_cat_main);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)v.getTag();
        }

//        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
//        if(isExpanded){
//            viewHolder.iv_image.setBackgroundColor(Color.GREEN);
//        }else{
//            viewHolder.iv_image.setBackgroundColor(Color.WHITE);
//        }
//
        viewHolder.main_name.setText(getGroup(groupPosition));

        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return smallList.get(groupPosition).get(childPosition);
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return smallList.get(groupPosition).size();
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.listview_small_category, null);
            viewHolder.small_name = (TextView) v.findViewById(R.id.txt_cat_small);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.small_name.setText(getChild(groupPosition, childPosition));

        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public TextView main_name;
        public TextView small_name;
    }

}
