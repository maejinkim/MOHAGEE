package com.example.maedin.mohagee.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class ServerThread extends Thread{

    private static ServerThread instacne = null; //instacne
    private String idText;
    private String passwordText;
    private String nameText;
    private String ageText;
    private String genderText;
    private String locationKey;
    private String locationText;
    private String lat, lon, range, category;


    private ServerThread()  {}  //private 생성자
    private String myResult; // 서버로 부터 받아온 값
    private Handler fgHandler; //프래그먼트 핸들러
    private Handler thHandler; //쓰레드 핸들러
    public Handler getFgHandler() {
        return thHandler;
    }
    public void setFgHandler(Handler mHadler){fgHandler = mHadler;}

    public static ServerThread getInstance() {
        if(instacne == null) {
            instacne = new ServerThread();
            instacne.setDaemon(true);
            instacne.start();
        }
        return instacne;
    }

    private String xml = "";
    private URL url = null;
    private BufferedReader in = null;
    private String inLine = "";

    public void setLogin(String idText,String passwordText,String myResult){
        this.idText = idText;
        this.passwordText = passwordText;
        this.myResult = myResult;
    }

    public void setLocation(String locationkey,String locationText,String myResult){
        this.locationKey = locationkey;
        this.locationText = locationText;
        this.myResult = myResult;
    }

    public void setRegister(String idText,String passwordText,String nameText,String ageText,String genderText,String myResult){
        this.idText = idText;
        this.passwordText = passwordText;
        this.nameText = nameText;
        this.ageText = ageText;
        this.genderText=genderText;
        this.myResult = myResult;
    }
    public void setIdCheck(String idText,String myResult){
        this.idText = idText;
        this.myResult = myResult;
    }

    public void setCategory(String lat, String lon, String range, String category, String myResult){
        this.lat = lat;
        this.lon = lon;
        this.range = range;
        this.category = category;
        this.myResult = myResult;
    }

    public void setCustomList(String idText,String myResult){
        this.idText = idText;
        this.myResult = myResult;
    }


    public void setAddCustom(String idText, String locationKey, String myResult){
        this.idText = idText;
        this.locationKey = locationKey;
        this.myResult = myResult;
    }

    public void setAllDeleteCustom(String idText, String myResult){
        this.idText = idText;
        this.myResult = myResult;
    }

    public void setDeleteCustom(String idText, String locationKey, String myResult){
        this.idText = idText;
        this.locationKey = locationKey;
        this.myResult = myResult;
    }

    public void run(){

        Looper.prepare();
        thHandler = new Handler() {
            @Override
            public void handleMessage(Message msg){
                Message retMsg = new Message();

                switch (msg.what) {
                    case 1: // Login
                        try {
                            URL url = new URL("http://163.180.116.251:8080/mohagi/sign_in");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("user_id").append("=").append(idText).append("&");
                            buffer.append("user_pw").append("=").append(passwordText);

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2: // Register
                        try {
                            URL url = new URL("http://163.180.116.251:8080/mohagi/sign_up");
                            Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅

                            params.put("user_id", idText);
                            params.put("user_name", nameText);
                            params.put("user_pw", passwordText);
                            params.put("birthdate", ageText);
                            params.put("gender", genderText);
                            StringBuilder postData = new StringBuilder();
                            for (Map.Entry<String, Object> param : params.entrySet()) {
                                if (postData.length() != 0) postData.append('&');
                                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                                postData.append('=');
                                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                            }
                            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                            conn.setRequestProperty("Cache-Control", "no-cache, must-revalidate");
                            conn.setRequestProperty("Pragma", "no-cache");
                            conn.setDoOutput(true);
                            conn.getOutputStream().write(postDataBytes); // POST 호출

                            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                            StringBuilder builder = new StringBuilder();
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                builder.append(line).append("\n");
                            }
                            reader.close();
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3: // Login
                        try {
                            URL url = new URL("http://163.180.116.251:8080/mohagi/check_overlap");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("").append("=").append(idText);

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4: // DetailView
                        try {
                            URL url = new URL("http://163.180.116.251:8080//mohagi/detail");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();
                            Log.d("서바", "들어왓어요");
                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();   //장소값 알아보기
                            buffer.append("loc_id").append("=").append(locationKey);

                            Log.d("인풋", "보냇어요");

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                            }
                            Log.d("아웃풋", "받앗어요");
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5: // 카테고리
                        try {
                            URL url = new URL("http://163.180.116.251:8080/mohagi/CategoryInBoundary");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("lat").append("=").append(lat).append("&");
                            buffer.append("lng").append("=").append(lon).append("&");
                            buffer.append("boundary").append("=").append(range).append("&");
                            buffer.append("smallctg").append("=").append(category);

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6: // 커스텀 리스트
                        try {
                            Log.d("ServerThread", "customlist start");
                            URL url = new URL("http://163.180.116.251:8080/mohagi/getBookmarklist");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("user_id").append("=").append(idText);

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                                Log.d("ServerThread", str);
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7: // HomeFragment
                        try {
                            URL url = new URL("http://163.180.116.251:8080/mohagi/home");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            //그냥 나한테 보내주는 거 하면 됨

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 8: // 커스텀 리스트 추가
                        try {
                            Log.d("ServerThread", "bookmark add start");
                            URL url = new URL("http://163.180.116.251:8080/mohagi/addBookmark");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("user_id").append("=").append(idText).append("&");
                            buffer.append("loc_id").append("=").append(locationKey);

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                                Log.d("ServerThread", str);
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 9: // 커스텀 리스트 전체 삭제
                        try {
                            Log.d("ServerThread", "bookmark delete start");
                            URL url = new URL("http://163.180.116.251:8080/mohagi/deleteAllBookmark");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("user_id").append("=").append(idText);


                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                                Log.d("ServerThread", str);
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 10: // 커스텀 리스트 선택 삭제
                        try {
                            Log.d("ServerThread", "bookmark delete start");
                            URL url = new URL("http://163.180.116.251:8080/mohagi/deleteBookmarks");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("user_id").append("=").append(idText).append("&");
                            buffer.append("loc_ids").append("=").append(locationKey);

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                                Log.d("ServerThread", str);
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 11: // 커스텀 리스트 확인
                        try {
                            Log.d("ServerThread", "bookmark check");
                            URL url = new URL("http://163.180.116.251:8080/mohagi/isBookmark");
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();

                            http.setDefaultUseCaches(false);
                            http.setDoInput(true);
                            http.setDoOutput(true);
                            http.setRequestMethod("POST");

                            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("user_id").append("=").append(idText).append("&");
                            buffer.append("loc_id").append("=").append(locationKey);

                            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                            PrintWriter writer = new PrintWriter(outStream);
                            writer.write(buffer.toString());
                            writer.flush();

                            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                            BufferedReader reader = new BufferedReader(tmp);
                            StringBuilder builder = new StringBuilder();
                            String str;
                            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                                Log.d("ServerThread", str);
                            }
                            myResult = builder.toString();
                            retMsg.obj = myResult;
                            retMsg.what = 11;
                            fgHandler.sendMessage(retMsg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        };
        Looper.loop();
    }

}