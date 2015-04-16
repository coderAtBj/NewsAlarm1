package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.db.AlarmDBHelper;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
        
        getActionBar().setTitle("Alarm List");
		getActionBar().setDisplayHomeAsUpEnabled(true);
        
        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());
        setListAdapter(mAdapter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	        case android.R.id.home:
	            this.finish();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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

}
