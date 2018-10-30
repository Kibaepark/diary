package com.example.ki.a10_25;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PageAdapter pageAdapter=new PageAdapter(getSupportFragmentManager());
        ViewPager viewPager=(ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(pageAdapter);
        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.GRAY,Color.rgb(54,46,43));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setupWithViewPager(viewPager);
     }
}






