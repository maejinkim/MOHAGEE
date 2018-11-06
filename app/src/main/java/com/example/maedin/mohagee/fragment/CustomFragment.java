package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.adapter.CourseListAdapter;
import com.example.maedin.mohagee.adapter.CustomListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.CourseItem;
import com.example.maedin.mohagee.item.PlaceItem;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomFragment extends Fragment implements View.OnClickListener{

    View view;
    ArrayList<PlaceItem> placeList = null;

    private ListView listView;
    private CustomListAdapter adapter;

    private ServerThread serverThread = ServerThread.getInstance();
    private String myResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_custom, container, false);

        serverThread.setFgHandler(mHandler);


        getCustomList();

//        placeList = new ArrayList<>();
//        PlaceItem temp = new PlaceItem();
//        temp.setId("loc_id");
//        temp.setName("loc_name");
//        temp.setCategory("small_ctg");
//        temp.setTheme("#theme1#theme2#theme3");
//        placeList.add(temp);

        listView = (ListView) view.findViewById(R.id.custom_list);
        if(placeList!=null) {
            adapter = new CustomListAdapter(placeList);
            listView.setAdapter(adapter);
        }

        return view;
    }



    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj.toString().equals(""))//즐찾한 장소가 하나도 없으면
            {
                Log.d("Custom", "didn't find any place");

            } else {
                //로그로 확인
                Log.d("Custom", msg.obj.toString());
                placeList = new ArrayList<>();

                try {

                    JSONObject object = new JSONObject(msg.obj.toString());
                    JSONArray array = new JSONArray(object.getString("bookmarks"));
                    for(int i=0; i < array.length(); i++){
                        try {
                            JSONObject jObject = array.getJSONObject(i);  // JSONObject 추출
                            PlaceItem temp = new PlaceItem();
                            temp.setId(jObject.getString("loc_id"));
                            temp.setName(jObject.getString("loc_name"));
                            temp.setCategory(jObject.getString("small_ctg"));
                            if(jObject.getString("theme1")=="null")
                            {
                                temp.setTheme("#"+"저렴"+" #"+"분위기"+" #"+"감성");

                            }
                            else
                            {
                                temp.setTheme("#"+jObject.getString("theme1")+" #"+jObject.getString("theme2")+" #"+jObject.getString("theme3"));

                            }
                            placeList.add(temp);

                        }catch (JSONException e)
                        {

                        }
                    }
                    if(array.length() != 0) {
                        adapter = new CustomListAdapter(placeList);
                        listView.setAdapter(adapter);
                    }
                }
                catch (JSONException e)
                {

                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }

    private void getCustomList()
    {
        Message msg = new Message();
        msg.what = 6;
        serverThread.setCustomList(((App)getActivity().getApplication()).getUser().getId(),myResult);
        serverThread.getFgHandler().sendMessage(msg);
    }
}
