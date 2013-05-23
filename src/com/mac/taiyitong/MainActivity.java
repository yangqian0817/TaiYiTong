package com.mac.taiyitong;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mac.taiyitong.adapter.MainDeviceListAdapter;
import com.mac.taiyitong.adapter.RoomListAdapter;
import com.mac.taiyitong.cons.Light_Cmd;
import com.mac.taiyitong.entity.Device;
import com.mac.taiyitong.entity.Room;
import com.mac.taiyitong.entity.Scene;
import com.mac.taiyitong.entity.Scene_Device;
import com.mac.taiyitong.util.SQLiteHelper;
import com.mac.taiyitong.util.WriteUtil;

public class MainActivity extends ActivityGroup {

	ImageView setting_Iv;
	Button choose_area_Btn;
	ToggleButton toggle_Btn;
	Button off_Btn;
	String[] area = new String[256];
	public int areaId_one = 0x00;
	public int areaId = -1;
	public int roomId = -1;
	public int deviceId = -1;
	public int channelId = -1;
	ListView room_list;
	ListView device_list;
	List<Room> room_data;
	List<Device> device_data;
	RoomListAdapter room_list_adapter;
	MainDeviceListAdapter device_list_adapter;
	SQLiteHelper sqLiteHelper;
	private LinearLayout container = null;
	HorizontalScrollView mode_hScrollView;
	LinearLayout mode_hLinearLayout;
	Thread socket_Thread;
	SharedPreferences preferences;
	String ip = "";
	int port = 0;
	public static Socket socket;
	Handler handler;
	List<String> sceneStrList;
	List<Scene> sceneList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setting_Iv = (ImageView) findViewById(R.id.setting_iv);
		choose_area_Btn = (Button) findViewById(R.id.choose_area_btn);
		toggle_Btn = (ToggleButton) findViewById(R.id.toggle_btn);
		off_Btn = (Button) findViewById(R.id.off_btn);

		room_list = (ListView) findViewById(R.id.room_list);
		device_list = (ListView) findViewById(R.id.device_list);
		container = (LinearLayout) findViewById(R.id.containerBody);
		mode_hLinearLayout = (LinearLayout) findViewById(R.id.mode_hLinearLayout);
		mode_hScrollView = (HorizontalScrollView) findViewById(R.id.mode_hScrollView);
		// mode_hScrollView.add
		sqLiteHelper = new SQLiteHelper(this, "tyt.db", null, 1);
		preferences = getSharedPreferences("ip_port", MODE_PRIVATE);
		ip = preferences.getString("ip", null);
		port = preferences.getInt("port", 0);
		sceneStrList = new ArrayList<String>();
		sceneList = new ArrayList<Scene>();
		room_data = new ArrayList<Room>();
		device_data = new ArrayList<Device>();
		getAreaData();
		initMode();

