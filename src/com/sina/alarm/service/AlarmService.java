package com.sina.alarm.service;

import com.sina.alarm.ui.MainActivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
	public static String TAG = AlarmService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String toneUriStr = null;
		if (intent != null) {
			toneUriStr = intent.getStringExtra(AlarmManagerHelper.TONE);	
		}
		MainActivity.startActivity(getApplicationContext(), 1, toneUriStr);
		AlarmManagerHelper.setAlarms(this);
		
		return super.onStartCommand(intent, flags, startId);
	}
}
