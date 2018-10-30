package com.example.ki.a10_25;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.response.model.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class diaryWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private String date;
    private TextView tw;
    private EditText et,rId;
    private String str,rec;
    private Button sendBtn;
    private Dialog dialog;
    private UserProfile userProfile;
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        date=getIntent().getStringExtra("date");
        tw=(TextView)findViewById(R.id.text);
        tw.setText(date);
        et=(EditText)findViewById(R.id.write);
        sendBtn=(Button)findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(this);
        id=getIntent().getLongExtra("id",id);
        rId=(EditText)findViewById(R.id.receiver_id);
        Log.e("id",Long.toString(id));
    }
    @Override
    public void onClick(View v) {
        str=et.getText().toString();
        rec=rId.getText().toString();
        new AlertDialog.Builder(this)
                .setTitle("일기작성완료")
                .setMessage("전송하시겠습니까?")
                .setIcon(android.R.drawable.ic_menu_send)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 확인시 처리 로직
                        Toast.makeText(diaryWriteActivity.this, "전송을 완료했습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(diaryWriteActivity.this, diaryReadActivity.class);
                        makeJson();
                        try {
                            String str=sendDiary.toString();
                            new jsonSend().execute();

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

    Map<String, Object> sendDiary;
    private void makeJson() {
        sendDiary= new HashMap<>();
            sendDiary.put("date", date);
            sendDiary.put("sender",id);
            sendDiary.put("receiver",rec);
            sendDiary.put("sendData",str);

        Log.e("json",sendDiary.toString());
    }

    public class jsonSend extends AsyncTask<Void, Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                String url = "http://192.168.51.14:3000/sendJson";
                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                StringBuilder postData =new StringBuilder();
                for(Map.Entry<String,Object> param:sendDiary.entrySet()){
                    if(postData.length()!=0)postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
                }
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
//                conn.setRequestProperty("Content-Type","application/json");
                Log.e("메롱",sendDiary.toString());
                byte[] outputInBytes = postData.toString().getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write( outputInBytes );
                os.flush();
                String response;
                int responseCode=conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;
                    int nLength = 0;
                    while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byteData = baos.toByteArray();

                    response = new String(byteData);

                    Log.i("하하하", "DATA response = " + response);
                }
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}





