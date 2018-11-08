package com.example.ki.a10_25;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ki.a10_25.FriendList.ListviewAdapter;
import com.example.ki.a10_25.FriendList.ListviewItem;
import com.example.ki.a10_25.Task.FriendsArray;
import com.example.ki.a10_25.Task.FriendsVO;
import com.example.ki.a10_25.Task.Url;
import com.example.ki.a10_25.Task.UserVO;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    private ArrayList<ListviewItem> arrayList;
    private View view;
    private String nick;
    private long id;
    private String url;
    private ImageView imageView;
    private TextView userId;
    private TextView userNick;
    private String result;
    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance(){
        Bundle args=new Bundle();
        MyFragment fragment=new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_my, container, false);
        initFragment();
        readJson read=new readJson(Url.getUrl()+"/getFriends?id="+id);
        Log.e("ssssss",Url.getUrl());
        read.execute();
        return view;
    }
    private void initFragment(){
        nick=UserVO.getNick();
        id=UserVO.getId();
        url=UserVO.getUrl();

        imageView=view.findViewById(R.id.imageView);
        userId=view.findViewById(R.id.userId);
        userNick=view.findViewById(R.id.userNick);

        Glide.with(getActivity()).load(url).into(imageView);
        userId.setText(Long.toString(id));
        userNick.setText(nick);

    }
    private List<FriendsVO> fa;

    public class readJson extends AsyncTask<Void, Void, String> {


        private String strUrl,strCookie;
        private URL Url;
        private ProgressDialog dialog;

        public readJson(String strUrl){
            this.strUrl=strUrl;
        };

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            try{
                Log.e("시발",strUrl);
                Url = new URL(strUrl); // URL화 한다.
                HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
                conn.setRequestMethod("GET"); // get방식 통신
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
            try {
                if(result!=null){
                    refineData();
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("파스","익셉션");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("파스","json");
            }
        }
        private JSONArray ja;
        private JSONObject jo;
        private void refineData() throws ParseException, JSONException {
            ListView listView=view.findViewById(R.id.listview);
            Log.e("data",result);
            JSONParser parser=new JSONParser();
            ja=(JSONArray) parser.parse(result);
            arrayList=new ArrayList<>();
            fa=new ArrayList<>();
            Log.e("dddddd",""+ja.size());
            for(int i=0;i<ja.size();i++){
                String tmp=ja.get(i).toString();
                jo=(JSONObject)parser.parse(tmp);
                Log.e("dkkdkdkdk",jo.toString());
                ListviewItem data=new ListviewItem(jo.get("profileimage").toString(),jo.get("nickname").toString());
                arrayList.add(data);
                FriendsVO vo=new FriendsVO(jo.get("id").toString(),jo.get("nickname").toString(),jo.get("profileimage").toString(),jo.get("flag").toString());
                fa.add(vo);
            }
            ListviewAdapter adapter=new ListviewAdapter(getActivity(),R.layout.item,arrayList);
            listView.setAdapter(adapter);
            FriendsArray.setFa(fa);
        }


    }
}

