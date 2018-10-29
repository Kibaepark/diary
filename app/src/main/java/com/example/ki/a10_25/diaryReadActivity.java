package com.example.ki.a10_25;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class diaryReadActivity extends AppCompatActivity {

    private String date;
    private TextView tw;
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        date=getIntent().getStringExtra("date");
        tw=(TextView)findViewById(R.id.text);
        tw.setText(date);
        id=getIntent().getLongExtra("id",id);
        Log.e("idd",Long.toString(getIntent().getLongExtra("id",id)));
    }
}
