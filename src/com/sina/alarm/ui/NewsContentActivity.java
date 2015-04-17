package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.app.AppLauncher;
import com.sina.alarm.app.Constants;
import com.sina.alarm.bean.AudioNewsItem;
import com.sina.alarm.model.MediaManager;
import com.sina.alarm.model.MediaManager.OnProgressChangeListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsContentActivity extends Activity implements OnClickListener, OnProgressChangeListener{
	public static void startActivity(Context ctx, int newsId) {
		Intent intent = new Intent();
		intent.setClass(ctx, NewsContentActivity.class);
		intent.putExtra(Constants.sNewsIdKey, newsId);
		ctx.startActivity(intent);
	}
	
	private int mNewsId;
	private AudioNewsItem mNewsItem;
	
	private TextView mNewsTitleView;
	private TextView mNewsContentView;
	private TextView mPlayProgress;
	private TextView mPlayDuration;
	private ImageView mPlayBtn;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_news_content);
        initView();
        setupActionBar();
        parseData();
        updateView();
        MediaManager.getInstance().setOnProgressChangeListener(this);
	}
	
	private void initView() {
		mNewsTitleView = (TextView)this.findViewById(R.id.tv_news_title);
		mNewsContentView = (TextView)this.findViewById(R.id.tv_news_content);
		mPlayProgress = (TextView)this.findViewById(R.id.tv_played_time);
		mPlayDuration = (TextView)this.findViewById(R.id.tv_total_time);
		mPlayBtn = (ImageView)this.findViewById(R.id.imv_play);
		mPlayBtn.setOnClickListener(this);
	}
	
	private void setupActionBar() {
    	ImageView imv = (ImageView)this.findViewById(R.id.imv_back);
    	imv.setVisibility(View.VISIBLE);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_alarm);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_share);
    	imv.setVisibility(View.VISIBLE);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_settings);
    	imv.setOnClickListener(this);
    }
	
	private void parseData() {
		Intent intent = getIntent();
		mNewsId = intent.getIntExtra(Constants.sNewsIdKey, 1);
		
		mNewsItem = AppLauncher.getNewsItem(mNewsId);
	}
	
	private void updateView() {
		if (mNewsItem == null) {
			return;
		}
		
		mNewsTitleView.setText(mNewsItem.getTitle());
		mNewsContentView.setText(mNewsItem.getContent());
		if (MediaManager.getInstance().isPlaying()) {
			mPlayBtn.setImageResource(R.drawable.ic_pause_small);
		} else {
			mPlayBtn.setImageResource(R.drawable.ic_play_small);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imv_back:
			this.finish();
			break;
		case R.id.imv_settings:
			SettingsActivity.startActivity(this);
			break;
		case R.id.imv_share:
			break;
		case R.id.imv_play:
			if (MediaManager.getInstance().isPlaying()) {
				MediaManager.getInstance().pause();
				mPlayBtn.setImageResource(R.drawable.ic_play_small);
			} else {
				MediaManager.getInstance().start();
				mPlayBtn.setImageResource(R.drawable.ic_pause_small);
			}
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onProgressChange(int currentPosition, int duration) {
		NewsContentActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mPlayProgress.setText(MediaManager.getInstance().getProgressString());
		        mPlayDuration.setText(MediaManager.getInstance().getDurationString());
			}
		});
	}
}
