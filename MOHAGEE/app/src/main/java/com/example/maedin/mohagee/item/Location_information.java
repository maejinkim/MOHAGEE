package com.example.maedin.mohagee.item;

import java.util.ArrayList;

public class Location_information {
    private String bigtype = "";            //대분류
    private String smalltype = "";          //소분류
    private ArrayList<String> themes;       //테마들
    private int order;                      //순서
    private String withwho = "";
    private String tel_num = "";
    private String rating ="";
    private String address = "";
    private String time = "";
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWithwho() {
        return withwho;
    }

    public void setWithwho(String withwho) {
        this.withwho = withwho;
    }


    public String getTel_num()
    {
        return tel_num;
    }
    public void setTel_num(String tel_num)
    {
        this.tel_num = tel_num;
    }

    public Location_information()
    {
        tel_num = "";
    }


    public Location_information(String big, String small, ArrayList<String> theme)
    {
        bigtype = big;
        smalltype = small;
        themes = new ArrayList<>();
        for (int i = 0 ; i<theme.size() ;i++){
            themes.add(theme.get(i));
        }
    }
    public Location_information(String big, String small) {
        bigtype = big;
        smalltype = small;
    }
    public String getBigtype()
    {
        return bigtype;
    }
    public String getSmalltype()
    {
        return smalltype;
    }
    public int getOrder(){ return order; }
    public void setBigtype(String input)
    {
        bigtype = input;
    }
    public void setSmalltype(String input)
    {
        smalltype = input;
    }


    public void setThemes(String input) {
        if(themes.size() <2)
        {
            themes.add(input);
        }
        else
        {
            themes.remove(0);
            themes.add(input);
        }
    }
    public void setOrder(int newone)
    {
        order = newone;
    }

    public void minusorder()
    {
        order--;
    }



    public ArrayList<String> getThemes() {
        return themes;
    }
}
