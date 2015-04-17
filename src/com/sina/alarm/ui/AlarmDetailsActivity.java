package com.sina.alarm.ui;

import com.sina.alarm.R;
import com.sina.alarm.db.AlarmDBHelper;
import com.sina.alarm.model.AlarmModel;
import com.sina.alarm.service.AlarmManagerHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmDetailsActivity extends Activity implements OnClickListener {
	
	public static void startActivity(Context ctx, long id) {
		Intent intent = new Intent();
		intent.setClass(ctx, AlarmDetailsActivity.class);
		intent.putExtra("id", id);
		ctx.startActivity(intent);
	}
	
	private AlarmModel alarmDetails;
	AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	
	private TimePicker timePicker;
	private EditText editName;
	private CustomSwitch chkWeekly;
	private CustomSwitch chkSunday;
	private CustomSwitch chkMonday;
	private CustomSwitch chkTuesday;
	private CustomSwitch chkWednesday;
	private CustomSwitch chkThursday;
	private CustomSwitch chkFriday;
	private CustomSwitch chkSaturday;
	private TextView txtToneSelection;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_details);
		setupActionBar();
		initViews();
		initData();
	}
	
	private void setupActionBar() {
    	ImageView imv = (ImageView)this.findViewById(R.id.imv_back);
    	imv.setOnClickListener(this);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_alarm);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_settings);
    	imv.setVisibility(View.GONE);
    	
    	imv = (ImageView)this.findViewById(R.id.imv_save_alarm);
    	imv.setVisibility(View.VISIBLE);
    	imv.setOnClickListener(this);
    	
    	TextView tv = (TextView)this.findViewById(R.id.tv_actionbar_title);
    	tv.setText(R.string.add_alarm);
    }
	
	private void initViews() {
		timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
		editName = (EditText) findViewById(R.id.alarm_details_name);
		chkWeekly = (CustomSwitch) findViewById(R.id.alarm_details_repeat_weekly);
		chkSunday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_sunday);
		chkMonday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_monday);
		chkTuesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_tuesday);
		chkWednesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_wednesday);
		chkThursday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_thursday);
		chkFriday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_friday);
		chkSaturday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_saturday);
		txtToneSelection = (TextView)findViewById(R.id.alarm_label_tone_selection);
		LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
		ringToneContainer.setOnClickListener(this);
	}
	
	private void initData() {
		long id = getIntent().getExtras().getLong("id");
		if (id == -1) {
			alarmDetails = new AlarmModel();
		} else {
			alarmDetails = dbHelper.getAlarm(id);
			
			timePicker.setCurrentMinute(alarmDetails.timeMinute);
			timePicker.setCurrentHour(alarmDetails.timeHour);
			
			editName.setText(alarmDetails.name);
			chkWeekly.setChecked(alarmDetails.repeatWeekly);
			chkSunday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
			chkMonday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
			chkTuesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
			chkWednesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
			chkThursday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
			chkFriday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRDIAY));
			
			txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode != RESULT_OK) {
			return;
		}
		
		switch (requestCode) {
		case 1:
			alarmDetails.alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
			txtToneSelection.setText(RingtoneManager.getRingtone(this,
			alarmDetails.alarmTone).getTitle(this));
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imv_back:
			this.finish();
			break;
		case R.id.imv_save_alarm:
			saveAlarm();
			break;
		case R.id.alarm_ringtone_container:
			Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
		    startActivityForResult(intent , 1);
			break;

		default:
			break;
		}
	}
	
	private void saveAlarm() {
		updateModelFromLayout();
		Log.i("abc123", "save alarmed");
		
		AlarmManagerHelper.cancelAlarms(this);
		
		if (alarmDetails.id < 0) {
			dbHelper.createAlarm(alarmDetails);
		} else {
			dbHelper.updateAlarm(alarmDetails);
		}
		
		AlarmManagerHelper.setAlarms(this);
		
		setResult(RESULT_OK);
		finish();
	}
	
	private void updateModelFromLayout() {
		alarmDetails.timeMinute = timePicker.getCurrentMinute().intValue();
		alarmDetails.timeHour = timePicker.getCurrentHour().intValue();
		
		alarmDetails.name = editName.getText().toString();
		
		alarmDetails.repeatWeekly = chkWeekly.isChecked();
		
		alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, chkSunday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.MONDAY, chkMonday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, chkTuesday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, chkWednesday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, chkThursday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.FRDIAY, chkFriday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, chkSaturday.isChecked());
		
		alarmDetails.isEnabled = true;
	}
}
