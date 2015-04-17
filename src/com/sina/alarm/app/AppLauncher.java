package com.sina.alarm.app;

import com.sina.alarm.db.AlarmDBHelper;
import com.sina.alarm.model.NewsModel;
import com.sina.alarm.sharedPrefs.NewsAlarmPrefConstants;
import com.sina.alarm.sharedPrefs.NewsAlarmPrefConstants.AppStateKeys;
import com.sina.alarm.sharedPrefs.NewsAlarmPreferenceUtils;

import android.app.Application;

public class AppLauncher extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		initAppData();
	}
	
	private void initAppData() {
		boolean firstLaunch = NewsAlarmPreferenceUtils.getBoolean(this,
				NewsAlarmPrefConstants.sAppStatePrefName, AppStateKeys.sFirstLaunch, true);
		if (!firstLaunch) {
			return;
		}
		
		AlarmDBHelper dbHelper = new AlarmDBHelper(this);
		NewsModel model1 = new NewsModel();
		model1.setNews_id(1);
		model1.setTitle("温州市副市长接受组织调查");
		model1.setContent("中央纪委监察部网站发布消息称，据浙江省纪委消息：浙江省温州市副市长孔海龙涉嫌严重危机，目前正在接受组织调查。");
		model1.setRead(false);
		dbHelper.insertNews(model1);
		
		NewsModel model2 = new NewsModel();
		model2.setNews_id(2);
		model2.setTitle("中日韩将在PM2.5领域进行合作");
		model2.setContent("日经中文网报道，中国、日本和韩国政府预计将在19号到30号于上海召开的环境部长会议上提出在PM2.5的共同观测方面展开合作，PM2.5产生于汽车尾气等，是东亚地区大气污染日益扩大的原因。计划将合作事宜写入2015年到2019年的五年共同行动计划。虽然三国在历史等问题上仍存在对立，但可以从环境等必要共同应对的问题着手，扩大部长级对话。");
		model2.setRead(false);
		dbHelper.insertNews(model2);
		
		NewsModel model3 = new NewsModel();
		model3.setNews_id(3);
		model3.setTitle("李嘉诚捐资15亿兴建寺庙今开放");
		model3.setContent("由香港富商李嘉诚捐资15亿港元修建的大埔慈山寺今天向公众开放。寺院占地5万平方米，拥有佛光寺和南禅寺等名刹，以及全球第二高的观音圣像。寺庙每天仅有400个参观名额，每天早上8时开放网上预约，几分钟即宣告爆满。");
		model3.setRead(false);
		dbHelper.insertNews(model3);
		
		NewsModel model4 = new NewsModel();
		model4.setNews_id(4);
		model4.setTitle("菲称中国南海问题让人恐惧 外交部：指责毫无道理");
		model4.setContent("4月14日，菲律宾总统阿基诺称，中国对南海提出主权声索引发各国恐惧。中方的填海造地活动威胁国际航线和渔业活动安全。对此，外交部发言人洪磊4月15日表示，菲方指责毫无道理，中方南沙岛礁建设完全是主权范围内的事情，不影响也不针对任何国家，更不可能威胁国际航运线路和渔业活动安全。");
		model4.setRead(false);
		dbHelper.insertNews(model4);
		
		NewsAlarmPreferenceUtils.putBoolean(this,
				NewsAlarmPrefConstants.sAppStatePrefName, AppStateKeys.sFirstLaunch, false);
	}
}
