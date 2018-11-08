package com.example.ki.a10_25;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ki.a10_25.Task.DiaryWithFriend;
import com.example.ki.a10_25.Task.JsonResult;
import com.example.ki.a10_25.Task.Url;
import com.example.ki.a10_25.Task.readJson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class diaryReadActivity extends AppCompatActivity implements View.OnClickListener {

    private String day,main,feeling;
    private TextView date,mainText,feel;
    private ImageView weather;
    private Button next, back;
    private int i,wt,resource;
    private ArrayList<DiaryWithFriend> diaryWithFriends;
    private int[] image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initView();
        initData(i);
        setView();
        initButton();
    }
    public void initView(){//view 속성 초기화
       i=getIntent().getIntExtra("position",i);
        image= new int[]{R.drawable.w1,R.drawable.w2, R.drawable.w3, R.drawable.w4,
                R.drawable.w5,R.drawable.w6,R.drawable.w7,R.drawable.w8,
                R.drawable.w9,R.drawable.w10, R.drawable.w11,R.drawable.w12,
                R.drawable.w13,R.drawable.w14, R.drawable.w15,R.drawable.w16};
       date=findViewById(R.id.date);
       mainText=findViewById(R.id.main_text);
       next=findViewById(R.id.next_btn);
       back=findViewById(R.id.back_btn);
       weather=findViewById(R.id.weather);
       feel=findViewById(R.id.feel);
    }

    private void initData(int Position){
        diaryWithFriends=JsonResult.getDiaryWithFriends();
        day=diaryWithFriends.get(Position).getDate();
        main=diaryWithFriends.get(Position).getMainText();
        wt=Integer.parseInt(diaryWithFriends.get(Position).getWeather());
        feeling=diaryWithFriends.get(Position).getHowru();
        resource=image[wt];
    }

    private void setView(){  //textview에 data를 보여주도록 설정
        date.setText(day);
        mainText.setText(main);
        weather.setImageResource(resource);
        feel.setText(feeling);
    }
    private void initButton(){
        next.setOnClickListener(this);
        back.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.next_btn){
            if(i==JsonResult.getDiaryWithFriends().size()-1){
                Toast.makeText(this,"마지막 일기입니다.",Toast.LENGTH_SHORT).show();
            }else {
                i++;
                initData(i);
                setView();
            }


        }else{
            if(i==0){
                Toast.makeText(this,"첫 일기입니다.",Toast.LENGTH_SHORT).show();
            }else {
                i--;
                initData(i);
                setView();
            }


        }
    }
}





