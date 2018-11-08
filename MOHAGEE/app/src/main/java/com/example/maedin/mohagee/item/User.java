package com.example.maedin.mohagee.item;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String id;
    private String pw;
    private String name;
    private String gender;
    private String age;

    // private ArrayList<String> theme;
    public User(){}

    public void setData(String id, String name,String pw,String gender, String age)
    {
        this.id = id;
        this.name = name;
        this.pw = pw;
        this.age = age;
        this.gender = gender;
        //this.theme = theme;
    }
    public User(Parcel in) {readFromParcel(in);}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(pw);
        //dest.writeList(theme);
    }

    private void readFromParcel(Parcel in) {
        id = in.readString();
        name = in.readString();
        pw = in.readString();
        gender = in.readString();
        age= in.readString();

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {return new User(in);}

        @Override
        public User[] newArray(int size) {return new User[size];}
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
