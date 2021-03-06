package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.db.AlarmDBHelper;
import com.sina.alarm.model.AlarmModel;
import com.sina.alarm.service.AlarmManagerHelper;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getString(R.string.remove_confirm))
    		.setTitle(getString(R.string.remove))
    		.setCancelable(true)
    		.setNegativeButton(getString(R.string.cancel), null)
    		.setPositiveButton(getString(R.string.ok),
    				new DialogInterface.OnClickListener() {
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
			case R.id.imv_add_alarm:
				startAlarmDetailActivity(-1);
				break;
			default:
				break;
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (resultCode == RESULT_OK) {
    		mAdapter.setAlarms(dbHelper.getAlarms());
    		mAdapter.notifyDataSetChanged();
    	}
    }
	
	private void startAlarmDetailActivity(long id) {
		Intent intent = new Intent();
		intent.setClass(this, AlarmDetailsActivity.class);
		intent.putExtra("id", id);
		this.startActivityForResult(intent, 0);
	}

	private void setupActionBar() {
    	ImageView imv = (ImageView)this.findViewById(R.id.imv_back);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_alarm);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_settings);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_add_alarm);
    	imv.setVisibility(View.VISIBLE);
    	imv.setOnClickListener(this);
    	
    	TextView tv = (TextView)this.findViewById(R.id.tv_actionbar_title);
    	tv.setText(R.string.alarm_list);
    }

}
