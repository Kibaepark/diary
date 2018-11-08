package com.example.ki.a10_25;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WeatherSpinnerAdapter extends BaseAdapter {

    private List<WeatherSpinnerItem> data;
    private Context context;


    public WeatherSpinnerAdapter(List<WeatherSpinnerItem> data,Context context){
        this.data=data;
        this.context=context;
        for (int i=0;i<data.size();i++){
            Log.e("dataaaaaaaaaaaa",data.get(i).getText());
        }
    };
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.weather_spinner_item,null);
        }
        TextView tw=convertView.findViewById(R.id.text);
        ImageView iv=convertView.findViewById(R.id.image);
        tw.setText(data.get(position).getText());
        iv.setImageResource(data.get(position).getImage());



        return convertView;
    }
}
