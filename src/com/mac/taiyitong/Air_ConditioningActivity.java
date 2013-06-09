package com.mac.taiyitong;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

import com.mac.taiyitong.broadcas.HomePressBroadcastReceiver;
import com.mac.taiyitong.cons.AC_Cmd;
import com.mac.taiyitong.cons.Light_Cmd;
import com.mac.taiyitong.util.WriteUtil;

public class Air_ConditioningActivity extends Activity {

	Button mode_Btn;
	Button wind_speed_Btn;
	Button sleep_Btn;
	Button t_add_Btn;
	Button t_sub_Btn;
	Button time_Btn;
	Button hour_Btn;
	Button minute_Btn;
	Button clear_Btn;
	Button wind_direction_Btn;
	Button close_Btn;
	Button light_Btn;
	Button open_Btn;

	int areaId_two = -1;
	int areaId_one = 0x30;
	int roomId = -1;
	int channelId = -1;
	HomePressBroadcastReceiver homePressBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_ac);
		mode_Btn = (Button) findViewById(R.id.mode_btn);
		wind_speed_Btn = (Button) findViewById(R.id.wind_speed_btn);
		sleep_Btn = (Button) findViewById(R.id.sleep_btn);
		t_add_Btn = (Button) findViewById(R.id.t_add_btn);
		t_sub_Btn = (Button) findViewById(R.id.t_sub_btn);
		time_Btn = (Button) findViewById(R.id.time_btn);
		hour_Btn = (Button) findViewById(R.id.hour_btn);
		minute_Btn = (Button) findViewById(R.id.minute_btn);
		clear_Btn = (Button) findViewById(R.id.clear_btn);
		wind_direction_Btn = (Button) findViewById(R.id.wind_direction_btn);
		close_Btn = (Button) findViewById(R.id.close_btn);
		light_Btn = (Button) findViewById(R.id.light_btn);
		open_Btn = (ToggleButton) findViewById(R.id.open_btn);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			roomId = bundle.getInt("roomId");
			areaId_two = bundle.getInt("areaId");
			if (areaId_two == 0xff) {
				areaId_one = 0x01;
			}
			channelId = bundle.getInt("channelId");
		}
		mode_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.mode.getVal());
			}
		});
		wind_speed_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId,
						AC_Cmd.wind_speed.getVal());
			}
		});
		sleep_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.sleep.getVal());
			}
		});
		t_add_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.t_add.getVal());
			}
		});
		t_sub_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.t_sub.getVal());
			}
		});
		time_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.time.getVal());
			}
		});

		light_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.light.getVal());
			}
		});

		hour_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.hour.getVal());
			}
		});

		minute_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.minute.getVal());
			}
		});

		clear_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId, AC_Cmd.clear.getVal());
			}
		});

		wind_direction_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId,
						AC_Cmd.wind_direction.getVal());
			}
		});

		close_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId,
						Light_Cmd.all_light_close.getVal());
			}
		});

		open_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(Air_ConditioningActivity.this, areaId_one,
						areaId_two, roomId, channelId,
						Light_Cmd.light_open.getVal());
			}
		});

		homePressBroadcastReceiver = new HomePressBroadcastReceiver();
		registerReceiver(homePressBroadcastReceiver, new IntentFilter(
				Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();

		unregisterReceiver(homePressBroadcastReceiver);
	}

}
