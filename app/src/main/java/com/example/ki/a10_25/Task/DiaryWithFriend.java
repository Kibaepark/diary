package com.example.ki.a10_25.Task;

public class DiaryWithFriend {
    private String date,receiver,weather,howru,mainText;
    public DiaryWithFriend(String date,String receiver,String weather,String howru,String mainText){
        this.date=date;
        this.receiver=receiver;
        this.weather=weather;
        this.howru=howru;
        this.mainText=mainText;
    }

    public String getDate() {
        return date;
    }

    public String getMainText() {
        return mainText;
    }

    public String getHowru() {
        return howru;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getWeather() {
        return weather;
    }
}
