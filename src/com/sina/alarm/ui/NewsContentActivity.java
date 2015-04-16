package com.sina.alarm.ui;

import com.sina.alarm.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class NewsContentActivity extends Activity implements OnClickListener {
	public static void startActivity(Context ctx) {
		Intent intent = new Intent();
		intent.setClass(ctx, NewsContentActivity.class);
		ctx.startActivity(intent);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_news_content);
        setupActionBar();
	}
	
	private void setupActionBar() {
    	ImageView imv = (ImageView)this.findViewById(R.id.imv_back);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_alarm);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_share);
    	imv.setVisibility(View.VISIBLE);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_settings);
    	imv.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
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
