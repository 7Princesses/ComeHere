package com.example.comehere;

public class MainData {

    private String imageIv; //list에 들어가는 사진
    private String titleTv; //list 제목
    private int priceTv; //가격
    private String unitTv; //단위
    private int stick; //붙은수량
    private int remain; //남은수량

    public MainData(String imageIv, String titleTv, int priceTv, String unitTv, int stick, int remain) {
        this.imageIv = imageIv;
        this.titleTv = titleTv;
        this.priceTv = priceTv;
        this.unitTv = unitTv;
        this.stick = stick;
        this.remain = remain;
    }


    public String getImageIv() {
        return imageIv;
    }

    public void setImageIv(String imageIv) {
        this.imageIv = imageIv;
    }

    public String getTitleTv() {
        return titleTv;
    }

    public void setTitleTv(String titleTv) {
        this.titleTv = titleTv;
    }

    public int getPriceTv() {
        return priceTv;
    }

    public void setPriceTv(int priceTv) {
        this.priceTv = priceTv;
    }

    public String getUnitTv() {
        return unitTv;
    }

    public void setUnitTv(String unitTv) {
        this.unitTv = unitTv;
    }

    public int getStick() {
        return stick;
    }

    public void setStick(int stick) {
        this.stick = stick;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }
}
