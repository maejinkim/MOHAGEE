package com.example.maedin.mohagee.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.fragment.HomeFragment;
import com.example.maedin.mohagee.fragment.Temp1;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private DrawerLayout drawerLayout;
    TextView user_name;
    Button btn_logout;

    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar); //activity actionbar로 대체

        //drawerLayout 지정
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //NavigationView 지정, 클릭시 setNavigationItemSelected로 이동
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        user_name = (TextView) headerView.findViewById(R.id.txt_userName);
        //user_name.setText(memberInfoItem.user_name + " 님");

        btn_logout = (Button) headerView.findViewById(R.id.btn_logout) ;
        btn_logout.setOnClickListener(this);

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
            case R.id.drawer_home:
                manager.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();
                break;

            case R.id.temp1:
                manager.beginTransaction().replace(R.id.content_main, new Temp1()).commit();
                break;
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);//Drawer를 닫음
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_logout:
            {
                finish();
                break;
            }
        }
    }
}
