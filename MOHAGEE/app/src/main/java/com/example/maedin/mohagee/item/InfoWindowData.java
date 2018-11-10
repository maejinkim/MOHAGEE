package com.example.maedin.mohagee.item;

public class InfoWindowData {
    private String image  ="";
    private String title = "";
    private String address = "";
    private String bigsmalltype = "";
    private String Time = "";
    private String telnum ="";
    private String rating = "";

    public String getTitle(){return title;}

    public void setTitle(String title){this.title = title;}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getbigsmalltype() {
        return bigsmalltype;
    }

    public void setbigsmalltype(String bigsmalltype) {
        this.bigsmalltype = bigsmalltype;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        if(telnum != null)
            this.telnum = telnum;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
