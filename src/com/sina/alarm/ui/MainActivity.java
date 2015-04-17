package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.app.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {
	public static void startActivity(Context ctx, long newsId) {
		Intent intent = new Intent();
		intent.setClass(ctx, MainActivity.class);
		intent.putExtra(Constants.sNewsIdKey, newsId);
		if (!(ctx instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		ctx.startActivity(intent);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupActionBar();
    }
    
    private void initView() {
    	//TODO:
    }
    
    private void setupActionBar() {
    	ImageView imv = (ImageView)this.findViewById(R.id.imv_back);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_alarm);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_settings);
    	imv.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imv_alarm:
			AlarmListActivity.startActvity(this);
			break;
		case R.id.imv_settings:
			//TODO: tempory here.
			NewsContentActivity.startActivity(this, 1);
			break;
		default:
			break;
		}
	}
}