package com.niit.software1721.entity;

import java.io.Serializable;

public class History implements Serializable {
    private String username;
    private String playTime;
    private String title;
    private String videoTitle;

    public History() {

    }

    public History(String username, String playTime, String title, String videoTitle) {
        this.username = username;
        this.playTime = playTime;
        this.title = title;
        this.videoTitle = videoTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    @Override
    public String toString() {
        return "History{" +
                "username='" + username + '\'' +
                ", playTime='" + playTime + '\'' +
                ", title='" + title + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                '}';
    }
}