		room_list_adapter = new RoomListAdapter(MainActivity.this, room_data, 0);
		device_list_adapter = new MainDeviceListAdapter(MainActivity.this,
				device_data);
		room_list.setAdapter(room_list_adapter);
		device_list.setAdapter(device_list_adapter);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 0) {
					Toast.makeText(MainActivity.this, R.string.ip_port_error,
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 1) {
					Toast.makeText(MainActivity.this, R.string.io_exception,
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 2) {
					Toast.makeText(MainActivity.this, R.string.connection_ok,
							Toast.LENGTH_SHORT).show();
					WriteUtil.showPwdDialog(MainActivity.this);
				}
			}
		};
		connection();
		choose_area_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this)
						.setSingleChoiceItems(area, areaId,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
										if ((which + 1) == roomId) {
											return;
										}
										container.removeAllViews();
										areaId = which + 1;
										room_data.clear();
										roomId = -1;
										deviceId = -1;
										channelId = -1;
										device_data.clear();
										if (areaId == 0xff) {
											areaId_one = 0x01;
										}

										room_data.addAll(sqLiteHelper
												.selectRoomByAreaID(areaId));
										sceneList.clear();
										sceneList.addAll(sqLiteHelper
												.selectSceneByAreaID(areaId));
										initMode();
										room_list_adapter
												.notifyDataSetChanged();
										room_list_adapter
												.notifyDataSetInvalidated();
										room_list_adapter.setSelectItem(roomId);
										device_list_adapter
												.notifyDataSetChanged();
										device_list_adapter
												.setSelectItem(deviceId);
										device_list_adapter
												.notifyDataSetInvalidated();
										toggle_Btn.setVisibility(View.VISIBLE);
										off_Btn.setVisibility(View.VISIBLE);
									}
								})
						.setNegativeButton(R.string.setting_cancel, null)
						.show();
			}
		});

		room_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == roomId) {
					return;
				}
				container.removeAllViews();
				room_list_adapter.setSelectItem(arg2);
				room_list_adapter.notifyDataSetInvalidated();
				roomId = arg2;
				deviceId = -1;
				channelId = -1;
				device_data.clear();
				device_data.addAll(sqLiteHelper.selectDeviceByRoomID(room_data
						.get(arg2).getId()));
				room_list_adapter.notifyDataSetChanged();
			}
		});

		device_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				device_list_adapter.setSelectItem(arg2);
				device_list_adapter.notifyDataSetInvalidated();
				deviceId = arg2;
				int device_type = device_data.get(deviceId).getType();
				channelId = device_data.get(deviceId).getChannelid();
				container.removeAllViews();
				Intent intent = new Intent();
				intent.putExtra("areaId", areaId);
				intent.putExtra("roomId", room_data.get(roomId).getId());
				intent.putExtra("channelId", channelId);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				switch (device_type) {
				case 1:
					intent.putExtra("type", 1);
					intent.setClass(MainActivity.this, LightActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"adjust_list", intent).getDecorView());
					break;
				case 2:
					intent.putExtra("type", 2);
					intent.setClass(MainActivity.this, LightActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"no_adjust_list", intent).getDecorView());
					break;
				case 3:
					intent.putExtra("type", 3);
					intent.setClass(MainActivity.this, LightActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"general_device", intent).getDecorView());
					break;
				case 4:
					intent.putExtra("type", 4);
					intent.setClass(MainActivity.this, CurtainActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"curtain", intent).getDecorView());
					break;
				case 5:
					intent.putExtra("type", 5);
					intent.setClass(MainActivity.this,
							Air_ConditioningActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"ac", intent).getDecorView());
					break;
				case 6:
					intent.putExtra("type", 6);
					intent.setClass(MainActivity.this, HeatingActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"heating", intent).getDecorView());
					break;
				case 7:
					intent.putExtra("type", 7);
					intent.setClass(MainActivity.this, MusicActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"music", intent).getDecorView());
					break;
				case 8:
					intent.putExtra("type", 8);
					intent.setClass(MainActivity.this, TVActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"tv", intent).getDecorView());
					break;
				case 9:
					intent.putExtra("type", 9);
					intent.setClass(MainActivity.this,
							ClothesHorseActivity.class);
					container.addView(getLocalActivityManager().startActivity(
							"tv", intent).getDecorView());
					break;
				default:
					break;
				}
			}
		});

		setting_Iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});

		off_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WriteUtil.write(MainActivity.this, areaId_one, areaId, roomId,
						channelId, Light_Cmd.all_close.getVal());
			}
		});
		toggle_Btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					WriteUtil.write(MainActivity.this, areaId_one, areaId,
							roomId, channelId,
							Light_Cmd.all_light_open.getVal());
				} else {
					WriteUtil.write(MainActivity.this, areaId_one, areaId,
							roomId, channelId,
							Light_Cmd.all_light_close.getVal());
				}
			}
		});
	}

	public void getAreaData() {
		for (int i = 0; i <= 255; i++) {
			area[i] = getResources().getString(R.string.area) + i;
		}
	}

	public void initMode() {
		mode_hLinearLayout.removeAllViews();
		sceneStrList.clear();
		for (int i = 0; i < sceneList.size(); i++) {
			Scene scene = sceneList.get(i);
			sceneStrList.add(scene.getName());
		}

		for (int i = 0; i < sceneStrList.size(); i++) {
			final TextView textView = new TextView(MainActivity.this);
			final int idx = i;
			textView.setText(sceneStrList.get(i));
			textView.setWidth(100);
			textView.setGravity(Gravity.CENTER);
			mode_hLinearLayout.addView(textView);
			textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					setModeColor(idx);
					exe_Mode(sceneList.get(idx).getId());
				}
			});
		}
	}

	private void exe_Mode(int sceneId) {
		if (WriteUtil.outputStream == null) {
			Toast.makeText(MainActivity.this, R.string.connect_msg,
					Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<Scene_Device> devices = new ArrayList<Scene_Device>();
				devices.clear();
				for (int i = 0; i < room_data.size(); i++) {
					devices.addAll(sqLiteHelper
							.selectScene_DeviceByRoomID(room_data.get(i)
									.getId()));
					for (int j = 0; j < devices.size(); j++) {
						if (devices.get(i).getState() == 1) {
							WriteUtil.write(MainActivity.this, areaId_one,
									areaId, roomId, channelId,
									Light_Cmd.light_open.getVal());
						} else {
							WriteUtil.write(MainActivity.this, areaId_one,
									areaId, roomId, channelId,
									Light_Cmd.light_close.getVal());
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public void setModeColor(int position) {
		int mode_size = mode_hLinearLayout.getChildCount();
		for (int i = 0; i < mode_size; i++) {
			TextView textView = (TextView) mode_hLinearLayout.getChildAt(i);
			if (position == i) {
				textView.setBackgroundResource(R.drawable.top3);
			} else {
				textView.setBackgroundColor(android.R.color.transparent);
			}
		}
	}

	public void connection() {
		if (ip == null || port == 0) {
			Toast.makeText(MainActivity.this, R.string.setting_ip_port_msg,
					Toast.LENGTH_SHORT).show();
			return;
		}
		socket_Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					socket = new Socket(ip, port);
					WriteUtil.outputStream = socket.getOutputStream();
					WriteUtil.inputStream = socket.getInputStream();
					handler.sendEmptyMessage(2);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(1);
				}
			}
		});
		socket_Thread.start();
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage(R.string.exit_msg);
		builder.setTitle(R.string.dialog_msg);
		builder.setPositiveButton(R.string.submit,
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});
		builder.setNegativeButton(R.string.setting_cancel,
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if (socket != null)
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		super.finish();
	}
}
