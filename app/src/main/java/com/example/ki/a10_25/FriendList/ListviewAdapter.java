package com.example.ki.a10_25.FriendList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ki.a10_25.R;

import java.util.ArrayList;

public class ListviewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ListviewItem> data;
    private int layout;
    private Context context;
    public ListviewAdapter(Context context, int layout, ArrayList<ListviewItem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
        this.context=context;
        Log.e("size,,,",Integer.toString(data.size()));
    }
    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position){return data.get(position).getName();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        ListviewItem listviewitem=data.get(position);
        ImageView icon=(ImageView)convertView.findViewById(R.id.imageView);
        Glide.with(context).load(listviewitem.getIcon()).into(icon);
        TextView name=(TextView)convertView.findViewById(R.id.textView);
        name.setText(listviewitem.getName());
        return convertView;
    }
}
