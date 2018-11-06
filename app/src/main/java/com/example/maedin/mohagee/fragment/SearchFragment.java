package com.example.maedin.mohagee.fragment;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.ListAdapter;
import com.example.maedin.mohagee.item.Location_information;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.thread.PathThread;
import com.example.maedin.mohagee.activity.SearchActivity;
import com.example.maedin.mohagee.activity.Select_Location_Activity;
import com.example.maedin.mohagee.thread.ServerThread;
import com.example.maedin.mohagee.thread.ServerThread2;
import com.example.maedin.mohagee.activity.SignInActivity;
import com.example.maedin.mohagee.item.Location_information;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.maedin.mohagee.activity.search_result_Activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SearchFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        TimePickerDialog.OnTimeSetListener{
    Calendar calendar;
    private MapView mapView = null;
    private Integer checklocation = 0;
    private String time = "";
    final Calendar cal  = Calendar.getInstance();
    private double Lat = 0;
    private double Lng = 0;
    private String big_class= "";
    private String small_class = "";
    private ArrayList<String> themes;
    private int checknum = 1;
    private ArrayList<ImageButton> locations;
    private LinearLayout Linear_layout;
    private String with_who = "";
    private ImageButton button;
    private ServerThread2 serverThread2;
    private ArrayList<Location_information> loc_list;


    FragmentTransaction tran;

    public SearchFragment()
    {
        locations = new ArrayList<>();
        themes = new ArrayList<>();
        loc_list = new ArrayList<>();
    }

    public void setSmall_class(String small_class) {
        this.small_class = small_class;
    }

    public void addtheme(String theme)
    {
        themes.add(theme);
    }

    public void deletetheme(String theme)
    {
        themes.remove(theme);
    }

    public interface OnMyListener{
        void onReceivedData(Object data);
    }
    private OnMyListener onMyListener;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof OnMyListener){
            onMyListener = (OnMyListener) getActivity();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serverThread2 = new ServerThread2(serverhandler);
        serverThread2.setDaemon(true);
        serverThread2.start();
    }



    View view;
    Button after_button, right_now, solo, with_friend, with_parent, doing_date, with_children, resturant, cafe;
    Button over_map_button, add_button, result_button;
    Button play_button, culture_button, etc_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Fragment origin_fragment = new Theme_fragment_origin();

        view = inflater.inflate(R.layout.fragment_search, container, false);

        mapView = (MapView)view.findViewById(R.id.map_part);
        mapView.getMapAsync(this);

        over_map_button = (Button)view.findViewById(R.id.over_map_button);
        over_map_button.setOnClickListener(this);

        after_button = (Button)view.findViewById(R.id.after);
        after_button.setOnClickListener(this);

        right_now = (Button)view.findViewById(R.id.right_now);
        right_now.setOnClickListener(this);

        solo = (Button)view.findViewById(R.id.solo);
        solo.setOnClickListener(this);

        with_friend = (Button)view.findViewById(R.id.with_friend);
        with_friend.setOnClickListener(this);

        with_parent = (Button)view.findViewById(R.id.with_parent);
        with_parent.setOnClickListener(this);

        doing_date = (Button)view.findViewById(R.id.doing_date);
        doing_date.setOnClickListener(this);

        with_children = (Button)view.findViewById(R.id.with_children);
        with_children.setOnClickListener(this);

        resturant = (Button)view.findViewById(R.id.resturant);
        resturant.setOnClickListener(this);

        cafe = (Button)view.findViewById(R.id.cafe);
        cafe.setOnClickListener(this);

        play_button = (Button)view.findViewById(R.id.play);
        play_button.setOnClickListener(this);


        culture_button = (Button)view.findViewById(R.id.culture);
        culture_button.setOnClickListener(this);

        etc_button = (Button)view.findViewById(R.id.etc);
        etc_button.setOnClickListener(this);


        add_button = (Button)view.findViewById(R.id.add_button);
        add_button.setOnClickListener(this);

        result_button = (Button)view.findViewById(R.id.show_result_button);
        result_button.setOnClickListener(this);

        Linear_layout = (LinearLayout) view.findViewById(R.id.dynamic_area);

        ((MainActivity) getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first_fragment , origin_fragment)
                .commit();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);

        markerOptions.title("서울");

        markerOptions.snippet("수도");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hour = String.valueOf(hourOfDay);
        String min = String.valueOf(minute);
        // Do something with the time chosen by the user
        time = "";
        time = time+hour+':'+min;
        Log.d("check_time", time);

    }


    @Override
    public void onClick(View v)
    {
        Fragment res_fragment = new Theme_fragment_resturant();
        Fragment cafe_fragment = new Theme_fragment_cafe();
        Fragment cul_fragment = new Theme_fragment_culture();
        Fragment play_fragment = new Theme_fragment_play();
        Fragment etc_fragment = new Theme_fragment_etc();
        Fragment origin_fragment = new Theme_fragment_origin();

        Button b;
        b = v.findViewById(v.getId());

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(300,300);
        params1.leftMargin = 15;
        params1.rightMargin = 15;

        Bundle bundle = new Bundle(1);

        switch (v.getId())
        {
            case R.id.after:
            {
                //TimePicker mTimePicker = new TimePicker();
                //mTimePicker.show(getActivity().getFragmentManager(), "Select time");
                if(!b.isSelected()) {
                    b.setSelected(true);
                    view.findViewById(R.id.right_now).setSelected(false);

                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(((MainActivity)getActivity()).getSupportFragmentManager(), "time picker time");

                }
                else
                {
                    b.setSelected(false);
                    time = "";
                }

                break;
            }

            case R.id.right_now:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    view.findViewById(R.id.after).setSelected(false);
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfnow = new SimpleDateFormat("HH:mm");
                    String formatDate = sdfnow.format(date);

                    time = formatDate;
                }
                else
                {
                    b.setSelected(false);
                    time = "";
                }

                Log.d("check_time", time);

                break;
            }
            case R.id.solo:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    with_who = "solo";

                    with_friend.setSelected(false);
                    with_parent.setSelected(false);
                    with_children.setSelected(false);
                    doing_date.setSelected(false);
                }
                else
                {
                    with_who = "";
                    b.setSelected(false);
                }

                break;
            }
            case R.id.with_friend:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    with_who = "with_friend";
                    solo.setSelected(false);
                    with_parent.setSelected(false);
                    with_children.setSelected(false);
                    doing_date.setSelected(false);
                }
                else
                {
                    with_who ="";
                    b.setSelected(false);
                }

                break;
            }
            case R.id.with_parent:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    with_who = "with parent";
                    with_friend.setSelected(false);
                    solo.setSelected(false);
                    with_children.setSelected(false);
                    doing_date.setSelected(false);
                }
                else
                {
                    with_who = "";
                    b.setSelected(false);
                }

                break;
            }
            case R.id.doing_date:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    with_who = "doing date";
                    with_friend.setSelected(false);
                    with_parent.setSelected(false);
                    with_children.setSelected(false);
                    solo.setSelected(false);
                }
                else
                {
                    with_who = "";
                    b.setSelected(false);
                }

                break;
            }
            case R.id.with_children:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    with_who = "with_children";
                    with_friend.setSelected(false);
                    with_parent.setSelected(false);
                    solo.setSelected(false);
                    doing_date.setSelected(false);
                }
                else
                {
                    with_who = "";
                    b.setSelected(false);
                }

                break;
            }
            case R.id.resturant:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    themes.clear();
                    big_class = "resturant";
                    cafe.setSelected(false);
                    play_button.setSelected(false);
                    culture_button.setSelected(false);
                    etc_button.setSelected(false);

                    bundle.putString("Type", "resturant");
                    res_fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , res_fragment)
                            .commit();
                }
                else
                {
                    themes.clear();
                    b.setSelected(false);
                    big_class = "";
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , origin_fragment)
                            .commit();
                }

                break;
            }
            case R.id.cafe:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    themes.clear();
                    big_class = "cafe";
                    setSmall_class("#카페");
                    resturant.setSelected(false);
                    play_button.setSelected(false);
                    culture_button.setSelected(false);
                    etc_button.setSelected(false);


                    bundle.putString("Type", "cafe");
                    cafe_fragment.setArguments(bundle);
                    ((MainActivity) getActivity())
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , cafe_fragment)
                            .commit();
                }
                else
                {
                    b.setSelected(false);
                    themes.clear();
                    big_class = "";
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , origin_fragment)
                            .commit();
                    setSmall_class("");

                }

                break;
            }
            case R.id.play:
            {

                if(!b.isSelected()) {
                    b.setSelected(true);
                    big_class = "play";
                    cafe.setSelected(false);
                    resturant.setSelected(false);
                    culture_button.setSelected(false);
                    etc_button.setSelected(false);
                    themes.clear();

                    bundle.putString("Type", "play");
                    play_fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , play_fragment)
                            .commit();
                }
                else
                {
                    b.setSelected(false);
                    themes.clear();

                    big_class = "";
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , origin_fragment)
                            .commit();
                }


                break;
            }
            case R.id.culture: {

                if(!b.isSelected()) {
                    b.setSelected(true);
                    big_class = "culture";
                    themes.clear();
                    cafe.setSelected(false);
                    play_button.setSelected(false);
                    resturant.setSelected(false);
                    etc_button.setSelected(false);
                    bundle.putString("Type", "culture");
                    cul_fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , cul_fragment)
                            .commit();
                }
                else
                {
                    b.setSelected(false);
                    big_class = "";
                    themes.clear();

                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , origin_fragment)
                            .commit();
                }


                break;
            }
            case R.id.etc:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                    big_class = "etc";
                    themes.clear();

                    cafe.setSelected(false);
                    play_button.setSelected(false);
                    culture_button.setSelected(false);
                    resturant.setSelected(false);

                    bundle.putString("Type", "etc");
                    etc_fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , etc_fragment)
                            .commit();
                }

                else
                {
                    b.setSelected(false);
                    big_class = "";
                    themes.clear();
                    ((MainActivity) getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.first_fragment , origin_fragment)
                            .commit();
                }


                break;
            }


            case R.id.over_map_button:
            {
                if(!b.isSelected()) {
                    b.setSelected(true);
                }
                else
                {
                    b.setSelected(false);
                }

                Log.d("textabc", "onClick: before");
                startActivityForResult(new Intent(getActivity(), Select_Location_Activity.class), 0);

                //Bundle bun = this.getArguments();
                // Lat = bun.getDouble("Lat");
                // Lng = bun.getDouble("Lng");

                //Log.d("textabc", "onClick: after");
                break;
            }
            case R.id.add_button:
            {
                if(big_class =="")
                {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "모하고 놀지 선택해 주세요!",Toast.LENGTH_LONG).show();
                    break;
                }
                else if(small_class == "")
                {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "정확히 모하고 놀지 선택해 주세요!",Toast.LENGTH_LONG).show();
                    break;
                }
                else if(Lat == 0 && Lng == 0)
                {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "어디서 모하고 놀지 선택해 주세요!",Toast.LENGTH_LONG).show();
                    break;
                }
                else if(time =="")
                {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "언제 모하고 놀지 선택해 주세요!",Toast.LENGTH_LONG).show();
                    break;
                }
                else if(with_who =="")
                {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "누구랑 모하고 놀지 선택해 주세요!",Toast.LENGTH_LONG).show();
                    break;

                }

                button = new ImageButton(getActivity());
                button.setId(checknum);

                button.setBackground(ContextCompat.getDrawable(((MainActivity)getActivity()).getApplicationContext(), R.drawable.circle_button));
                if(small_class == "#카페") {
                    button.setImageResource(R.drawable.cafe_button_new);
                }
                else if(small_class.equals("#중식")){
                    button.setImageResource(R.drawable.china_button);
                }
                else if(small_class.equals("#한식"))
                {
                    button.setImageResource(R.drawable.korea_button);
                }

                else if(small_class.equals("#분식"))
                {
                    button.setImageResource(R.drawable.snack_button);
                }
                else if(small_class.equals("#양식"))
                {
                    button.setImageResource(R.drawable.west_button);
                }
                else if (small_class.equals("#일식"))
                {
                    button.setImageResource(R.drawable.japan_button);
                }
                else if(small_class.equals("#당구장"))
                {
                    button.setImageResource(R.drawable.billiard_button);
                }
                else if (small_class.equals("#피시방"))
                {
                    button.setImageResource(R.drawable.pc_room_button);
                }
                else if (small_class.equals("#노래방"))
                {
                    button.setImageResource(R.drawable.singing_room_button);
                }
                else if(small_class.equals("#방탈출"))
                {
                    button.setImageResource(R.drawable.room_escape_button);
                }
                else if (small_class.equals("#볼링장"))
                {
                    button.setImageResource(R.drawable.bowling);
                }
                else if (small_class.equals("#영화"))
                {
                    button.setImageResource(R.drawable.cineama_button);
                }
                else if(small_class.equals("#연극"))
                {
                    button.setImageResource(R.drawable.theater_button);
                }
                else if(small_class.equals("#전시회"))
                {
                    button.setImageResource(R.drawable.gallery_button);
                }
                else if (small_class.equals("#쇼핑"))
                {
                    button.setImageResource(R.drawable.shopping_button);
                }
                else if(small_class.equals("#공원"))
                {
                    button.setImageResource(R.drawable.park);
                }
                button.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
                button.setLayoutParams(params1);

                Location_information temp = new Location_information(big_class,small_class,themes);
                loc_list.add(temp);
                locations.add(button);
                Linear_layout.addView(button);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = view.getId();
                        for(int i=index ; i<locations.size() ; i++)
                        {
                            int temp = locations.get(i).getId();
                            locations.get(i).setId(temp-1);
                        }
                        locations.remove(index-1);
                        loc_list.remove(index-1);

                        Linear_layout.removeView(view);
                        checknum--;

                        if(loc_list.size() == 0)
                        {
                            result_button.setSelected(false);
                        }
                    }
                });

                if(loc_list.size() >=1)
                {
                    result_button.setSelected(true);
                }

                checknum ++;
                themes.clear();
                big_class = "";
                small_class = "";

                cafe.setSelected(false);
                play_button.setSelected(false);
                resturant.setSelected(false);
                etc_button.setSelected(false);
                culture_button.setSelected(false);

                ((MainActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.first_fragment , origin_fragment)
                        .commit();


                break;
            }
            case R.id.show_result_button:
            {
                if(loc_list.size() == 0)
                {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "스케줄을 담아주세요!", Toast.LENGTH_LONG).show();
                    break;
                }
                serverThread2.initiate(time,Lat, Lng, with_who, loc_list);
                serverThread2.getFgHandler().sendEmptyMessage(0);
                break;
            }

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
                String latstr= String.valueOf(Lat);
                Log.d("place", latstr);

        }
    }

    private Handler serverhandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                ArrayList<String> get = (ArrayList<String>)msg.obj;
                get.add(String.valueOf(Lat));
                get.add(String.valueOf(Lng));
                Intent intent = new Intent(getActivity(), search_result_Activity.class);
                intent.putStringArrayListExtra("locations", get);
                startActivity(intent);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    };

}
