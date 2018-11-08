package com.example.ki.a10_25.Task;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class JsonResult {
    private static JSONArray ja;
    private static ArrayList<String> date;
    private static JSONParser parser;
    private static ArrayList<DiaryWithFriend> diaryWithFriends;
    private static DiaryWithFriend vo;

    public static void setJa(JSONArray ja) throws ParseException, JSONException {
        JsonResult.ja = ja;

        diaryWithFriends=new ArrayList<>();
        date=new ArrayList<>();
        parser=new JSONParser();
        for(int i=0;i<ja.size();i++){
            String tmp=ja.get(i).toString();
            org.json.simple.JSONObject jo= (org.json.simple.JSONObject) parser.parse(tmp);
            String d= (String) jo.get("date");
            date.add(d);
            DiaryWithFriend dr=new DiaryWithFriend(jo.get("date").toString(),jo.get("receiver").toString(),jo.get("weather").toString(),jo.get("howru").toString(),jo.get("sendData").toString());
            diaryWithFriends.add(dr);
        }
    }
    public static ArrayList<DiaryWithFriend> getDiaryWithFriends() {
        return diaryWithFriends;
    }

    public static ArrayList<String> getDate() {
        return date;
    }
}
