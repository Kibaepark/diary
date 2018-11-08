package com.example.ki.a10_25;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ki.a10_25.FriendList.PageAdapter;

import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private String nick;
    private long id;
    private String url;
    private Map<String , Object> json;


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






