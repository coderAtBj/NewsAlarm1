package com.sina.alarm.bean;

import android.net.Uri;

public class AudioNewsItem {
    final private int id;

    private Uri audioUri;
    private String title;
    private String description;
    private String content;

    public AudioNewsItem(int id) {
        this.id = id;
    }

    public AudioNewsItem(int id, Uri audioUri, String title, String description, String content) {
        this(id);
        this.audioUri = audioUri;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Uri getAudioUri() {
        return audioUri;
    }

    public void setAudioUri(Uri audioUri) {
        this.audioUri = audioUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
