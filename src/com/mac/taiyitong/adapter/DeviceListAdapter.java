package com.mac.taiyitong.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mac.taiyitong.R;
import com.mac.taiyitong.entity.Device;

public class DeviceListAdapter extends BaseAdapter {

	private Context context;
	private List<Device> list;

	public DeviceListAdapter(Context context, List<Device> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public DeviceListAdapter() {
		super();
		// TODO Auto-generated constructor stub
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
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater localinflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = localinflater
					.inflate(R.layout.device_list_item, null);

			holder = new ViewHolder();

			holder.channelid_tv = (TextView) convertView
					.findViewById(R.id.channelid_tv);
			holder.device_name_tv = (TextView) convertView
					.findViewById(R.id.device_name_tv);
			holder.device_type_tv = (TextView) convertView
					.findViewById(R.id.device_type_tv);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
			resetViewHolder(holder);
		}

		Device device = list.get(position);
		holder.channelid_tv.setText(device.getChannelid() + "");
		holder.device_name_tv.setText(device.getName());
		switch (device.getType()) {
		case 1:
			holder.device_type_tv.setText("�ɵ����");
			break;
		case 2:
			holder.device_type_tv.setText("���ɵ����");
			break;

		case 3:
			holder.device_type_tv.setText("ͨ�õ���");
			break;

		case 4:
			holder.device_type_tv.setText("������");
			break;

		case 5:
			holder.device_type_tv.setText("�յ�");
			break;

		case 6:
			holder.device_type_tv.setText("�ز�ů");
			break;
		case 7:
			holder.device_type_tv.setText("��������");
			break;
		case 8:
			holder.device_type_tv.setText("���ӻ�");
			break;
		case 9:
			holder.device_type_tv.setText("���¼�");
			break;
		default:
			break;
		}
		if (position == selectItem) {
			convertView.setBackgroundColor(R.color.setting_choose_bg);
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
		TextView channelid_tv;
		TextView device_name_tv;
		TextView device_type_tv;
	}

	void resetViewHolder(ViewHolder viewHolder) {
		viewHolder.channelid_tv.setText(null);
		viewHolder.device_name_tv.setText(null);
		viewHolder.device_type_tv.setText(null);
	}
}
