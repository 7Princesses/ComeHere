package com.example.comehere;

public class MyPageData {
    private String userNickname;
    private String userId;
    private String school;
    private int identity;

    public MyPageData(String userNickname, String userId, String school, int identity) {
        this.userNickname = userNickname;
        this.userId = userId;
        this.school = school;
        this.identity = identity;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }
}