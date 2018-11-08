package com.example.ki.a10_25.Task;

import java.util.List;

public class FriendsArray {
    private static List<FriendsVO> fa;

    public static void setFa(List<FriendsVO> fa) {
        FriendsArray.fa = fa;
    }

    public static List<FriendsVO> getFa() {
        return fa;
    }
}
