package com.mac.taiyitong;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class HeatingActivity extends Activity {
	int areaId_two = -1;
	int areaId_one = 0x30;
	int roomId = -1;
	int channelId = -1;
	Button add_Btn;
	Button sub_Btn;
	ToggleButton heating_witch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_heating);
		add_Btn = (Button) findViewById(R.id.add_btn);
		sub_Btn = (Button) findViewById(R.id.sub_btn);
		heating_witch = (ToggleButton) findViewById(R.id.heating_witch);

		heating_witch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					// WriteUtil.write(areaId_one, areaId_two, roomId,
					// channelId,
					// Light_Cmd.light_open);
				} else {
					// WriteUtil.write(areaId_one, areaId_two, roomId,
					// channelId,
					// Light_Cmd.light_close);
				}
			}
		});
	}
}
