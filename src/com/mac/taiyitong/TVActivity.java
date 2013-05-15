package com.mac.taiyitong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.mac.taiyitong.cons.Light_Cmd;
import com.mac.taiyitong.cons.TV_Cmd;
import com.mac.taiyitong.util.WriteUtil;

public class TVActivity extends Activity {

	Button back_Btn;
	Button choose_Btn;
	Button slience_Btn;
	Button v_add_Btn;
	Button v_sub_Btn;
	Button submit_Btn;
	Button next_Btn;
	Button last_Btn;
	Button video_Btn;
	ToggleButton toggle_Btn;

	int areaId_two = -1;
	int areaId_one = 0x03;
	int roomId = -1;
	int channelId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_tv);
		back_Btn = (Button) findViewById(R.id.back_btn);
		choose_Btn = (Button) findViewById(R.id.choose_btn);
		slience_Btn = (Button) findViewById(R.id.slience_btn);
		v_add_Btn = (Button) findViewById(R.id.v_add_btn);
		v_sub_Btn = (Button) findViewById(R.id.v_sub_btn);
		submit_Btn = (Button) findViewById(R.id.submit_btn);
		next_Btn = (Button) findViewById(R.id.next_btn);
		last_Btn = (Button) findViewById(R.id.last_btn);
		video_Btn = (Button) findViewById(R.id.video_btn);
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
		back_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.back.getVal());
			}
		});
		choose_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.choose.getVal());
			}
		});
		slience_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.slience.getVal());
			}
		});
		v_add_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.v_add.getVal());
			}
		});
		v_sub_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.v_sub.getVal());
			}
		});
		submit_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.submit.getVal());
			}
		});
		next_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.previous.getVal());
			}
		});

		last_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.next.getVal());
			}
		});

		video_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
						roomId, channelId, TV_Cmd.video.getVal());
			}
		});

		toggle_Btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
							roomId, channelId, Light_Cmd.light_open.getVal());
				} else {
					WriteUtil.write(TVActivity.this, areaId_one, areaId_two,
							roomId, channelId, Light_Cmd.light_close.getVal());
				}
			}
		});
	}
}
