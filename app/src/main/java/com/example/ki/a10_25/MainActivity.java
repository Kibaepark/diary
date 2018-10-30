package com.example.ki.a10_25;

import android.content.Intent;
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
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PageAdapter pageAdapter=new PageAdapter(getSupportFragmentManager());
        ViewPager viewPager=(ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
     }
}






