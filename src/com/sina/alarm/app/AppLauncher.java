package com.sina.alarm.app;

import java.util.ArrayList;
import java.util.List;

import com.sina.alarm.R;
import com.sina.alarm.bean.AudioNewsItem;

import android.app.Application;

public class AppLauncher extends Application {
	private static List<AudioNewsItem> mItems;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initAppData();
	}
	
	public static List<AudioNewsItem> getNewsItems() {
		return mItems;
	}
	
	public static AudioNewsItem getNewsItem(int id) {
		if (mItems == null) {
			return null;
		}
		return mItems.get(id);
	}
	
	private void initAppData() {
		if (mItems != null) {
			return;
		}
		
		mItems = new ArrayList<AudioNewsItem>();
		mItems.add(new AudioNewsItem(
				1,
				R.raw.audio_1,
				"李嘉诚15亿捐建寺庙曝光 每天公供400人参观",
				"由香港富商李嘉诚捐资15亿港元修建的大埔慈山寺今天向公众开放...",
				"由香港富商李嘉诚捐资15亿港元修建的大埔慈山寺今天向公众开放。寺院占地5万平方米，拥有佛光寺和南禅寺等名刹，以及全球第二高的观音圣像。寺庙每天仅有400个参观名额，每天早上8时开放网上预约，几分钟即宣告爆满。"));
		mItems.add(new AudioNewsItem(
				2,
				R.raw.audio_2,
				"中日韩将提出共同观测PM2.5",
				"中国、日本和韩国政府预计将在19号到30号于上海召开的环境部长会议上提出在PM2.5的共同观测方面展开合作...",
				"日经中文网报道，中国、日本和韩国政府预计将在19号到30号于上海召开的环境部长会议上提出在PM2.5的共同观测方面展开合作，PM2.5产生于汽车尾气等，是东亚地区大气污染日益扩大的原因。计划将合作事宜写入2015年到2019年的五年共同行动计划。虽然三国在历史等问题上仍存在对立，但可以从环境等必要共同应对的问题着手，扩大部长级对话。"));
		mItems.add(new AudioNewsItem(
				3,
				R.raw.audio_3,
				"浙江省温州市副市长孔海龙接受组织调查",
				"浙江省温州市副市长孔海龙涉嫌严重危机，目前正在接受组织调查...",
				"中央纪委监察部网站发布消息称，据浙江省纪委消息：浙江省温州市副市长孔海龙涉嫌严重危机，目前正在接受组织调查。"));
		mItems.add(new AudioNewsItem(
				4,
				R.raw.audio_4,
				"菲总统称中国对南海主权声索引发恐惧 中方回应",
				"菲律宾总统阿基诺称，中国对南海提出主权声索引发各国恐惧。中方的填海造地活动威胁国际航线和渔业活动安全...",
				"4月14日，菲律宾总统阿基诺称，中国对南海提出主权声索引发各国恐惧。中方的填海造地活动威胁国际航线和渔业活动安全。对此，外交部发言人洪磊4月15日表示，菲方指责毫无道理，中方南沙岛礁建设完全是主权范围内的事情，不影响也不针对任何国家，更不可能威胁国际航运线路和渔业活动安全。"));
	}
}
