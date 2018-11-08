package com.example.ki.a10_25;

public class WeatherSpinnerItem {
    private int image,position;
    private String text;
    public WeatherSpinnerItem(int image, String text,int position){
        this.image=image;
        this.text=text;
        this.position=position;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }
}
