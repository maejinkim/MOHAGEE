package com.example.maedin.mohagee.activity;


import java.util.ArrayList;

public class Location_information {
    private String bigtype = "";            //대분류
    private String smalltype = "";          //소분류
    private ArrayList<String> themes;       //테마들

    Location_information()
    {
        themes = new ArrayList<String>();
    }
    public String getBigtype()
    {
        return bigtype;
    }
    public String getSmalltype()
    {
        return smalltype;
    }
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

    public ArrayList<String> getThemes() {
        return themes;
    }
}
