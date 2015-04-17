package com.sina.alarm.db;

import android.provider.BaseColumns;

public final class AlarmContract {
	 public AlarmContract() {}
	 
	    public static abstract class Alarm implements BaseColumns {
	        public static final String TABLE_NAME = "alarm";
	        public static final String COLUMN_NAME_ALARM_NAME = "name";
	        public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
	        public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
	        public static final String COLUMN_NAME_ALARM_REPEAT_DAYS = "days";
	        public static final String COLUMN_NAME_ALARM_REPEAT_WEEKLY = "weekly";
	        public static final String COLUMN_NAME_ALARM_TONE = "tone";
	        public static final String COLUMN_NAME_ALARM_ENABLED = "isEnabled";
	    }
	    
	    public static abstract class News implements BaseColumns {
	        public static final String TABLE_NAME = "news";
	        public static final String COLUMN_NAME_NEWS_ID = "news_id";
	        public static final String COLUMN_NAME_NEWS_TITLE = "title";
	        public static final String COLUMN_NAME_NEWS_CONTENT = "content";
	        public static final String COLUMN_NAME_NEWS_READ = "read_flag";
	    }
}
