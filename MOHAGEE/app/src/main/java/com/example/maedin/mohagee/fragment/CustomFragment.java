package com.example.maedin.mohagee.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.activity.SearchActivity;
import com.example.maedin.mohagee.activity.Select_Location_Activity;
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
    ArrayList<PlaceItem> checkList = null;

    Button btnResult;

    private ListView listView;
    private CustomListAdapter adapter;

    private ServerThread serverThread = ServerThread.getInstance();
    private String myResult;

    Double Lat, Lng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_custom, container, false);

        btnResult = view.findViewById(R.id.btn_custom_result);
        btnResult.setOnClickListener(this);

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
                            temp.setLat(jObject.getString("latitude"));
                            temp.setLng(jObject.getString("longitude"));
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
            case R.id.btn_custom_result:
                checkList = adapter.getCheckList();
                if(checkList.size() == 0)
                {
                    Toast.makeText(getContext(), "선택한 장소가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialogSelect();
                }
                break;

        }
    }

    private void dialogSelect()
    {
        final LinearLayout layout = (LinearLayout) View.inflate(getContext(),R.layout.dialog_custom_select, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
        dlg.setTitle("시작 위치를 설정합니다.");
        dlg.setCancelable(false);
        dlg.setView(layout);
        Button btnDefault = layout.findViewById(R.id.btn_custom_default);
        btnDefault.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Lat = 37.4980744;
                Lng = 127.0252673;
                getResult();
            }
        });
        Button btnSelect = layout.findViewById(R.id.btn_custom_select);
        btnSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), Select_Location_Activity.class), 0);
            }
        });
        dlg.setPositiveButton("취소", null);
        dlg.show();
    }

    private void getResult()
    {
        try{
            JSONArray newsend = new JSONArray();

            for(int i = 0 ; i<checkList.size() ; i++)
            {
                JSONObject temp = new JSONObject();
                temp.put("latitude",checkList.get(i).getLat());
                temp.put("longitude",checkList.get(i).getLng());
                newsend.put(temp);
            }

            JSONObject temp = new JSONObject();
            temp.put("Lat",Double.toString(Lat));
            temp.put("Lng", Double.toString(Lng));
            newsend.put(temp);
            JSONObject json = new JSONObject();
            json.put("locations", newsend);
            Intent intent = new Intent(getContext(), SearchActivity.class);
            intent.putExtra("locations", json.toString());
            getContext().startActivity(intent);

        }catch (JSONException e)
        {

        }
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        switch(requestcode)
        {
            case 0:
                Lat = data.getExtras().getDouble("Lat");
                Lng = data.getExtras().getDouble("Lng");
                getResult();
                break;
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
