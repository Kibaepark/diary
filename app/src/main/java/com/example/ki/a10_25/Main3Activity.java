package com.example.ki.a10_25;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    private String date;
    private TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        date=getIntent().getStringExtra("date");
        tw=(TextView)findViewById(R.id.text);
        tw.setText(date);
    }
}
