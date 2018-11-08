package com.example.ki.a10_25.DiaryList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ki.a10_25.R;

import java.util.ArrayList;

public class DiaryListViewAdapter extends BaseAdapter  {
    private LayoutInflater inflater;
    private ArrayList<DiaryListViewItem> data;
    private int layout;
    public DiaryListViewAdapter(Context context, int layout, ArrayList<DiaryListViewItem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
        Log.e("size,,,",Integer.toString(data.size()));
    }
    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position){return data.get(position).getDiaryname();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        DiaryListViewItem listviewitem = data.get(position);
        TextView user1 = (TextView) convertView.findViewById(R.id.user1);
        user1.setText(listviewitem.getUser1());
        TextView user2 = (TextView) convertView.findViewById(R.id.user2);
        user2.setText(listviewitem.getUser2());
        TextView diaryname = (TextView) convertView.findViewById(R.id.diaryname);
        diaryname.setText(listviewitem.getDiaryname());
        return convertView;
    }


}
