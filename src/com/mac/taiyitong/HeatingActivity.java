package com.mac.taiyitong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.mac.taiyitong.cons.Heating_Cmd;
import com.mac.taiyitong.util.WriteUtil;

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
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			roomId = bundle.getInt("roomId");
			areaId_two = bundle.getInt("areaId");
			if (areaId_two == 0xff) {
				areaId_one = 0x01;
			}
			channelId = bundle.getInt("channelId");
		}
		heating_witch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				WriteUtil.write(HeatingActivity.this, areaId_one, areaId_two,
						roomId, channelId, Heating_Cmd.witch.getVal());
			}
		});

		add_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(HeatingActivity.this, areaId_one, areaId_two,
						roomId, channelId, Heating_Cmd.t_add.getVal());
			}
		});

		sub_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(HeatingActivity.this, areaId_one, areaId_two,
						roomId, channelId, Heating_Cmd.t_sub.getVal());
			}
		});
	}
}
