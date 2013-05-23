package com.mac.taiyitong.adapter;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mac.taiyitong.R;
import com.mac.taiyitong.SceneSettingActivity;
import com.mac.taiyitong.entity.Scene_Device;
import com.mac.taiyitong.util.SQLiteHelper;

public class SceneDeviceListAdapter extends BaseAdapter {

	private Context context;
	private List<Scene_Device> list;
	private SQLiteHelper sqLiteHelper;
	private boolean b = false;

	public SceneDeviceListAdapter(Context context, List<Scene_Device> list) {
		super();
		this.context = context;
		this.list = list;
		sqLiteHelper = new SQLiteHelper(context, "tyt.db", null, 1);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater localinflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = localinflater.inflate(
					R.layout.scene_device_list_item, null);

			holder = new ViewHolder();
			holder.device_name_tv = (TextView) convertView
					.findViewById(R.id.device_name_tv);
			holder.witch_btn = (Button) convertView
					.findViewById(R.id.witch_btn);
			convertView.setTag(holder);
			// TextView device_name_tv = (TextView) convertView
			// .findViewById(R.id.device_name_tv);
			// ToggleButton toggleButton = (ToggleButton) convertView
			// .findViewById(R.id.toggle_btn);

		} else {
			holder = (ViewHolder) convertView.getTag();
			resetViewHolder(holder);
		}
		final Scene_Device scene_Device = list.get(position);
		holder.device_name_tv.setText(scene_Device.getDevice().getName());
		if (scene_Device.getState() == 1) {
			holder.witch_btn.setText(R.string.on);
		}

		holder.witch_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (scene_Device.getState() == 1) {
					sqLiteHelper.delScene_Device(scene_Device.getId());
					Scene_Device sd = scene_Device;
					sd.setId(0);
					sd.setState(0);
					sd.setSceneid(0);
					list.set(position, sd);
					((Button) v).setText(R.string.off);
				} else {
					ContentValues contentValues = new ContentValues();

					contentValues.put("deviceid", list.get(position)
							.getDevice().getId());
					contentValues.put("state", 1);
					contentValues.put(
							"sceneid",
							((SceneSettingActivity) context).scene_data.get(
									((SceneSettingActivity) context).sceneId)
									.getId());
					int add_scene_device_id = (int) sqLiteHelper
							.addScene_Device(contentValues);
					scene_Device.setId(add_scene_device_id);
					scene_Device.setState(1);
					scene_Device
							.setSceneid(((SceneSettingActivity) context).scene_data
									.get(((SceneSettingActivity) context).sceneId)
									.getId());
					list.set(position, scene_Device);
					((Button) v).setText(R.string.on);
				}
			}
		});
		if (position == selectItem) {
			convertView.setBackgroundResource(R.drawable.list_check_bg);
		} else {
			convertView.setBackgroundColor(Color.TRANSPARENT);
		}
		return convertView;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	private int selectItem = -1;

	class ViewHolder {
		TextView device_name_tv;
		Button witch_btn;

	}

	void resetViewHolder(ViewHolder viewHolder) {
		viewHolder.device_name_tv.setText(null);
		viewHolder.witch_btn.setText(R.string.off);
	}
}
