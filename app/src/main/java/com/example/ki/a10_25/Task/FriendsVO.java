package com.example.ki.a10_25.Task;

public class FriendsVO {
    private  String id,nick,url,flag;

    public FriendsVO(String id, String nick, String url,String flag){
        this.id=id;
        this.nick=nick;
        this.url=url;
        this.flag=flag;

    }
    public String getUrl() {
        return url;
    }

    public String getNick() {
        return nick;
    }

    public String getId() {
        return id;
    }

    public String getFlag() {
        return flag;
    }
}
