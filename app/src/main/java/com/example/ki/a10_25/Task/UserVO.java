package com.example.ki.a10_25.Task;

public class UserVO {
    private static long id;
    private static String nick;
    private static String url;

    public static void setId(long userid) {
        id=userid;
    }

    public static void setNick(String usernick) {
        nick=usernick;
    }

    public static void setUrl(String userurl) {
        url=userurl;
    }

    public static long getId() {
        return id;
    }

    public static String getNick() {
        return nick;
    }

    public static String getUrl() {
        return url;
    }
}
