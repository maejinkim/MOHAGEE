package com.example.maedin.mohagee.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.activity.Select_Location_Activity;
import com.example.maedin.mohagee.activity.SignInActivity;
import com.example.maedin.mohagee.adapter.CategoryListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.PlaceItem;
import com.example.maedin.mohagee.thread.ServerThread;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryFragment extends Fragment{

    TextView txtAddress;
    TextView txtBoundary;
    Button btnAddress;
    Button btnBoundary;

    ExpandableListView category;
    ArrayList<String> mainList = null;
    ArrayList<ArrayList<String>> smallList = null;

    //강남역 기준 테스트
    Double lat = 37.4980744;
    Double lon = 127.0252673;
    String address = "";
    String ragne = "500";

    private ServerThread serverThread=ServerThread.getInstance();
    private String myResult;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);

        category = (ExpandableListView) view.findViewById(R.id.list_category);
        txtAddress = (TextView) view.findViewById(R.id.txt_cat_address);
        txtBoundary = (TextView) view.findViewById(R.id.txt_cat_boundary);
        btnAddress = (Button) view.findViewById(R.id.btn_cat_address);
        btnBoundary = (Button) view.findViewById(R.id.btn_cat_boundary);

        serverThread.setFgHandler(mHandler);


        mainList = ((App)getActivity().getApplication()).getMainCategoryList();
        smallList = ((App)getActivity().getApplication()).getSmallCategoryList();

        category.setAdapter(new CategoryListAdapter(getContext(), mainList, smallList));

        btnBoundary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getBoundary();
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getAddress();
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        category.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //통신결과는 로그창에서 확인
                ArrayList<String> selectList = smallList.get(groupPosition);
                String select = selectList.get(childPosition);
                getCategoryList(select);
//
                return false;
            }
        });

        return view;
    }


    private void getAddress()
    {
        startActivityForResult(new Intent(getActivity(), Select_Location_Activity.class), 0);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        switch(requestcode)
        {
            case 0:
                lat = data.getExtras().getDouble("Lat");
                lon = data.getExtras().getDouble("Lng");
                address = data.getExtras().getString("Addr");
                txtAddress.setText(address);
                break;
        }
    }

    private void getBoundary()
    {
        final LinearLayout layout = (LinearLayout) View.inflate(getContext(), R.layout.dialog_input_boundary, null);

        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
        dlg.setTitle("범위를 입력해주세요!");
        dlg.setView(layout);
        dlg.setCancelable(false);
        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editBoundary = (EditText) layout.findViewById(R.id.edit_boundary);
                ragne = editBoundary.getText().toString();
                if (ragne.equals(""))
                {
                    Toast.makeText(getActivity(), "잘못된 입력입니다.", Toast.LENGTH_SHORT ).show();
                }
                else
                    txtBoundary.setText(ragne+"M");
            }
        });
        dlg.show();
    }

    private void getCategoryList(String cat)
    {
        String category = cat;

        serverThread.setCategory(Double.toString(lat),Double.toString(lon),ragne,category,myResult);
        serverThread.getFgHandler().sendEmptyMessage(5);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj.toString().equals(""))//장소가 하나도 없으면
            {
                Log.d("Category", "didn't find any place");

            } else {
                //로그로 확인

                ArrayList<PlaceItem> list = new ArrayList<>();

                Log.d("Category", msg.obj.toString());
                try {

                    JSONObject object = new JSONObject(msg.obj.toString());
                    JSONArray array = new JSONArray(object.getString("locations"));
                    for(int i=0; i < array.length(); i++){
                        try {
                            JSONObject jObject = array.getJSONObject(i);  // JSONObject 추출
                            PlaceItem temp = new PlaceItem();
                            temp.setId(jObject.getString("loc_id"));
                            temp.setStar(jObject.getString("star"));
                            temp.setAddress( jObject.getString("loc_addr"));
                            temp.setName(jObject.getString("loc_name"));
                            temp.setLat(jObject.getString("latitude"));
                            temp.setLng(jObject.getString("longitude"));
                            list.add(temp);

                        }catch (JSONException e)
                        {

                        }
                    }
                }
                catch (JSONException e)
                {

                }

                CategoryListFragment f = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list); // list 넘기기
                f.setArguments(bundle);

                ((MainActivity)getActivity()).changeFragment(f);
            }
        }
    };
}
