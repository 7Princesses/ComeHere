package com.example.comehere;

import java.util.ArrayList;

public class Article {
    private String articleCode;
    private String productName;
    private Integer totalPrice;
    private String URL;
    private String tradePlace;
    private String category;
    private Integer productCount;
    private String unit;
    private String content;
    private String UID;
    private ArrayList<String> imageList;

    public Article() {}
    public Article(String articleCode, String productName, Integer totalPrice, String URL, String tradePlace, String category, Integer productCount, String unit, String content, String UID, ArrayList<String> imageList) {
        this.articleCode = articleCode;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.URL = URL;
        this.tradePlace = tradePlace;
        this.category = category;
        this.productCount = productCount;
        this.unit = unit;
        this.content = content;
        this.UID = UID;
        this.imageList = imageList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTradePlace() {
        return tradePlace;
    }

    public void setTradePlace(String tradePlace) {
        this.tradePlace = tradePlace;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }
}