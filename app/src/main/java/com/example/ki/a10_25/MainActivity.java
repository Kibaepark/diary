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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;//날짜를 선택하는 달력
    private Button button1;
    private Button button2;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker=(DatePicker)findViewById(R.id.datePicker);

        button1  =(Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = String.format("%d-%d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class );
                intent.putExtra("date",result);
                startActivity(intent);
            }
        });
        button2 =(Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = String.format("%d-%d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                intent.putExtra("date",result);
                startActivity(intent);
            }
        });



    }
}
