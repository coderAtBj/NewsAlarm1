package com.sina.alarm.model;

import com.sina.alarm.db.AlarmContract.News;

import android.content.ContentValues;
import android.database.Cursor;

public class NewsModel {
	private long id;
	private long news_id;
	private String title;
	private String content;
	private Boolean read;
	
	public NewsModel() {}
	
	public NewsModel(Cursor cur) {
		id = cur.getLong(cur.getColumnIndex(News._ID));
		news_id = cur.getLong(cur.getColumnIndex(News.COLUMN_NAME_NEWS_ID));
		title = cur.getString(cur.getColumnIndex(News.COLUMN_NAME_NEWS_TITLE));
		content = cur.getString(cur.getColumnIndex(News.COLUMN_NAME_NEWS_CONTENT));
		read = cur.getInt(cur.getColumnIndex(News.COLUMN_NAME_NEWS_CONTENT)) == 0 ? false : true;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNews_id() {
		return news_id;
	}
	public void setNews_id(long news_id) {
		this.news_id = news_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	
	public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		values.put(News.COLUMN_NAME_NEWS_ID, news_id);
		values.put(News.COLUMN_NAME_NEWS_TITLE, title);
		values.put(News.COLUMN_NAME_NEWS_CONTENT, content);
		values.put(News.COLUMN_NAME_NEWS_READ, read);
		return values;
	}
}
