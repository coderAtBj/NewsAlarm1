package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.db.AlarmDBHelper;
import com.sina.alarm.db.AlarmModel;
import com.sina.alarm.service.AlarmManagerHelper;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmListActivity extends ListActivity implements OnClickListener {
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
        
        setupActionBar();
        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());
        setListAdapter(mAdapter);
	}
	
	public void deleteAlarm(long id) {
    	final long alarmId = id;
    	Log.i(null, "1234567");
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Do you want delete?").setTitle("Truely set?").setCancelable(true)
    	.setNegativeButton("Cancel", null)
    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dbHelper.deleteAlarm(alarmId);
				mAdapter.setAlarms(dbHelper.getAlarms());
				mAdapter.notifyDataSetChanged();
			}
		}).show();
    }
	
	public void setAlarmEnabled(long id, boolean isEnabled) {
    	AlarmManagerHelper.cancelAlarms(this);
    	
    	AlarmModel model = dbHelper.getAlarm(id);
    	model.isEnabled = isEnabled;
    	dbHelper.updateAlarm(model);
    	
    	mAdapter.setAlarms(dbHelper.getAlarms());
    	mAdapter.notifyDataSetChanged();
    	
    	AlarmManagerHelper.setAlarms(this);
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

	private void setupActionBar() {
    	ImageView imv = (ImageView)this.findViewById(R.id.imv_back);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_alarm);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_settings);
    	imv.setVisibility(View.GONE);
    	
    	TextView tv = (TextView)this.findViewById(R.id.tv_actionbar_title);
    	tv.setText(R.string.alarm_list);
    }

}
