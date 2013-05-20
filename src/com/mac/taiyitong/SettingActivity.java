package com.mac.taiyitong;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mac.taiyitong.adapter.ArrayAdapter;
import com.mac.taiyitong.adapter.DeviceListAdapter;
import com.mac.taiyitong.adapter.RoomListAdapter;
import com.mac.taiyitong.entity.Device;
import com.mac.taiyitong.entity.Room;
import com.mac.taiyitong.util.SQLiteHelper;
import com.mac.taiyitong.util.WriteUtil;

public class SettingActivity extends Activity {
	ImageView room_add_Iv;
	ImageView device_add_Iv;
	Button room_delete_Btn;
	Button back_Btn;
	Button device_delete_Btn;
	SQLiteHelper sqLiteHelper;
	public int roomId = -1;
	public int areaId = -1;
	public int deviceId = -1;
	ListView setting_romantic_list;
	ListView setting_area_list;
	ListView setting_room_list;
	ListView setting_device_list;
	List<String> area_data;
	List<Room> room_data;
	List<Device> device_data;
	Spinner device_type_sp;
	Spinner channelid_sp;
	Spinner room_num_sp;
	List<String> channelid_List;
	List<String> room_num_List;

	android.widget.ArrayAdapter<String> setting_romantic_list_adapter;
	ArrayAdapter setting_area_list_adapter;
	RoomListAdapter setting_room_list_adapter;
	DeviceListAdapter setting_device_list_adapter;
	String[] setting_data = new String[] { "房间/设备设置", "远程控制设置", "场景设置" };
	String[] device_type = new String[] { "可调光灯", "不可调光灯", "通用电器", "窗帘类", "空调",
			"地采暖", "背景音乐", "电视机", "晾衣架" };
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Handler handler;
	String n_ip = null;
	int n_port = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		room_add_Iv = (ImageView) findViewById(R.id.room_add);
		device_add_Iv = (ImageView) findViewById(R.id.device_add);

		room_delete_Btn = (Button) findViewById(R.id.room_delete_btn);
		back_Btn = (Button) findViewById(R.id.back_btn);
		device_delete_Btn = (Button) findViewById(R.id.device_delete_btn);

		setting_romantic_list = (ListView) findViewById(R.id.setting_romantic_list);
		setting_area_list = (ListView) findViewById(R.id.setting_area_list);
		setting_room_list = (ListView) findViewById(R.id.setting_room_list);
		setting_device_list = (ListView) findViewById(R.id.setting_device_list);
		sharedPreferences = getSharedPreferences("ip_port", MODE_PRIVATE);
		editor = sharedPreferences.edit();
		sqLiteHelper = new SQLiteHelper(this, "tyt.db", null, 1);

