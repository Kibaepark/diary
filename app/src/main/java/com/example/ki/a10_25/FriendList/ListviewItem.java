package com.example.ki.a10_25.FriendList;

public class ListviewItem {
    private String name,str;
    public String getIcon(){return str;}
    public String getName(){return name;}
    public ListviewItem(String str,String name){
        this.str=str;
        this.name=name;
    }
}
