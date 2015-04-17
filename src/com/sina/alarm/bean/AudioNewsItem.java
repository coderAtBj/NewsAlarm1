package com.sina.alarm.bean;


public class AudioNewsItem {
    final private int id;

    private int audioResource;
    private String title;
    private String description;
    private String content;

    public AudioNewsItem(int id) {
        this.id = id;
    }

    public AudioNewsItem(int id, int audioResId, String title, String description, String content) {
        this(id);
        this.audioResource = audioResId;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getAudioResource() {
        return audioResource;
    }

    public void setAudioResource(int audioUri) {
        this.audioResource = audioUri;
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
