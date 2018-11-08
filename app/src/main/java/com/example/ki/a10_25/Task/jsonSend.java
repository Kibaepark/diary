package com.example.ki.a10_25.Task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
 public class jsonSend extends AsyncTask<Void, Void,Void> {

    Map<String,Object> json;
    private String strUrl;
    public jsonSend(Map<String,Object> json,String strUrl){
        this.json=json;
        this.strUrl=strUrl;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {

            URL obj = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            StringBuilder postData =new StringBuilder();
            for(Map.Entry<String,Object> param:json.entrySet()){
                if(postData.length()!=0)postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
            }
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
//                conn.setRequestProperty("Content-Type","application/json");
            Log.e("메롱",json.toString());
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
