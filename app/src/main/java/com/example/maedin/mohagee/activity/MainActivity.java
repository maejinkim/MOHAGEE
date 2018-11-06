package com.example.maedin.mohagee.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.fragment.CategoryFragment;
import com.example.maedin.mohagee.fragment.CourseSwapViewFragment;
import com.example.maedin.mohagee.fragment.CustomFragment;
import com.example.maedin.mohagee.fragment.HomeFragment;
import com.example.maedin.mohagee.fragment.MypageFragmet;
import com.example.maedin.mohagee.fragment.PlaceFragment;
import com.example.maedin.mohagee.fragment.SearchFragment;
import com.example.maedin.mohagee.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private DrawerLayout drawerLayout;
    TextView userName;
    Button btnLogout;
    Button btnMypage;

    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar); //activity actionbar로 대체

        //drawerLayout 지정
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //NavigationView 지정, 클릭시 setNavigationItemSelected로 이동
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        userName = (TextView) headerView.findViewById(R.id.txt_userName);
        userName.setText(((App)getApplication()).getUser().getName());

        btnLogout = (Button) headerView.findViewById(R.id.btn_logout) ;
        btnLogout.setOnClickListener(this);

        btnMypage = (Button) headerView.findViewById(R.id.btn_mypage) ;
        btnMypage.setOnClickListener(this);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, new HomeFragment())
                .commit();

    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        FragmentManager manager = getSupportFragmentManager();

        switch (item.getItemId()) {
            case R.id.menu_home:
                manager.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();
                break;

            case R.id.menu_search:
                manager.beginTransaction().replace(R.id.content_main, new SearchFragment(), "search_fragment").commit();
                break;

            case R.id.menu_custom:
                manager.beginTransaction().replace(R.id.content_main, new CustomFragment()).commit();
                break;

            case R.id.menu_category:
                manager.beginTransaction().replace(R.id.content_main, new CategoryFragment()).commit();
                break;

            case R.id.menu_setting:
                manager.beginTransaction().replace(R.id.content_main, new SettingFragment()).commit();
                break;
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);//Drawer를 닫음
        return true;
    }


    public void changeFragment(Fragment fragment)
    {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);//Drawer를 닫음
    }

    public void changeDatailFragment(String id, Fragment fragment)
    {
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);//Drawer를 닫음
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_logout:
            {
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
            }

            case R.id.btn_mypage:
            {
                changeFragment(new MypageFragmet());
                break;
            }
        }
    }
}
