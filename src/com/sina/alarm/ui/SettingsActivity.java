package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.ui.adapter.ChannelAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener, OnItemClickListener {

	public static void startActivity(Context ctx) {
		Intent intent = new Intent();
		intent.setClass(ctx, SettingsActivity.class);
		ctx.startActivity(intent);
	}
	
	private GridView mGridView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_settings);
        setupActionBar();
        initView();
	}
	
	private void setupActionBar() {
    	ImageView imv = (ImageView)this.findViewById(R.id.imv_back);
    	imv.setVisibility(View.VISIBLE);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_alarm);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_share);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_settings);
    	imv.setVisibility(View.GONE);
    	
    	TextView tv = (TextView)this.findViewById(R.id.tv_actionbar_title);
    	tv.setText(R.string.settings);
    }
	
	private void initView() {
		mGridView = (GridView)this.findViewById(R.id.grid_channel);
		mGridView.setAdapter(new ChannelAdapter(this));
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imv_back:
			this.finish();
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ImageView selectImv = (ImageView)view.findViewById(R.id.imv_choose);
		if (selectImv.getVisibility() == View.VISIBLE) {
			selectImv.setVisibility(View.GONE);
		} else {
			selectImv.setVisibility(View.VISIBLE);	
		}
	}
}
