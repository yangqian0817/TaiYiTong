package com.mac.taiyitong;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.mac.taiyitong.broadcas.HomePressBroadcastReceiver;
import com.mac.taiyitong.cons.Curtain_Cmd;
import com.mac.taiyitong.util.WriteUtil;

public class CurtainActivity extends Activity {
	int type = -1;
	int areaId_two = -1;
	int areaId_one = 0x00;
	int roomId = -1;
	int channelId = -1;
	ToggleButton switch_Btn;
	Button pause_Btn;
	HomePressBroadcastReceiver homePressBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_curtain);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			type = bundle.getInt("type");
			roomId = bundle.getInt("roomId");
			areaId_two = bundle.getInt("areaId");
			if (areaId_two == 0xff) {
				areaId_one = 0x01;
			}
			channelId = bundle.getInt("channelId");
		}
		switch_Btn = (ToggleButton) findViewById(R.id.light_switch);
		pause_Btn = (Button) findViewById(R.id.pause_btn);
		switch_Btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					WriteUtil.write(CurtainActivity.this, areaId_one,
							areaId_two, roomId, channelId,
							Curtain_Cmd.close.getVal());
				} else {
					WriteUtil.write(CurtainActivity.this, areaId_one,
							areaId_two, roomId, channelId,
							Curtain_Cmd.open.getVal());
				}
			}
		});
		pause_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(CurtainActivity.this, areaId_one, areaId_two,
						roomId, channelId, Curtain_Cmd.pause.getVal());
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
