package com.mac.taiyitong.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.mac.taiyitong.entity.Device;
import com.mac.taiyitong.entity.Room;

public class SQLiteHelper extends SQLiteOpenHelper {
	SQLiteDatabase db;

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS room(id integer PRIMARY KEY autoincrement,name VARCHAR(32),roomnum integer,areaid integer)");
		db.execSQL("CREATE TABLE IF NOT EXISTS device(id integer PRIMARY KEY autoincrement,name VARCHAR(32),channelid integer,type integer,roomid integer)");
		db.execSQL("CREATE TABLE IF NOT EXISTS channel(id integer PRIMARY KEY)");
		for (int i = 1; i <= 256; i++) {
			db.execSQL("insert into channel(id) values(" + i + ")");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion != newVersion) {
			db.execSQL("drop table if exists room");
			db.execSQL("drop table if exists device");
			onCreate(db);
		}
	}

	public long addRoom(ContentValues contentValues) {
		db = getWritableDatabase();
		long result = -1;
		try {
			db.beginTransaction();
			// db.execSQL("insert into room(name,areaid) values('"
			// + room.getName() + "','" + room.getAreaid() + "')");
			result = db.insert("room", null, contentValues);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
		return result;
	}

	public boolean delRoom(int id) {
		boolean b = false;
		db = getWritableDatabase();
		try {
			db.beginTransaction();
			db.execSQL("delete from room where id=" + id);
			db.execSQL("delete from device where roomid=" + id);
			db.setTransactionSuccessful();
			b = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
		return b;
	}

	public List<Room> selectRoomByAreaID(int areaid) {
		db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from room where areaid=" + areaid, null);
		List<Room> list = new ArrayList<Room>();
		while (cursor.moveToNext()) {
			Room room = new Room();
			room.setId(cursor.getInt(0));
			room.setName(cursor.getString(1));
			room.setRoomnum(cursor.getInt(2));
			room.setAreaid(cursor.getInt(3));
			list.add(room);
		}
		cursor.close();
		db.close();
		return list;
	}

	public long addDevice(ContentValues contentValues) {
		db = getWritableDatabase();
		long result = -1;
		try {
			db.beginTransaction();
			// db.execSQL("insert into device(name,channelid,type,room) values('"
			// + device.getName() + "'," + device.getChannelid() + ","
			// + device.getType() + "," + "," + device.getRoomid() + ")");
			result = db.insert("device", null, contentValues);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
		return result;
	}

	public boolean delDevice(int id) {
		boolean b = false;
		db = getWritableDatabase();
		try {
			db.beginTransaction();
			db.execSQL("delete from device where id=" + id);
			db.setTransactionSuccessful();
			b = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
		return b;
	}

	public List<Device> selectDeviceByRoomID(int roomid) {
		db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from device where roomid="
				+ roomid, null);
		List<Device> list = new ArrayList<Device>();
		while (cursor.moveToNext()) {
			Device device = new Device();
			device.setId(cursor.getInt(0));
			device.setName(cursor.getString(1));
			device.setChannelid(cursor.getInt(2));
			device.setType(cursor.getInt(3));
			device.setRoomid(cursor.getInt(4));
			list.add(device);
		}
		cursor.close();
		db.close();
		return list;
	}

	public List<String> selectNotInChannelID(int id) {
		db = this.getReadableDatabase();
		Cursor cursor = db
				.rawQuery(
						"SELECT id FROM channel WHERE id NOT IN (SELECT channelid FROM device WHERE roomid="
								+ id + ") ", null);
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			list.add(cursor.getInt(0) + "");
		}
		cursor.close();
		db.close();
		return list;
	}

	public List<String> selectNotInRoomNum(int id) {
		db = this.getReadableDatabase();
		Cursor cursor = db
				.rawQuery(
						"SELECT id FROM channel WHERE id NOT IN (SELECT roomnum FROM room WHERE areaid="
								+ id + ") ", null);
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			list.add(cursor.getInt(0) + "");
		}
		cursor.close();
		db.close();
		return list;
	}
}
