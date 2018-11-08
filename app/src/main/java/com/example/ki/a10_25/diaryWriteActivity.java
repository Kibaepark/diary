package com.example.ki.a10_25;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ki.a10_25.Task.DiaryWithFriend;
import com.example.ki.a10_25.Task.JsonResult;
import com.example.ki.a10_25.Task.Url;
import com.example.ki.a10_25.Task.UserVO;
import com.example.ki.a10_25.Task.jsonSend;
import com.kakao.usermgmt.response.model.UserProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class diaryWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private String date;
    private TextView tw;
    private EditText et,rId;

    private Button sendBtn;
    private Dialog dialog;
    private UserProfile userProfile;
    private long id;



    private Spinner spinner;
    private ArrayList<WeatherSpinnerItem> data;
    private WeatherSpinnerItem item;
    private int[] image;
    private String[] string;
    private EditText receiverId, diary,fill;
    private Button btn;
    private String str, rec,f;
    private WeatherSpinnerItem result;
    private String flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);


        initFragment();
        initSpinner();
    }
    @Override
    public void onClick(View v) {


        str=diary.getText().toString();
        rec=receiverId.getText().toString();
        f=fill.getText().toString();
        if(str.isEmpty()||rec.isEmpty() ||f.isEmpty()){
            Toast.makeText(this,"양식이 모두 채워지지 않았습니다.",Toast.LENGTH_SHORT).show();
        }
        else {
            Log.e("이게 왜되는데",str);
            new AlertDialog.Builder(this)
                    .setTitle("일기작성완료")
                    .setMessage("전송하시겠습니까?")
                    .setIcon(android.R.drawable.ic_menu_send)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // 확인시 처리 로직
                            Toast.makeText(diaryWriteActivity.this, "전송을 완료했습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(diaryWriteActivity.this, MainActivity.class);
                            intent.putExtra("id",id);
                            makeJson();
                            try {
                                String str=sendDiary.toString();
                                new jsonSend(sendDiary, Url.getUrl()+"/sendJson").execute();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);

                        }})
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // 취소시 처리 로직
                            Toast.makeText(diaryWriteActivity.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                        }})
                    .show();
        }
    }
    private Map<String, Object> sendDiary;
    private String mTime;
    private void makeJson() {
        sendDiary= new HashMap<>();

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
        Date currentTime = new Date ();
        mTime = mSimpleDateFormat.format ( currentTime );
        String[] strs=mTime.split("");
        if(strs[9].equals("0")){strs[9]="";}
        mTime="";
        for(int i=0;i<strs.length;i++){
            mTime+=strs[i];
        }


            sendDiary.put("sender",UserVO.getId());
            sendDiary.put("receiver",rec);
            sendDiary.put("sendData",str);
            sendDiary.put("howru",f);
            sendDiary.put("weather",result.getPosition());
            sendDiary.put("date",mTime);


        Log.e("json",sendDiary.toString());
    }



    private void initSpinner(){
        spinner=(Spinner)findViewById(R.id.weather);
        data=new ArrayList<>();
        image= new int[]{R.drawable.w1,R.drawable.w2, R.drawable.w3, R.drawable.w4,
                R.drawable.w5,R.drawable.w6,R.drawable.w7,R.drawable.w8,
                R.drawable.w9,R.drawable.w10, R.drawable.w11,R.drawable.w12,
                R.drawable.w13,R.drawable.w14, R.drawable.w15,R.drawable.w16};
        string=new String[]{"맑음","흐림(낮)","대체로맑음","비바람","눈","폭우","번개와 비","흐림(밤)","소나기","번개","바람","가랑비","비","여우비","맑음(밤)","폭설"};

        id=getIntent().getLongExtra("id",id);
        for(int i=0; i<16;i++){
            item=new WeatherSpinnerItem(image[i],string[i],i);
            data.add(item);
            Log.e("data",data.get(i).getText());
        }
        Log.e("dataaaaa",""+data.size());

        WeatherSpinnerAdapter spinnerAdapter=new WeatherSpinnerAdapter(data,this);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result=new WeatherSpinnerItem(image[position],string[position],position);
                Log.e("dataaa",result.getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initFragment(){
        flag=getIntent().getStringExtra("flag");
        receiverId=(EditText)findViewById(R.id.receiver_id);

        if(flag.equals("1")){
            rec=getIntent().getStringExtra("receiver");
            receiverId.setText(rec);
        }
        diary=(EditText)findViewById(R.id.diary);
        fill=(EditText)findViewById(R.id.fill);
        Log.e("뭐냐 시발",diary.getText().toString());
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
        id=getIntent().getLongExtra("id",id);
    }


}





