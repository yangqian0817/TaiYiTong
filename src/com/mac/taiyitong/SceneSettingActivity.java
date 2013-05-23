package com.mac.taiyitong;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mac.taiyitong.adapter.ArrayAdapter;
import com.mac.taiyitong.adapter.RoomListAdapter;
import com.mac.taiyitong.adapter.SceneDeviceListAdapter;
import com.mac.taiyitong.adapter.SceneListAdapter;
import com.mac.taiyitong.entity.Room;
import com.mac.taiyitong.entity.Scene;
import com.mac.taiyitong.entity.Scene_Device;
import com.mac.taiyitong.util.SQLiteHelper;

public class SceneSettingActivity extends Activity {
	ImageView scene_add_Iv;
	Button scene_delete_Btn;
	Button scene_modify_Btn;
	Button back_Btn;

	SQLiteHelper sqLiteHelper;
	public int roomId = -1;
	public int areaId = -1;
	public int sceneId = -1;
	ListView setting_scene_list;
	ListView setting_area_list;
	ListView setting_room_list;
	ListView setting_scene_device_list;

	List<Room> room_data;
	public List<Scene_Device> scene_device_data;
	public List<Scene> scene_data;

	List<String> area_List;

	SceneListAdapter setting_scene_list_adapter;
	ArrayAdapter setting_area_list_adapter;
	RoomListAdapter setting_room_list_adapter;
	SceneDeviceListAdapter setting_scene_device_list_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_scene);
		scene_add_Iv = (ImageView) findViewById(R.id.scene_add);
		scene_delete_Btn = (Button) findViewById(R.id.scene_delete_btn);
		scene_modify_Btn = (Button) findViewById(R.id.modify_scene_btn);
		back_Btn = (Button) findViewById(R.id.back_btn);

		setting_scene_list = (ListView) findViewById(R.id.setting_scene_list);
		setting_area_list = (ListView) findViewById(R.id.setting_area_list);
		setting_room_list = (ListView) findViewById(R.id.setting_room_list);
		setting_scene_device_list = (ListView) findViewById(R.id.setting_scene_device_list);
		sqLiteHelper = new SQLiteHelper(this, "tyt.db", null, 1);

		getAreaData();

		setting_area_list_adapter = new ArrayAdapter(this, area_List);
		setting_area_list.setAdapter(setting_area_list_adapter);

		setting_scene_list_adapter = new SceneListAdapter(this, scene_data);
		setting_scene_list.setAdapter(setting_scene_list_adapter);

		setting_room_list_adapter = new RoomListAdapter(this, room_data, 1);
		setting_room_list.setAdapter(setting_room_list_adapter);

		setting_scene_device_list_adapter = new SceneDeviceListAdapter(this,
				scene_device_data);
		setting_scene_device_list.setAdapter(setting_scene_device_list_adapter);

		setting_scene_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (sceneId == arg2) {
					return;
				}
				sceneId = arg2;
				setting_scene_list_adapter.setSelectItem(arg2);
				setting_scene_list_adapter.notifyDataSetInvalidated();
				room_data.clear();
				room_data.addAll(sqLiteHelper.selectRoomByAreaID(areaId));
				setting_room_list_adapter.notifyDataSetChanged();
				setting_room_list_adapter.notifyDataSetInvalidated();
				setting_room_list_adapter.setSelectItem(roomId);

			}
		});

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
				scene_data.clear();
				scene_device_data.clear();
				roomId = -1;
				sceneId = -1;

				scene_data.addAll(sqLiteHelper.selectSceneByAreaID(areaId));

				setting_scene_list_adapter.notifyDataSetChanged();
				setting_scene_list_adapter.setSelectItem(sceneId);
				setting_scene_list_adapter.notifyDataSetInvalidated();

				setting_room_list_adapter.notifyDataSetChanged();
				setting_room_list_adapter.setSelectItem(roomId);
				setting_room_list_adapter.notifyDataSetInvalidated();

				setting_scene_device_list_adapter.notifyDataSetChanged();

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
				scene_device_data.clear();
				scene_device_data.addAll(sqLiteHelper
						.selectScene_DeviceByRoomID(room_data.get(arg2).getId()));

				setting_scene_device_list_adapter.notifyDataSetChanged();
			}
		});
		scene_modify_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sceneId == -1) {
					Toast.makeText(SceneSettingActivity.this, "请选中要修改的场景",
							Toast.LENGTH_LONG).show();
					return;
				}
				final Scene scene = scene_data.get(sceneId);

				final EditText scene_Name = new EditText(
						SceneSettingActivity.this);
				scene_Name.setText(scene.getName());
				new AlertDialog.Builder(SceneSettingActivity.this)
						.setTitle("修改场景")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(scene_Name)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (scene_Name.getText().toString()
												.trim() == null
												|| "".equals(scene_Name
														.getText().toString()
														.trim())) {
											Toast.makeText(
													SceneSettingActivity.this,
													"场景名称不能为空",
													Toast.LENGTH_LONG).show();
											return;
										}
										ContentValues contentValues = new ContentValues();
										contentValues.put("id", scene.getId());
										contentValues.put("name", scene_Name
												.getText().toString().trim());
										contentValues.put("areaid",
												scene.getAreaid());
										if (sqLiteHelper
												.modifyScene(contentValues)) {
											scene.setName(scene_Name.getText()
													.toString().trim());
											scene_data.set(sceneId, scene);

											setting_scene_list_adapter
													.notifyDataSetChanged();
											Toast.makeText(
													SceneSettingActivity.this,
													"修改成功", Toast.LENGTH_LONG)
													.show();

										} else {
											Toast.makeText(
													SceneSettingActivity.this,
													"修改失败", Toast.LENGTH_LONG)
													.show();
										}

									}
								}).setNegativeButton("取消", null).show();

			}
		});

		scene_add_Iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (areaId == -1) {
					Toast.makeText(SceneSettingActivity.this, "请选中要添加场景的区域",
							Toast.LENGTH_LONG).show();
					return;
				}
				final EditText scene_Name = new EditText(
						SceneSettingActivity.this);
				new AlertDialog.Builder(SceneSettingActivity.this)
						.setTitle("添加场景")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(scene_Name)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (scene_Name.getText().toString()
												.trim() == null
												|| "".equals(scene_Name
														.getText().toString()
														.trim())) {
											Toast.makeText(
													SceneSettingActivity.this,
													"场景名称不能为空",
													Toast.LENGTH_LONG).show();
											return;
										}
										ContentValues contentValues = new ContentValues();
										contentValues.put("name", scene_Name
												.getText().toString().trim());
										contentValues.put("areaid", areaId);
										Scene scene = new Scene();
										int add_sceneid = (int) sqLiteHelper
												.addScene(contentValues);
										if (add_sceneid != -1) {
											scene.setName(scene_Name.getText()
													.toString().trim());
											scene.setAreaid(areaId);
											scene.setId(add_sceneid);
											scene_data.add(scene);

											setting_scene_list_adapter
													.notifyDataSetChanged();
											Toast.makeText(
													SceneSettingActivity.this,
													"添加成功", Toast.LENGTH_LONG)
													.show();
										} else {
											Toast.makeText(
													SceneSettingActivity.this,
													"添加失败", Toast.LENGTH_LONG)
													.show();
										}

									}
								}).setNegativeButton("取消", null).show();

			}
		});

		scene_delete_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sceneId == -1) {
					Toast.makeText(SceneSettingActivity.this, "请选中要删除的项目",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (sqLiteHelper.delScene(scene_data.get(sceneId).getId())) {
					scene_data.remove(sceneId);
					setting_scene_list_adapter.notifyDataSetChanged();
					Toast.makeText(SceneSettingActivity.this, "删除成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(SceneSettingActivity.this, "删除失败",
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
		scene_data = new ArrayList<Scene>();
		room_data = new ArrayList<Room>();
		scene_device_data = new ArrayList<Scene_Device>();
		area_List = new ArrayList<String>();

		for (int i = 0; i <= 255; i++) {
			area_List.add("区域" + i);
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}
