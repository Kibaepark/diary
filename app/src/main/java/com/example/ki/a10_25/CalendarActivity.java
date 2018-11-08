package com.example.ki.a10_25;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ki.a10_25.Calendar.EventDecorator;
import com.example.ki.a10_25.Calendar.OneDayDecorator;
import com.example.ki.a10_25.Task.FriendsArray;
import com.example.ki.a10_25.Task.JsonResult;
import com.example.ki.a10_25.Task.Url;
import com.example.ki.a10_25.Task.UserVO;
import com.example.ki.a10_25.Task.readJson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

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
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    OneDayDecorator oneDayDecorator;
    private MaterialCalendarView materialCalendarView;
    private String result;
    private int position;
    private Calendar calendar;
    private JSONParser parser=new JSONParser();
    private JSONArray ja=new JSONArray();
    private ArrayList<String> dates;
    private ArrayList<CalendarDay> days;
    private CalendarDay calendarDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initFragment();
        position=getIntent().getIntExtra("user2",position);
        readJson read=new readJson(Url.getUrl()+"/receiveJson?id="+UserVO.getId() +"&receiver="+FriendsArray.getFa().get(position).getId());
        read.execute();

    }

    public void initFragment(){
        materialCalendarView=findViewById(R.id.calendarView);

    }

    public void initCalendar(){

        oneDayDecorator=new OneDayDecorator(this);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        EventDecorator eventDecorator=new EventDecorator(Color.RED,days,this);
        Log.e("아ㅏㅏㅏㅏㅏ개미친",dates.get(0));
        materialCalendarView.addDecorators(oneDayDecorator,eventDecorator);


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();
                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");
                String shot_Day = Year + "-" + Month + "-" + Day;
                Log.i("shot_Day test", shot_Day + "");
                materialCalendarView.clearSelection();
                int count=0;
                Log.e("  ",""+days.size());
                if (count==0){
                    for(int i=0;i<dates.size();i++){
                        Log.e(dates.get(i),shot_Day);
                        if(dates.get(i).equals(shot_Day)){

                            Intent intent=new Intent(CalendarActivity.this, diaryReadActivity.class);
                            intent.putExtra("position",i);
                            startActivity(intent);
                            finish();
                            break;
                        }else{
                            count++;
                        }

                    }
                }else if(count==dates.size()){
                    Toast.makeText(getApplicationContext(), "해당날짜에는 일기가없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void parsingJson() throws ParseException, JSONException {
        ja= (JSONArray) parser.parse(result);
        dates=new ArrayList<>();
        JsonResult.setJa(ja);
        dates=JsonResult.getDate();
        makeCalendarArray();
        initCalendar();
    }

    public void makeCalendarArray(){
        calendar=Calendar.getInstance();
        days=new ArrayList<>();
        for (int i=0;i<dates.size();i++){
            String[] tmp= dates.get(i).split("-");
            int year=Integer.parseInt(tmp[0]);
            int month=Integer.parseInt(String.valueOf(Integer.parseInt(tmp[1])-1));
            int day=Integer.parseInt(tmp[2]);
            calendar.set(year,month,day);
            calendarDay=CalendarDay.from(calendar);
            days.add(calendarDay);
        }
    }

    public class readJson extends AsyncTask<Void, Void, String> {


        private String strUrl,strCookie;
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
            try {
                parsingJson();

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
