package com.mac.taiyitong.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mac.taiyitong.R;
import com.mac.taiyitong.entity.Scene_Device;

public class SceneDeviceListAdapter extends BaseAdapter {

	private Context context;
	private List<Scene_Device> list;

	public SceneDeviceListAdapter(Context context, List<Scene_Device> list) {
		super();
		this.context = context;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater localinflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = localinflater.inflate(
					R.layout.scene_device_list_item, null);
		}
		TextView device_name_tv = (TextView) convertView
				.findViewById(R.id.device_name_tv);
		ToggleButton toggleButton = (ToggleButton) convertView
				.findViewById(R.id.toggle_btn);
		Scene_Device device = list.get(position);

		device_name_tv.setText(device.getDevice().getName());
		if (device.getState() == 1) {
			toggleButton.setChecked(true);
		}
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

}
