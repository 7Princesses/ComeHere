package com.example.comehere;

import android.content.Intent;

public class User {
    private String UID;
    private String nickname;
    private String school;
    private Integer studentId;
    private String studentIdCard;

    public User() {}

    public User(String uid, String nickname, String school, Integer studentId, String studentIdCard) {
        this.UID = uid;
        this.nickname = nickname;
        this.school = school;
        this.studentId = studentId;
        this.studentIdCard = studentIdCard;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentIdCard() {
        return studentIdCard;
    }

    public void setStudentIdCard(String studentIdCard) {
        this.studentIdCard = studentIdCard;
    }
}
