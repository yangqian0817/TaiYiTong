package com.mac.taiyitong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

import com.mac.taiyitong.cons.Light_Cmd;
import com.mac.taiyitong.util.ByteStrParser;
import com.mac.taiyitong.util.WriteUtil;

public class LightActivity extends Activity {
	int type = -1;
	int areaId_two = -1;
	int areaId_one = 0x00;
	int roomId = -1;
	int channelId = -1;
	ToggleButton light_switch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_light);
		SeekBar light_SeekBar = (SeekBar) findViewById(R.id.light_seekBar);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			type = bundle.getInt("type");
			roomId = bundle.getInt("roomId");
			areaId_two = bundle.getInt("areaId");
			if (areaId_two == 0xff) {
				areaId_one = 0x01;
			}
			channelId = bundle.getInt("channelId");
			if (type != 1)
				light_SeekBar.setVisibility(View.INVISIBLE);
		}
		light_switch = (ToggleButton) findViewById(R.id.light_switch);
		light_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					WriteUtil.write(LightActivity.this, areaId_one, areaId_two,
							roomId, channelId, Light_Cmd.light_open.getVal());
				} else {
					WriteUtil.write(LightActivity.this, areaId_one, areaId_two,
							roomId, channelId, Light_Cmd.light_close.getVal());
				}
			}
		});

		light_SeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int sb_Val = seekBar.getProgress() + 63;
				String h_sb_Val_Str = Integer.toHexString(sb_Val);
				byte[] cmd = ByteStrParser.hexStringToBytes(h_sb_Val_Str);
				WriteUtil.write(LightActivity.this, areaId_one, areaId_two,
						roomId, channelId, cmd[0]);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}
		});

	}
}
