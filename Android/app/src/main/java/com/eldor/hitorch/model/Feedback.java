package com.eldor.hitorch.model;

public class Feedback {

    private String feedText;

    private String theDate;

    private String title;

    private String sentimentRatio;

    public Feedback(String feedText, String theDate, String title, String sentimentRatio) {
        this.feedText = feedText;
        this.theDate = theDate;
        this.title = title;
        this.sentimentRatio = sentimentRatio;
    }

    public String getFeedText() {
        return feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    public String getTheDate() {
        return theDate;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSentimentRatio() {
        return sentimentRatio;
    }

    public void setSentimentRatio(String sentimentRatio) {
        this.sentimentRatio = sentimentRatio;
    }

}
