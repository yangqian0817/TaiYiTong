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
import com.mac.taiyitong.entity.Room;

public class RoomNameListAdapter extends BaseAdapter {

	private Context context;
	private List<Room> list;
	int type;

	public RoomNameListAdapter(Context context, List<Room> list, int type) {
		super();
		this.context = context;
		this.list = list;
		this.type = type;
	}

	public RoomNameListAdapter() {
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
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater localinflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
			convertView = localinflater.inflate(R.layout.simple_listview_item,
					null);
			// TextView textView = (TextView) convertView
			// .findViewById(R.id.textView1);
			// textView.setText(list.get(position).getName());

			holder.room_name_tv = (TextView) convertView
					.findViewById(R.id.textView1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			resetViewHolder(holder);
		}

		if (position == selectItem) {
			if (type == 1) {
				convertView.setBackgroundColor(R.color.setting_choose_bg);
			} else if (type == 0) {
				convertView.setBackgroundResource(R.drawable.list_check_bg);
			}
		} else {
			convertView.setBackgroundColor(Color.TRANSPARENT);
		}
		Room room = list.get(position);
		holder.room_name_tv.setText(room.getName());
		return convertView;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	private int selectItem = -1;

	class ViewHolder {
		TextView room_name_tv;
	}

	void resetViewHolder(ViewHolder viewHolder) {
		viewHolder.room_name_tv.setText(null);
	}
}
