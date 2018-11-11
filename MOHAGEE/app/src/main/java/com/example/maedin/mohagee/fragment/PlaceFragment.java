package com.example.maedin.mohagee.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.adapter.HomeListAdapter;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.InstaItem;
import com.example.maedin.mohagee.thread.ServerThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class PlaceFragment extends Fragment {

    View view;
    ImageView img;
    TextView detail_name;
    TextView big_category;
    TextView small_category;
    TextView theme1;
    TextView theme2;
    TextView theme3;
    TextView time;
    TextView address;
    TextView star;
    ImageView heart;
    ImageView heartRed;

    private ServerThread serverThread=ServerThread.getInstance();
    private static final String TAG = "SignupActivity";
    private String myResult;
    String id;
    Bitmap bitmap;
    String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_place, container, false);

        img=(ImageView)view.findViewById(R.id.img_detail);
        detail_name=(TextView)view.findViewById(R.id.txt_detail_name);
        big_category=(TextView)view.findViewById(R.id.txt_detail_mc);
        small_category=(TextView)view.findViewById(R.id.txt_detail_sc);
        theme1=(TextView)view.findViewById(R.id.txt_detail_theme1);
        theme2=(TextView)view.findViewById(R.id.txt_detail_theme2);
        theme3=(TextView)view.findViewById(R.id.txt_detail_theme3);
        address=(TextView)view.findViewById(R.id.txt_detail_address);
        star=(TextView)view.findViewById(R.id.txt_detail_popularity);
        heart=(ImageView)view.findViewById(R.id.image_heart);
        heartRed=(ImageView)view.findViewById(R.id.image_heart_red);

        serverThread.setFgHandler(mHandler);

        id=getArguments().getString("id");
        Log.d("여기에여","들어왓어요");
          //장소 이름을 뭐로 넘겨주는지 확인할것

        serverThread.setLocation(id,name,myResult);
        serverThread.getFgHandler().sendEmptyMessage(4);
        Log.d("handler","들어왓어요");

        heart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "커스텀 리스트에 추가했습니다.", Toast.LENGTH_SHORT).show();
                heartRed.setVisibility(View.VISIBLE);
                heart.setVisibility(View.INVISIBLE);
                serverThread.setAddCustom(((App)getActivity().getApplication()).getUser().getId(),id,myResult);
                serverThread.getFgHandler().sendEmptyMessage(8);
            }
        });
        heartRed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "이미 리스트에 추가한 장소입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void getIsCustom()
    {
        Message msg = new Message();
        msg.what = 11;
        serverThread.setDeleteCustom(((App)getActivity().getApplication()).getUser().getId(),id,myResult);
        serverThread.getFgHandler().sendMessage(msg);
    }

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){

            if (msg.what == 11)
            {
                if (msg.obj.toString().contains("true"))
                {

                    heartRed.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.INVISIBLE);

                }
            }
            else
            {
                String temp=msg.obj.toString();
                Log.d("msg","보고싶어요");
                System.out.println(msg.obj.toString());
                try {
                    JSONObject jsonObject = new JSONObject(temp);
                    try {

                        String place_name = jsonObject.getString("place_name");
                        String place_address = jsonObject.getString("place_address");
                        String place_theme1 = jsonObject.getString("theme1");
                        String place_theme2= jsonObject.getString("theme2");
                        String place_theme3 = jsonObject.getString("theme3");
                        String place_image = jsonObject.getString("place_image");
                        String stars=jsonObject.getString("star");
                        String big=jsonObject.getString("big_category");
                        String small=jsonObject.getString("small_category");

                        detail_name.setText(place_name);
                        address.setText(place_address);
                        if(place_theme1=="null")
                        {
                            theme1.setText("");
                        }
                        else
                        {
                            theme1.setText("#"+place_theme1);
                        }
                        if(place_theme2=="null")
                        {
                            theme2.setText("");
                        }
                        else
                        {
                            theme2.setText("#"+place_theme2);
                        }
                        if(place_theme3=="null")
                        {
                            theme3.setText("");
                        }
                        else
                        {
                            theme3.setText("#"+place_theme3);
                        }

                        star.setText(stars);
                        big_category.setText(big);
                        small_category.setText(small);

                        new PlaceFragment.DownloadImageTask((ImageView) getView().findViewById(R.id.img_detail)).execute(place_image);
                        img.setImageBitmap(bitmap);  // Replace your activity with my main

                        getIsCustom();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };


}
