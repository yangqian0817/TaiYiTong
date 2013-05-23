//package com.mac.taiyitong;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//import com.mac.taiyitong.cons.Curtain_Cmd;
//import com.mac.taiyitong.util.WriteUtil;
//
//public class CopyOfClothesHorseActivity1 extends Activity {
//
//	int areaId_two = -1;
//	int areaId_one = 0x30;
//	int roomId = -1;
//	int channelId = -1;
//	Button up_Btn;
//	Button down_Btn;
//	Button pause_Btn;
//	Button lock_Btn;
//	Button light_Btn;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.control_curtain);
//		up_Btn = (Button) findViewById(R.id.up_btn);
//		down_Btn = (Button) findViewById(R.id.down_btn);
//		pause_Btn = (Button) findViewById(R.id.pause_btn);
//		lock_Btn = (Button) findViewById(R.id.lock_btn);
//		light_Btn = (Button) findViewById(R.id.light_btn);
//
//		Bundle bundle = getIntent().getExtras();
//		if (bundle != null) {
//			roomId = bundle.getInt("roomId");
//			areaId_two = bundle.getInt("areaId");
//			if (areaId_two == 0xff) {
//				areaId_one = 0x01;
//			}
//			channelId = bundle.getInt("channelId");
//		}
//
//		up_Btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				WriteUtil.write(CopyOfClothesHorseActivity1.this, areaId_one, areaId_two,
//						roomId, channelId, Curtain_Cmd.up.getVal());
//			}
//		});
//
//		down_Btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				WriteUtil.write(CopyOfClothesHorseActivity1.this, areaId_one, areaId_two,
//						roomId, channelId, Curtain_Cmd.down.getVal());
//			}
//		});
//
//		pause_Btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				WriteUtil.write(CopyOfClothesHorseActivity1.this, areaId_one, areaId_two,
//						roomId, channelId, Curtain_Cmd.pause.getVal());
//			}
//		});
//
//		lock_Btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				WriteUtil.write(CopyOfClothesHorseActivity1.this, areaId_one, areaId_two,
//						roomId, channelId, Curtain_Cmd.lock.getVal());
//			}
//		});
//
//		light_Btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				WriteUtil.write(CopyOfClothesHorseActivity1.this, areaId_one, areaId_two,
//						roomId, channelId, Curtain_Cmd.light.getVal());
//			}
//		});
//
//	}
//
// }
