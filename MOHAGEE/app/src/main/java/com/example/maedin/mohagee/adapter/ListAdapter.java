package com.example.maedin.mohagee.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.SearchActivity;
import com.example.maedin.mohagee.activity.search_result_Activity;
import com.example.maedin.mohagee.item.result_item_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ArrayList<JSONObject> > items = null;
    private int count = 0;
    private Context context;
    private double Lat;
    private double Lng;

    public ListAdapter(ArrayList<ArrayList<JSONObject> > _oData, Context context, int size, double Lat, double Lng)
    {
        items = _oData;
        count = size;
        this.context = context;
        this.Lat = Lat;
        this.Lng = Lng;
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.result_item_layout, parent, false);
        }

        TextView oTextTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.textDate);
        Button button = (Button)convertView.findViewById(R.id.search_road_button1);

        String text = "";


        try {
            for(int i = 0 ; i<items.get(position).size() ; i++) {
                if (i != items.get(position).size() - 1) {
                    text += items.get(position).get(i).getString("loc_name") + " -> ";
                } else {
                    text += items.get(position).get(i).getString("loc_name");
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }


        //다 프로세싱한 데이터를 Listview item에 넣는 부분.
        String temp = ((Integer)(position+1)).toString();
        oTextTitle.setText("스케줄 " + temp);
        oTextDate.setText(text);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                try {
                    JSONArray newsend = new JSONArray();
                    JSONObject temp = new JSONObject();
                    temp.put("Lat",Lat);
                    temp.put("Lng", Lng);
                    for(int i = 0 ; i<items.get(position).size() ; i++)
                    {
                        newsend.put(items.get(position).get(i));
                    }
                    newsend.put(temp);
                    JSONObject json = new JSONObject();
                    json.put("locations", newsend);
                    Intent intent = new Intent(context, SearchActivity.class);
                    intent.putExtra("locations", json.toString());
                    context.startActivity(intent);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        });

        return convertView;
    }


}
