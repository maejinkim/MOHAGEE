package com.example.maedin.mohagee.item;

import java.util.ArrayList;

public class InstaItem {

    private int id;
    private String name;
    private String address;
    private String phone_number;
    private String theme1;
    private String theme2;
    private String theme3;
    private String image;

    public InstaItem()
    {}
    public InstaItem(int id, String name, String address, String theme1,String theme2, String theme3,String phone_number, String image)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_number=phone_number;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.theme3 = theme3;
        this.image=image;
    }

    public void setData( String name, String address, String theme1,String theme2, String theme3,String phone_number,String image)
    {
        this.name = name;
        this.address = address;

        this.phone_number=phone_number;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.theme3 = theme3;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public String getTheme1() {
        return theme1;
    }
    public String getTheme2() {
        return theme2;
    }
    public String getTheme3() {
        return theme3;
    }
    public String getImage() {
        return image;
    }



}
