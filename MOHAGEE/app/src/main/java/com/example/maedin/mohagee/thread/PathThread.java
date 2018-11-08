package com.example.maedin.mohagee.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.Queue;

public class PathThread extends Thread {

    private final String call_url = "https://api2.sktelecom.com/tmap/";
    private final String type = "routes/pedestrian?version=1";  // 타입 = 도보
    /* *    Another type
     *       Car = routes?version=1
     *       Bicyle = routes/bicycle?version=1
     * */
    private final String code = "reqCoordType=WGS84GEO&resCoordType=WGS84GEO";
    private final String key = "f2cb4b59-32a1-4293-967f-03f42c3d845a";
    //Server key f2cb4b59-32a1-4293-967f-03f42c3d845a
    //Browser key a51a52d7-69c0-43b2-ae33-239133da849a
    String xml = null;
    private URL url = null;
    private BufferedReader in = null;
    private String inLine = "";

    private Handler thHandler = null; // 쓰레드 핸들러
    private Handler fgHandler = null; // 프래그먼트 핸들러

    private Queue<String> startX;  // 출발 X좌표
    private Queue<String> startY;  // 출발 Y좌표
    private Queue<String> endX;    // 도착 X좌표
    private Queue<String> endY;    // 도착 Y좌표
    private String startName = "현위치";
    private String endName; // 도착지 이름

    public Handler getFgHandler() {
        return thHandler;
    }
    public PathThread(Handler h) {
        fgHandler = h;
        startX = new LinkedList<String>();
        startY = new LinkedList<String>();
        endX = new LinkedList<String>();
        endY = new LinkedList<String>();
    }

    //====================================================================================================


    //====================================================================================================

    public void initiate(String startX, String startY, String endX, String endY, String endName) {
        this.startX.add(startX);
        this.startY.add(startY);
        this.endX.add(endX);
        this.endY.add(endY);
        this.endName = endName;
    }

    @Override
    public void run() {
        Looper.prepare();
        thHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //JSONArray jsonArray = new JSONArray();
                Integer temp = startX.size();
                String t = temp.toString();
                Log.d("check_size", t);
                for (int i = 0; i < startX.size();) {
                    String s_x = startX.poll();
                    String s_y = startY.poll();
                    String e_x = endX.poll();
                    String e_y = endY.poll();

                    Message retMsg = new Message();
                    StringBuilder uri = new StringBuilder();
                    uri.append(url);
                    uri.append(type);
                    uri.append("&appKey=" + key);
                    StringBuilder content = new StringBuilder();
                    content.append("&startY=" + s_y);
                    content.append("&startX=" + s_x);
                    content.append("&endY=" + e_y);
                    content.append("&endX=" + e_x);
                    content.append("&" + code);
                    Log.d("init_param", s_x + ',' + e_y + ',' + startName + ',' + endName);
                    try {
                        startName = URLEncoder.encode(startName, "UTF-8");
                        endName = URLEncoder.encode(endName, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Log.d("THREAD_ERR", "thread error1");
                    }

                    try {
                        url = new URL(call_url + type + "&appKey=" + key + "&startX=" + s_x + "&startY=" + s_y
                                + "&endX=" + e_x + "&endY=" + e_y + "&" + code + "&startName=" + startName + "&endName=" + endName);
                        Log.d("URL", url + "<-");

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        Log.d("THREAD_ERR", "thread error2");
                    }

                    try {
                        in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("THREAD_ERR", "thread error3");
                    }

                    try {
                        while ((inLine = in.readLine()) != null) {
                            xml = inLine;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("THEREAD_ERR", "thread_error4");
                    }

                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("THREAD_ERR", "thrread_error5");
                    }

                    Log.d("XMLRET", xml + "<-");
                    //jsonArray.put(xml);
                    retMsg.obj = xml;
                    fgHandler.sendMessage(retMsg);
                }
                /*JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("jsonArray", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                retMsg.obj = jsonObject.toString();*/
            }
        };
        Looper.loop();
    }
}
