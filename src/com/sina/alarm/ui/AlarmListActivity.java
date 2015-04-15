package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.db.AlarmDBHelper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmListActivity extends ListActivity {
	public static void startActvity(Context ctx) {
		Intent intent = new Intent();
		intent.setClass(ctx, AlarmListActivity.class);
		ctx.startActivity(intent);
	}
	
	private AlarmListAdapter mAdapter;
	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_list);
        
        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());
        setListAdapter(mAdapter);
	}

}
