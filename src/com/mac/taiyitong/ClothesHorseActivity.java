package com.mac.taiyitong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.mac.taiyitong.cons.ClothesHorse_Cmd;
import com.mac.taiyitong.util.WriteUtil;

public class ClothesHorseActivity extends Activity {

	int areaId_two = -1;
	int areaId_one = 0x00;
	int roomId = -1;
	int channelId = -1;
	Button pause_Btn;
	ToggleButton toggle_Btn;
	Button light_Btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_clothes_horse);
		pause_Btn = (Button) findViewById(R.id.pause_btn);
		toggle_Btn = (ToggleButton) findViewById(R.id.toggle_btn);
		light_Btn = (Button) findViewById(R.id.light_btn);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			roomId = bundle.getInt("roomId");
			areaId_two = bundle.getInt("areaId");
			if (areaId_two == 0xff) {
				areaId_one = 0x01;
			}
			channelId = bundle.getInt("channelId");
		}
		toggle_Btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					WriteUtil.write(ClothesHorseActivity.this, areaId_one,
							areaId_two, roomId, channelId,
							ClothesHorse_Cmd.open.getVal());
				} else {
					WriteUtil.write(ClothesHorseActivity.this, areaId_one,
							areaId_two, roomId, channelId,
							ClothesHorse_Cmd.close.getVal());
				}
			}
		});

		pause_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(ClothesHorseActivity.this, areaId_one,
						areaId_two, roomId, channelId,
						ClothesHorse_Cmd.pause.getVal());
			}
		});

		light_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(ClothesHorseActivity.this, areaId_one,
						areaId_two, roomId, channelId,
						ClothesHorse_Cmd.light.getVal());
			}
		});

	}

}
