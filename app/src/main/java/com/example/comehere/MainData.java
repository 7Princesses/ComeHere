package com.example.comehere;

public class MainData {

    private int photo; //용품 사진
    private String title; //제목
    private int price; //단위 당 가격
    private String unit; //단위 이름
    private int remain; //남은 수량

    public MainData(int photo, String title, int price, String unit, int remain) {
        this.photo = photo;
        this.title = title;
        this.price = price;
        this.unit = unit;
        this.remain = remain;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit_name) {
        this.unit = unit_name;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

}
