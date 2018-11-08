package com.example.maedin.mohagee.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.ListAdapter;
import com.example.maedin.mohagee.item.result_item_data;
import com.google.android.gms.common.internal.constants.ListAppsActivityContract;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class search_result_Activity extends FragmentActivity {
    Double Lat, Lng;
    Button button;
    ArrayList<String> get;            //스캐줄의 전체
    ListView mlistview= null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.result_layout);
        get = (ArrayList<String>) intent.getStringArrayListExtra("locations");

        ArrayList<ArrayList<JSONObject>> send = new ArrayList<>();
         Log.d( "JSON_check",get.get(0).substring(13));
         try {
             JSONArray temp = new JSONArray(get.get(0).substring(13));

             for (int i = 0; i < temp.length(); i++) {
                 ArrayList<JSONObject> sendobj = new ArrayList<>();
                 JSONArray frag;

                 Lat = Double.valueOf(get.get(get.size()-2));
                 Lng = Double.valueOf(get.get(get.size()-1));
                 for (int k = 0; k < get.size()-2; k++) {
                     frag = new JSONArray(get.get(k).substring(13));
                     sendobj.add(frag.getJSONObject(i));
                 }
                 send.add(sendobj);
             }
         } catch (JSONException e)
         {
             e.printStackTrace();
         }


            mlistview = (ListView) findViewById(R.id.listview);
            ListAdapter adapter = new ListAdapter(send, this.getApplicationContext(), send.size(), Lat, Lng);
            mlistview.setAdapter(adapter);
        button = (Button) findViewById(R.id.search_button_result);
        button.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }

        });
    }

}
