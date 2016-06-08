package com.qdqy.admin.sinaapp.newsdata;

import android.graphics.Bitmap;

public class News {

    public String flag;
    public String title;
    public String date;
    public String source;
    public String summary;
    public int comment;
    public String url;
    public Bitmap photo;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getFlag(){
        return flag;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getSummary() {
        return summary;
    }

    public int getComment() { return comment; }

    public String getUrl() { return url; }

}

