package com.sina.alarm.ui;

import com.sina.alarm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    
    private void initView() {
    	Button btn = (Button)this.findViewById(R.id.btn_add_alarm);
    	btn.setOnClickListener(this);
    	
    	btn = (Button)this.findViewById(R.id.btn_alarm_list);
    	btn.setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_add_alarm:
			AlarmDetailsActivity.startActivity(this, -1);
			break;
		case R.id.btn_alarm_list:
			AlarmListActivity.startActvity(this);
			break;
		default:
			break;
		}
	}
}