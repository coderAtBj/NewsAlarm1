package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.app.Constants;
import com.sina.alarm.db.AlarmDBHelper;
import com.sina.alarm.model.NewsModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsContentActivity extends Activity implements OnClickListener {
	public static void startActivity(Context ctx, long newsId) {
		Intent intent = new Intent();
		intent.setClass(ctx, NewsContentActivity.class);
		ctx.startActivity(intent);
	}
	
	private long mNewsId;
	private NewsModel mNewsModel;
	private AlarmDBHelper mDbHelper;
	
	private TextView mNewsContentView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_news_content);
        initView();
        setupActionBar();
        parseData();
        updateView();
	}
	
	private void initView() {
		mNewsContentView = (TextView)this.findViewById(R.id.tv_news_content);
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
		mNewsId = intent.getLongExtra(Constants.sNewsIdKey, 1);
		
		if (mDbHelper == null) {
			mDbHelper = new AlarmDBHelper(this.getApplicationContext());
		}
		mNewsModel = mDbHelper.getNews(mNewsId);
	}
	
	private void updateView() {
		if (mNewsModel == null) {
			return;
		}
		
		mNewsContentView.setText(mNewsModel.getTitle() + "\n\n" + mNewsModel.getContent());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imv_back:
			this.finish();
			break;
		case R.id.imv_settings:
			//TODO:
			break;
		case R.id.imv_share:
			break;

		default:
			break;
		}
		
	}
}
