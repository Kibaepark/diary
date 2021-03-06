package com.example.ki.a10_25.Task;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class readJson extends AsyncTask<Void, Void, String> {


    private String strUrl, result,strCookie;
    private URL Url;

    public readJson(String strUrl){
        this.strUrl=strUrl;
    };


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(Void... voids) {
        try{
            Log.e("시발",strUrl);
            Url = new URL(strUrl); // URL화 한다.
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
            conn.setRequestMethod("GET"); // get방식 통신
            conn.setDoInput(true); // 읽기모드 지정
            conn.setUseCaches(false); // 캐싱데이터를 받을지 안받을지
            conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정

            strCookie = conn.getHeaderField("Set-Cookie"); //쿠키데이터 보관
            //데이터 불러오기
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

    }

    public String getResult() {
        return result;
    }
}