		getAreaData();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 0) {
					Toast.makeText(SettingActivity.this, "IP地址和端口错误",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 1) {
					Toast.makeText(SettingActivity.this, "网络异常",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 2) {
					Toast.makeText(SettingActivity.this, "连接成功",
							Toast.LENGTH_SHORT).show();
				}
			}
		};

		setting_romantic_list_adapter = new android.widget.ArrayAdapter<String>(
				this, R.layout.simple_listview_item, setting_data);
		setting_romantic_list.setAdapter(setting_romantic_list_adapter);

		setting_romantic_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 1) {
					final Dialog dialog = new Dialog(SettingActivity.this,
							R.style.myDialogTheme);
					dialog.setContentView(R.layout.setting_ip_dialog);
					dialog.show();
					final EditText ip_Et = (EditText) dialog
							.findViewById(R.id.ip_et);
					final EditText port_Et = (EditText) dialog
							.findViewById(R.id.port_et);
					String ip_Str = sharedPreferences.getString("ip", null);
					int port = sharedPreferences.getInt("port", 0);
					if (ip_Str != null && port != 0) {
						ip_Et.setText(ip_Str);
						port_Et.setText(port + "");
					}
					Button connect_Btn = (Button) dialog
							.findViewById(R.id.connect_btn);
					Button reset_Btn = (Button) dialog
							.findViewById(R.id.reset_btn);

					connect_Btn
							.setOnClickListener(new android.view.View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									String n_ip_Str = ip_Et.getText()
											.toString().trim();
									String n_port_Str = port_Et.getText()
											.toString().trim();
									if (n_ip_Str == null || "".equals(n_ip_Str)) {
										Toast.makeText(SettingActivity.this,
												"请输入IP地址", Toast.LENGTH_LONG)
												.show();
										return;
									}
									if (n_port_Str == null
											|| "".equals(n_port_Str)) {
										Toast.makeText(SettingActivity.this,
												"请输入端口号", Toast.LENGTH_LONG)
												.show();
										return;
									}
									editor.clear();
									n_ip = n_ip_Str;
									editor.putString("ip", n_ip_Str);
									n_port = Integer.parseInt(n_port_Str);
									editor.putInt("port", n_port);
									editor.commit();
									dialog.dismiss();
									connection(n_ip, n_port);
								}
							});
					reset_Btn
							.setOnClickListener(new android.view.View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
				} else if (arg2 == 2) {
					Intent intent = new Intent();
					intent.setClass(SettingActivity.this,
							SceneSettingActivity.class);
					startActivity(intent);
				}
			}
		});

		setting_area_list_adapter = new ArrayAdapter(this, area_data);
		setting_area_list.setAdapter(setting_area_list_adapter);

		setting_room_list_adapter = new RoomListAdapter(this, room_data, 1);
		setting_room_list.setAdapter(setting_room_list_adapter);

		setting_device_list_adapter = new DeviceListAdapter(this, device_data);
		setting_device_list.setAdapter(setting_device_list_adapter);
		setting_area_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (areaId == arg2 + 1) {
					return;
				}
				setting_area_list_adapter.setSelectItem(arg2);
				setting_area_list_adapter.notifyDataSetInvalidated();
				areaId = arg2 + 1;
				room_data.clear();
				roomId = -1;
				deviceId = -1;
				device_data.clear();
				room_data.addAll(sqLiteHelper.selectRoomByAreaID(areaId));

				setting_room_list_adapter.notifyDataSetChanged();
				setting_room_list_adapter.notifyDataSetInvalidated();
				setting_room_list_adapter.setSelectItem(roomId);
				setting_device_list_adapter.notifyDataSetChanged();
				setting_device_list_adapter.setSelectItem(deviceId);
				setting_device_list_adapter.notifyDataSetInvalidated();
			}
		});

		setting_room_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (roomId == arg2) {
					return;
				}
				setting_room_list_adapter.setSelectItem(arg2);
				setting_room_list_adapter.notifyDataSetInvalidated();
				roomId = arg2;
				deviceId = -1;
				device_data.clear();
				device_data.addAll(sqLiteHelper.selectDeviceByRoomID(room_data
						.get(arg2).getId()));
				setting_device_list_adapter.notifyDataSetChanged();
				setting_room_list_adapter.notifyDataSetInvalidated();
				setting_device_list_adapter.notifyDataSetChanged();
				setting_device_list_adapter.notifyDataSetInvalidated();
			}
		});

		setting_device_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				deviceId = arg2;
				setting_device_list_adapter.setSelectItem(arg2);
				setting_device_list_adapter.notifyDataSetInvalidated();
			}
		});

		room_add_Iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (areaId == -1) {
					Toast.makeText(SettingActivity.this, "请选中要添加房间的区域",
							Toast.LENGTH_LONG).show();
					return;
				}
				final Dialog dialog = new Dialog(SettingActivity.this,
						R.style.myDialogTheme);
				dialog.setContentView(R.layout.add_room_dialog);
				dialog.show();
				final EditText room_name_Et = (EditText) dialog
						.findViewById(R.id.room_name_et);
				room_num_sp = (Spinner) dialog.findViewById(R.id.room_num_sp);

				room_num_List.clear();
				room_num_List.addAll(sqLiteHelper.selectNotInRoomNum(areaId));
				if (room_num_List.size() == 0) {
					Toast.makeText(SettingActivity.this, "最多添加256个房间",
							Toast.LENGTH_LONG).show();
					return;
				}
				android.widget.ArrayAdapter<String> room_num_adapter = new android.widget.ArrayAdapter<String>(
						SettingActivity.this, R.layout.simple_spinner_item,
						room_num_List);
				room_num_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_item);
				room_num_sp.setAdapter(room_num_adapter);

				Button add_Btn = (Button) dialog.findViewById(R.id.add_btn);
				Button cancel_Btn = (Button) dialog
						.findViewById(R.id.cancel_btn);

				cancel_Btn
						.setOnClickListener(new android.view.View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				add_Btn.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String roomName = room_name_Et.getText().toString()
								.trim();
						if (roomName == null || "".equals(roomName)) {
							Toast.makeText(SettingActivity.this, "名称不能为空",
									Toast.LENGTH_LONG).show();
							return;
						}

						int room_num = Integer.parseInt(room_num_List
								.get(room_num_sp.getSelectedItemPosition()));
						Room room = new Room();
						room.setAreaid(areaId);
						room.setName(roomName);
						room.setRoomnum(room_num);
						ContentValues contentValues = new ContentValues();
						contentValues.put("areaid", areaId);
						contentValues.put("name", roomName);
						contentValues.put("roomnum", room_num);
						int result = (int) sqLiteHelper.addRoom(contentValues);
						if (result != -1) {
							Toast.makeText(SettingActivity.this, "添加成功",
									Toast.LENGTH_LONG).show();
							room.setId(result);
							room_data.add(room);
							setting_room_list_adapter.notifyDataSetChanged();
						} else {
							Toast.makeText(SettingActivity.this, "添加失败",
									Toast.LENGTH_LONG).show();
						}
						dialog.dismiss();
					}
				});

			}
		});

		device_add_Iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (roomId == -1) {
					Toast.makeText(SettingActivity.this, "请选中添加设备的房间",
							Toast.LENGTH_LONG).show();
					return;
				}
				final Dialog dialog = new Dialog(SettingActivity.this,
						R.style.myDialogTheme);
				dialog.setContentView(R.layout.add_device_dialog);
				dialog.show();
				final EditText device_name_Et = (EditText) dialog
						.findViewById(R.id.device_name_et);
				device_type_sp = (Spinner) dialog
						.findViewById(R.id.device_type_sp);
				channelid_sp = (Spinner) dialog.findViewById(R.id.channel_sp);

				android.widget.ArrayAdapter<String> device_adapter = new android.widget.ArrayAdapter<String>(
						SettingActivity.this, R.layout.simple_spinner_item,
						device_type);
				device_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_item);
				device_type_sp.setAdapter(device_adapter);

				channelid_List.clear();
				channelid_List.addAll(sqLiteHelper
						.selectNotInChannelID(room_data.get(roomId).getId()));
				if (channelid_List.size() == 0) {
					Toast.makeText(SettingActivity.this, "最多添加256个设备",
							Toast.LENGTH_LONG).show();
					return;
				}
				android.widget.ArrayAdapter<String> channel_adapter = new android.widget.ArrayAdapter<String>(
						SettingActivity.this, R.layout.simple_spinner_item,
						channelid_List);
				channel_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_item);
				channelid_sp.setAdapter(channel_adapter);

				Button add_Btn = (Button) dialog.findViewById(R.id.add_btn);
				Button cancel_Btn = (Button) dialog
						.findViewById(R.id.cancel_btn);

				cancel_Btn
						.setOnClickListener(new android.view.View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				add_Btn.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String deviceName = device_name_Et.getText().toString()
								.trim();
						if (deviceName == null || "".equals(deviceName)) {
							Toast.makeText(SettingActivity.this, "名称不能为空",
									Toast.LENGTH_LONG).show();
							return;
						}

						Device device = new Device();
						int channelid = Integer.parseInt(channelid_List
								.get(channelid_sp.getSelectedItemPosition()));
						device.setName(deviceName);
						device.setChannelid(channelid);
						device.setRoomid(room_data.get(roomId).getId());
						device.setType(device_type_sp.getSelectedItemPosition() + 1);

						ContentValues contentValues = new ContentValues();
						contentValues.put("roomid", room_data.get(roomId)
								.getId());
						contentValues.put("name", deviceName);
						contentValues.put("channelid", channelid);
						contentValues.put("type",
								device_type_sp.getSelectedItemPosition() + 1);

						int result = (int) sqLiteHelper
								.addDevice(contentValues);
						if (result != -1) {
							Toast.makeText(SettingActivity.this, "添加成功",
									Toast.LENGTH_LONG).show();
							device.setId(result);
							device_data.add(device);
							setting_device_list_adapter.notifyDataSetChanged();
						} else {
							Toast.makeText(SettingActivity.this, "添加失败",
									Toast.LENGTH_LONG).show();
						}
						dialog.dismiss();
					}
				});

			}
		});

		room_delete_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (roomId == -1) {
					Toast.makeText(SettingActivity.this, "请选中要删除的项目",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (sqLiteHelper.delRoom(room_data.get(roomId).getId())) {
					room_data.remove(roomId);
					setting_room_list_adapter.notifyDataSetChanged();
					device_data.clear();
					setting_device_list_adapter.notifyDataSetChanged();
					Toast.makeText(SettingActivity.this, "删除成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(SettingActivity.this, "删除失败",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		device_delete_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (deviceId == -1) {
					Toast.makeText(SettingActivity.this, "请选中要删除的项目",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (sqLiteHelper.delDevice(device_data.get(deviceId).getId())) {
					device_data.remove(deviceId);
					setting_device_list_adapter.notifyDataSetChanged();
					Toast.makeText(SettingActivity.this, "删除成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(SettingActivity.this, "删除失败",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		back_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void getAreaData() {
		area_data = new ArrayList<String>();
		room_data = new ArrayList<Room>();
		device_data = new ArrayList<Device>();
		channelid_List = new ArrayList<String>();
		room_num_List = new ArrayList<String>();
		for (int i = 0; i <= 255; i++) {
			area_data.add("区域" + i);
		}
	}

	public void connection(final String ip, final int port) {
		if (MainActivity.socket != null) {
			if (MainActivity.socket.isConnected()) {
				try {
					MainActivity.socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					MainActivity.socket = new Socket(ip, port);
					WriteUtil.outputStream = MainActivity.socket
							.getOutputStream();
					handler.sendEmptyMessage(2);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(1);
				}
			}
		}).start();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}
