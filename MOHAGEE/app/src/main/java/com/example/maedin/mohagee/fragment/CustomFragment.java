package com.example.maedin.mohagee.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
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

    boolean allchecked = false;

    Button btnResult, btnAll, btnAllDelete, btnDelete;

    private ListView listView;
    private CustomListAdapter adapter;
    private TextView txtInfo;

    private ServerThread serverThread = ServerThread.getInstance();
    private String myResult;

    Double Lat, Lng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_custom, container, false);

        btnResult = view.findViewById(R.id.btn_custom_result);
        btnAll = view.findViewById(R.id.custom_all);
        btnAllDelete = view.findViewById(R.id.custom_all_delete);
        btnDelete = view.findViewById(R.id.custom_selete_delete);
        txtInfo = view.findViewById(R.id.txt_custom_info);


        btnResult.setOnClickListener(this);
        btnAll.setOnClickListener(this);
        btnAllDelete.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        serverThread.setFgHandler(mHandler);

        listView = (ListView) view.findViewById(R.id.custom_list);


        getCustomList();

        return view;
    }


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
            case R.id.custom_selete_delete:
                checkList = adapter.getCheckList();
                if(checkList.size() == 0)
                {
                    Toast.makeText(getContext(), "선택한 장소가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialogDelete();
                }
                break;
            case R.id.custom_all_delete:
                dialogAllDelete();
                break;
            case R.id.custom_all:
                if (!allchecked)
                {
                    btnAll.setText("전체선택 해제");
                    allchecked = true;
                }
                else
                {
                    btnAll.setText("전체선택");
                    allchecked = false;
                }

                adapter.setAllChecked(allchecked);
                adapter.notifyDataSetChanged();

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

    private void dialogDelete()
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
        dlg.setTitle("리스트에서 삭제");
        dlg.setMessage("선택한 장소를 리스트에서 삭제하시겠습니까?");
        dlg.setCancelable(false);
        dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Message msg = new Message();
                msg.what = 10;
                String loc_id = "";

                for(int i = 0; i < checkList.size(); i++)
                {
                    if (i > 0)
                        loc_id+=",";

                    loc_id+=checkList.get(i).getId();
                }
                serverThread.setDeleteCustom(((App)getActivity().getApplication()).getUser().getId(),loc_id,myResult);
                serverThread.getFgHandler().sendMessage(msg);
            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
    }

    private void dialogAllDelete()
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
        dlg.setTitle("리스트에서 전체 삭제");
        dlg.setMessage("모든 장소를 리스트에서 삭제하시겠습니까?");
        dlg.setCancelable(false);
        dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Message msg = new Message();
                msg.what = 9;
                serverThread.setAllDeleteCustom(((App)getActivity().getApplication()).getUser().getId(),myResult);
                serverThread.getFgHandler().sendMessage(msg);
            }
        });
        dlg.setNegativeButton("취소", null);
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
                temp.put("loc_addr",checkList.get(i).getAddress());
                temp.put("loc_time",checkList.get(i).getTime());
                temp.put("loc_name",checkList.get(i).getName());
                temp.put("big_ctg",checkList.get(i).getBig_cat());
                temp.put("small_ctg",checkList.get(i).getCategory());
                temp.put("star",checkList.get(i).getStar());
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

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj.toString().equals("delete Bookmarks success!\n"))
            {
                getCustomList();
            }
            else if (msg.obj.toString().equals("delete All Bookmark success!\n"))
            {
                getCustomList();
            }
            else {
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
                            temp.setTime(jObject.getString("loc_time"));
                            temp.setBig_cat(jObject.getString("big_ctg"));
                            temp.setAddress(jObject.getString("loc_addr"));
                            temp.setLat(jObject.getString("latitude"));
                            temp.setLng(jObject.getString("longitude"));
                            temp.setStar(jObject.getString("star"));
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
                    adapter = new CustomListAdapter(placeList);
                    listView.setAdapter(adapter);
                    if (placeList.size() == 0)
                        txtInfo.setVisibility(View.VISIBLE);
                }
                catch (JSONException e)
                {

                }
            }
        }
    };
}
