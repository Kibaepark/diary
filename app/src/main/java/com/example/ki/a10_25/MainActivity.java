package com.example.ki.a10_25;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePicker datePicker;//날짜를 선택하는 달력
    private Button button1;
    private Button button2;
    private long id;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        id=getIntent().getLongExtra("id",id);
        Log.e("id",Long.toString(getIntent().getLongExtra("id",id)));
        datePicker=(DatePicker)findViewById(R.id.datePicker);
        button1  =(Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 =(Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String result = String.format("%d-%d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
        Intent intent;
        if (v.getId()==R.id.button1){
            intent = new Intent(MainActivity.this, diaryReadActivity.class );
            intent.putExtra("date",result);
            intent.putExtra("id",id);
            startActivity(intent);
        }
        else if (v.getId()==R.id.button2){
            intent = new Intent(MainActivity.this, diaryWriteActivity.class);
            intent.putExtra("date",result);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }
}
