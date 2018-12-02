package com.eldor.hitorch.model;

public class MyReviews {

    private String content;
    private String title;
    private String fullname;
    private String date;
    private double rate;

    public MyReviews(String title, String content, String fullname, String date, double rate) {
        this.content = content;
        this.title = title;
        this.fullname = fullname;
        this.date = date;
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
