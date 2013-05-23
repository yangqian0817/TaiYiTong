package com.mac.taiyitong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.mac.taiyitong.cons.Music_Cmd;
import com.mac.taiyitong.cons.TV_Cmd;
import com.mac.taiyitong.util.WriteUtil;

public class MusicActivity extends Activity {
	Button exit_Btn;
	Button menu_Btn;
	Button v_add_Btn;
	Button v_sub_Btn;
	Button submit_Btn;
	Button next_Btn;
	Button last_Btn;
	Button mode_Btn;
	ToggleButton toggle_Btn;
	int areaId_two = -1;
	int areaId_one = 0x30;
	int roomId = -1;
	int channelId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_music);

		exit_Btn = (Button) findViewById(R.id.exit_btn);
		menu_Btn = (Button) findViewById(R.id.menu_btn);
		v_add_Btn = (Button) findViewById(R.id.v_add_btn);
		v_sub_Btn = (Button) findViewById(R.id.v_sub_btn);
		submit_Btn = (Button) findViewById(R.id.submit_btn);
		next_Btn = (Button) findViewById(R.id.next_btn);
		last_Btn = (Button) findViewById(R.id.last_btn);
		mode_Btn = (Button) findViewById(R.id.mode_btn);
		toggle_Btn = (ToggleButton) findViewById(R.id.toggle_btn);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			roomId = bundle.getInt("roomId");
			areaId_two = bundle.getInt("areaId");
			if (areaId_two == 0xff) {
				areaId_one = 0x01;
			}
			channelId = bundle.getInt("channelId");
		}
		exit_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.exit.getVal());
			}
		});
		menu_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.menu.getVal());
			}
		});
		v_add_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.v_add.getVal());
			}
		});
		v_sub_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.v_sub.getVal());
			}
		});
		submit_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.submit.getVal());
			}
		});
		next_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.forward.getVal());
			}
		});

		last_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.rewind.getVal());
			}
		});

		mode_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.mode.getVal());
			}
		});

		toggle_Btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				// if (isChecked) {
				// WriteUtil.write(MusicActivity.this,areaId_one, areaId_two,
				// roomId, channelId,
				// Light_Cmd.light_open.getVal());
				// } else {
				// WriteUtil.write(MusicActivity.this,areaId_one, areaId_two,
				// roomId, channelId,
				// Light_Cmd.light_close.getVal());
				// }
				WriteUtil.write(MusicActivity.this, areaId_one, areaId_two,
						roomId, channelId, Music_Cmd.witch.getVal());
			}
		});
	}

}
