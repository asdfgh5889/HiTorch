package com.eldor.hitorch.model;

public class Plan {

    private String date;
    private int type;
    private String title;
    private String location;
    private String money;
    private boolean isComplete;

    public Plan(String date, int type, String title, String location, String money) {
        this.date = date;
        this.type = type;
        this.title = title;
        this.location = location;
        this.money = money;
        this.isComplete = false;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

}
