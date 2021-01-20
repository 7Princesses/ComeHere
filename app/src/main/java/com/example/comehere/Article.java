package com.example.comehere;

public class Article {
    private String articleTitle;
    private String productName;
    private Integer totalPrice;
    private String URL;
    private String tradePlace;
    private String category;
    private Integer productCount;
    private String unit;
    private String content;
    private String UID;

    public Article() {}
    public Article(String articleCode, String productName, Integer totalPrice, String URL, String tradePlace, String category, Integer productCount, String unit, String content, String UID) {
        this.articleTitle = articleCode;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.URL = URL;
        this.tradePlace = tradePlace;
        this.category = category;
        this.productCount = productCount;
        this.unit = unit;
        this.content = content;
        this.UID = UID;
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

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}