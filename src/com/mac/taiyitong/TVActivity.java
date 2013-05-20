package com.mac.taiyitong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
	Button number_Btn;

	int areaId_two = -1;
	int areaId_one = 0x03;
	int roomId = -1;
	int channelId = -1;
	Dialog dialog;

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
		number_Btn = (Button) findViewById(R.id.number_btn);
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
		number_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new AlertDialog.Builder(TVActivity.this).create();
				dialog.show();
				// dialog.getWindow().setContentView(
				// R.layout.control_tv_choose_dialog);
				LayoutInflater factory = LayoutInflater.from(TVActivity.this);
				View view = factory.inflate(R.layout.control_tv_choose_dialog,
						null);
				Button btn_0 = (Button) view.findViewById(R.id.button0);
				Button btn_1 = (Button) view.findViewById(R.id.button1);
				Button btn_2 = (Button) view.findViewById(R.id.button2);
				Button btn_3 = (Button) view.findViewById(R.id.button3);
				Button btn_4 = (Button) view.findViewById(R.id.button4);
				Button btn_5 = (Button) view.findViewById(R.id.button5);
				Button btn_6 = (Button) view.findViewById(R.id.button6);
				Button btn_7 = (Button) view.findViewById(R.id.button7);
				Button btn_8 = (Button) view.findViewById(R.id.button8);
				Button btn_9 = (Button) view.findViewById(R.id.button9);
				Button btn_ok = (Button) view.findViewById(R.id.submit_btn);
				btn_0.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_0.getVal());
					}
				});
				btn_1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_1.getVal());
					}
				});
				btn_2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_2.getVal());
					}
				});
				btn_3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_3.getVal());
					}
				});
				btn_4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_4.getVal());
					}
				});
				btn_5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_5.getVal());
					}
				});
				btn_6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_6.getVal());
					}
				});
				btn_7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_7.getVal());
					}
				});
				btn_8.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_8.getVal());
					}
				});
				btn_9.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						WriteUtil.write(TVActivity.this, areaId_one,
								areaId_two, roomId, channelId,
								TV_Cmd.v_9.getVal());
					}
				});
				btn_ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				dialog.getWindow().setContentView(view);
				WindowManager.LayoutParams layoutParams = dialog.getWindow()
						.getAttributes();

				layoutParams.width = 460; // ÐÞ¸Ä´°Ìå¿í¸ß

				layoutParams.height = 350;
				dialog.getWindow().setAttributes(layoutParams);

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
