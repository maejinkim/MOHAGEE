package com.example.maedin.mohagee.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.fragment.HomeFragment;
import com.example.maedin.mohagee.item.InstaItem;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeListAdapter extends BaseAdapter {

    //넣어줄 데이터 리스트
    private ArrayList<InstaItem> InstaList = null;
    private int listCnt = 0;
    Bitmap bitmap;
    LayoutInflater inflater = null;

    //생성자 : 데이터 셋팅
    public HomeListAdapter(ArrayList<InstaItem> InstaList) {
        this.InstaList = InstaList;
        listCnt = InstaList.size();

    }

    //화면 갱신 전 호출, 아이템 갯수 결정
    @Override
    public int getCount() {
        return listCnt;
    }


    //리스트 뷰에 데이터를 넣어줌 - 화면 표시, position: 몇번째아이템
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //postion: List View의 위치
        //첫번째면 position = 0;
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null)
        {
            if (inflater == null)
                inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_home, parent, false);
        }

        //위젯과 연결
        TextView name = (TextView) convertView.findViewById(R.id.place_name);
        TextView address = (TextView) convertView.findViewById(R.id.address);
        TextView phone_number = (TextView) convertView.findViewById(R.id.phone_number);
        TextView theme1 = (TextView) convertView.findViewById(R.id.txt_detail_theme1);
        TextView theme2 = (TextView) convertView.findViewById(R.id.txt_detail_theme2);
        TextView theme3 = (TextView) convertView.findViewById(R.id.txt_detail_theme3);
        final ImageView image=(ImageView) convertView.findViewById(R.id.img_detail);

        InstaItem temp = InstaList.get(position);
        ArrayList<String> themes=new ArrayList<>() ;
        String theme_a= temp.getTheme1();
        String theme_b= temp.getTheme2();
        String theme_c= temp.getTheme3();
        themes.add(theme_a);
        themes.add(theme_b);
        themes.add(theme_c);



        name.setText(temp.getName());
        address.setText(temp.getAddress());
        phone_number.setText(temp.getPhone_number());
        theme1.setText("");
        theme1.setText(theme1.getText()+"#"+themes.get(0)+" ");
        theme2.setText("");
        theme2.setText(theme2.getText()+"#"+themes.get(1)+" ");
        theme3.setText("");
        theme3.setText(theme3.getText()+"#"+themes.get(2)+" ");
        String img=temp.getImage();

        //Bitmap bmp = BitmapFactory.decodeByteArray(k, 0, k.length);

/*
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.amanda); // Replace your image name with my one
        BitmapDrawable bitmapDrawable  = new BitmapDrawable(MainActivity.this.getResources(), myBitmap);

       */

        new DownloadImageTask((ImageView) convertView.findViewById(R.id.img_detail)).execute(img);
        image.setImageBitmap(bitmap);  // Replace your activity with my main





        convertView.setTag(""+position);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return InstaList.get(position);
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

}
