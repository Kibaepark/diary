package com.example.ki.a10_25;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ki.a10_25.DiaryList.DiaryListViewAdapter;
import com.example.ki.a10_25.DiaryList.DiaryListViewItem;
import com.example.ki.a10_25.FriendList.ListviewAdapter;
import com.example.ki.a10_25.FriendList.ListviewItem;
import com.example.ki.a10_25.Task.FriendsArray;
import com.example.ki.a10_25.Task.FriendsVO;
import com.example.ki.a10_25.Task.JsonResult;
import com.example.ki.a10_25.Task.Url;
import com.example.ki.a10_25.Task.UserVO;

import org.json.JSONException;
import org.json.simple.JSONArray;
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

public class DiaryListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<DiaryListViewItem> arrayList;
    private ListView listView;
    private long id;
    private String result,flag;
    private List<FriendsVO> fa;
    private Button newbtn;
    private String[] name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        id=UserVO.getId();
        new readJson(Url.getUrl()+"/getFriends?id="+id).execute();
    }
    private void init(){
        flag=getIntent().getStringExtra("flag");
        ListView listView=(ListView)findViewById(R.id.listView);
        arrayList=new ArrayList<>();
        newbtn=findViewById(R.id.new_btn);
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DiaryListActivity.this,diaryWriteActivity.class);
                intent.putExtra("flag","0");
                startActivity(intent);
            }
        });
        if(flag.equals("1")){
            newbtn.setVisibility(View.VISIBLE);
        }
        name=new String[]{
                "zi존일기","T없e맑은일기","난 ㄱr끔 눈물을 흘린다","유일한 ㅁr약","S2이쁘ㄴㅣS2","zl지존lz","zx지존x법사zx","♡㉧=il㉥㉥i겅듀♡§","카드값줘체리","LH7rcHnㅔ"
                ,"㉴己Б했던기억들도〃ユェй란㈔乙占도♡","부릅뜨니숲이었어","당신의 눈동자에 건배"
        };
        int s=(int)(Math.random()*13);
        if(FriendsArray.getFa()!=null){
            for(int i=0;i<FriendsArray.getFa().size();i++){
                DiaryListViewItem data=new DiaryListViewItem(UserVO.getNick(),FriendsArray.getFa().get(i).getNick(),name[s]);
                arrayList.add(data);
            }
        }
        DiaryListViewAdapter adapter=new DiaryListViewAdapter(this,R.layout.diary_list_item,arrayList);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(flag.equals("0")){//일기 읽기
            Intent intent=new Intent(DiaryListActivity.this, CalendarActivity.class);
            intent.putExtra("user1",UserVO.getId());
            intent.putExtra("user2",position);
            startActivity(intent);
        }else{//일기쓰기
            Log.e("왜안돼시발",FriendsArray.getFa().get(position).getFlag());
            if(FriendsArray.getFa().get(position).getFlag().equals("0")){
                Toast.makeText(this,"상대의 일기가 아직 없어 작성이 불가합니다. 상대의 일기가 올 때까지 기다리세요",Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent =new Intent(DiaryListActivity.this, diaryWriteActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("receiver",FriendsArray.getFa().get(position).getId());
                intent.putExtra("flag","1");
                startActivity(intent);
            }
        }
    }

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
            JSONParser parser = new JSONParser();
            try {
                JSONArray ja = (JSONArray) parser.parse(aVoid);
                Log.e("jajaja", ja.toString());
                List<FriendsVO> vos = new ArrayList<>();
                for (int i = 0; i < ja.size(); i++) {
                    String tmp = ja.get(i).toString();
                    JSONObject jo = (JSONObject) parser.parse(tmp);
                    FriendsVO fo = new FriendsVO(jo.get("id").toString(), jo.get("nickname").toString(), jo.get("profileimage").toString(), jo.get("flag").toString());
                    vos.add(fo);
                }
                FriendsArray.setFa(vos);
                init();
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }


}
