package com.example.maedin.mohagee.activity;


import java.util.ArrayList;

public class Location_information {
    private String bigtype = "";            //대분류
    private String smalltype = "";          //소분류
    private ArrayList<String> themes;       //테마들
    private int order;                      //순서
    private String withwho;

    public Location_information(String big, String small, ArrayList<String> theme,int ordernum, String withwho)
    {
        bigtype = big;
        smalltype = small;
        themes = theme;
        order = ordernum;
        this.withwho = withwho;
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
