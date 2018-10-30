package com.example.ki.a10_25;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class diaryReadActivity extends AppCompatActivity {

    private String date;
    private TextView tw,dd,s,r,d;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        id=getIntent().getLongExtra("id",id);
        new readJson().execute();
        date=getIntent().getStringExtra("date");
        tw=(TextView)findViewById(R.id.text);
        tw.setText(date);
        dd=(TextView) findViewById(R.id.diary_date);
        s=(TextView)findViewById(R.id.sender);
        r=(TextView)findViewById(R.id.receiver);
        d=(TextView)findViewById(R.id.diary);
        Log.e("idd",Long.toString(getIntent().getLongExtra("id",id)));
    }


    public class readJson extends AsyncTask<Void, Void, String> {

        private String strUrl, result,date,sender,receiver,diary,strCookie;
        private URL Url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            strUrl = "http://192.168.0.15:3000/receiveJson?id="+id;//탐색하고 싶은 URL이다.
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            try{
                Url = new URL(strUrl); // URL화 한다.
                HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
                conn.setRequestMethod("GET"); // get방식 통신
                conn.setDoInput(true); // 읽기모드 지정
                conn.setUseCaches(false); // 캐싱데이터를 받을지 안받을지
                conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정

                strCookie = conn.getHeaderField("Set-Cookie"); //쿠키데이터 보관

                for (Map.Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
                    for (String value : header.getValue()) {
                        System.out.println(header.getKey() + " : " + value);
                    }
                }
                try (InputStream in = conn.getInputStream();
                     ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                    byte[] buf = new byte[1024 * 8];
                    int length = 0;
                    while ((length = in.read(buf)) != -1) {
                        out.write(buf, 0, length);
                    }
                    result=new String(out.toByteArray(),"UTF-8");
                    System.out.println(new String(out.toByteArray(), "UTF-8"));
                }
            }catch(MalformedURLException | ProtocolException exception) {
                exception.printStackTrace();
            }catch(IOException io){
                io.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            System.out.println(result);
            JSONParser parser=new JSONParser();
            try {
                JSONObject jsonObject=(JSONObject)parser.parse(result);
                date=jsonObject.get("date").toString();
                receiver=jsonObject.get("receiver").toString();
                diary=jsonObject.get("sendData").toString();
                sender=jsonObject.get("sender").toString();
                dd.setText(date);
                s.setText(sender);
                r.setText(receiver);
                d.setText(diary);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }



    }
}





