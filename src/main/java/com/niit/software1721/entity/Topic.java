package com.niit.software1721.entity;

import java.io.Serializable;

public class Topic implements Serializable {
    private int id;
    private String title;
    private String subTitle;
    private int background;

    public Topic(String title, String subTitle, int background) {
        this.title = title;
        this.subTitle = subTitle;
        this.background = background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public Topic() {

    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", background=" + background +
                '}';
    }
}
