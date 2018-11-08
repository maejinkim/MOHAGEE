package com.example.maedin.mohagee.application;

import android.app.Application;
import android.os.StrictMode;

import com.example.maedin.mohagee.item.CourseItem;
import com.example.maedin.mohagee.item.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {

    private ArrayList<CourseItem> courseList;
    private ArrayList<String> mainCategoryList;
    private ArrayList<ArrayList<String>> smallCategoryList;
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();

        courseList = new ArrayList<>();
        createCategoryList();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public int getCourseListSize()
    {
        return  courseList.size();
    }

    public ArrayList<CourseItem> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<CourseItem> courseList) {
        this.courseList = courseList;
    }

    public CourseItem getPositionItem(int i)
    {
        return courseList.get(i);
    }

    public void addCourse(CourseItem courseItem)
    {
        courseList.add(courseItem);
    }

    public void clearCourse()
    {
        courseList.clear();
    }

    public void createCategoryList()
    {
        mainCategoryList = new ArrayList<>(Arrays.asList("맛집","카페","놀이","문화","기타"));
        ArrayList<String> food = new ArrayList<>(Arrays.asList("한식", "일식","중식","양식","분식"));
        ArrayList<String> cafe = new ArrayList<>(Arrays.asList("카페"));
        ArrayList<String> play = new ArrayList<>(Arrays.asList("당구장","피시방","노래방","방탈출"));
        ArrayList<String> culture = new ArrayList<>(Arrays.asList("영화", "연극","전시회"));
        ArrayList<String> etc = new ArrayList<>(Arrays.asList("쇼핑", "공원"));
        smallCategoryList = new ArrayList<>(Arrays.asList(food, cafe, play, culture, etc));

    }

    public ArrayList<String> getSmallCategoryList(int index)
    {
        return smallCategoryList.get(index);
    }

    public ArrayList<String> getMainCategoryList()
    {
        return mainCategoryList;
    }

    public ArrayList<ArrayList<String>> getSmallCategoryList() {
        return smallCategoryList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
