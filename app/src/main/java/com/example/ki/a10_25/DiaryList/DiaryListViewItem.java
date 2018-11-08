package com.example.ki.a10_25.DiaryList;

public class DiaryListViewItem {
    private String user1, user2, diaryname;

    public String getDiaryname() {
        return diaryname;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public DiaryListViewItem(String user1, String user2, String diaryname){
        this.user1=user1;
        this.user2=user2;
        this.diaryname=diaryname;
    }
}
