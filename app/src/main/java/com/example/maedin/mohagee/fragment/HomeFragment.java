package com.example.maedin.mohagee.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.HomeListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.InstaItem;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ArrayList<InstaItem> InstaList = null;
    private ServerThread serverThread=ServerThread.getInstance();
    private ListView listView;
    private HomeListAdapter adapter;
    private String myResult="";

    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        serverThread.setFgHandler(mHandler);

        view = inflater.inflate(R.layout.frgment_home, container, false);

        if (InstaList == null)
        {
            InstaList = new ArrayList<>();
        }
        for(int i=0; i<30; i++)
        {
            if(i==0)
                System.out.println("응 다 나왓어~");

            serverThread.getFgHandler().sendEmptyMessage(7);
        }

        listView = (ListView) view.findViewById(R.id.home_list_view);
        adapter = new HomeListAdapter(InstaList);
        Log.d("size",String.valueOf(InstaList.size()));
        listView.setAdapter(adapter);

        return view;
    }
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            String temp=msg.obj.toString();

            try {
                JSONObject jsonObject = new JSONObject(temp);
                try {
                    InstaItem instaItem=new InstaItem();
                    String place_name = jsonObject.getString("place_name");
                    String place_address = jsonObject.getString("place_address");
                    String place_theme1 = jsonObject.getString("theme1");
                    String place_theme2= jsonObject.getString("theme2");
                    String place_theme3 = jsonObject.getString("theme3");
                    String place_phone = jsonObject.getString("star");
                    String place_image =jsonObject.getString("img");
                    //byte[] byteImage = Base64.decode(jsonObject.getString("img").toString(), Base64.URL_SAFE);
                    instaItem.setData(place_name,place_address,place_theme1,place_theme2,place_theme3,place_phone,place_image);
                    InstaList.add(instaItem);
                    adapter = new HomeListAdapter(InstaList);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


}

