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
import com.mac.taiyitong.entity.Scene;
import com.mac.taiyitong.entity.Scene_Device;

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
		db.execSQL("CREATE TABLE IF NOT EXISTS scene(id integer PRIMARY KEY autoincrement,name VARCHAR(32),areaid integer)");
		// db.execSQL("CREATE TABLE IF NOT EXISTS scene_room(id integer PRIMARY KEY autoincrement,roomid integer,sceneid integer)");
		db.execSQL("CREATE TABLE IF NOT EXISTS scene_device(id integer PRIMARY KEY autoincrement,deviceid integer,state integer,sceneid integer)");
		for (int i = 0; i <= 255; i++) {
			db.execSQL("insert into channel(id) values(" + i + ")");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion != newVersion) {
			db.execSQL("drop table if exists room");
			db.execSQL("drop table if exists device");
			db.execSQL("drop table if exists channel");
			db.execSQL("drop table if exists scene_device");
			db.execSQL("drop table if exists scene");
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

	public long addScene(ContentValues contentValues) {
		db = getWritableDatabase();
		long result = -1;
		try {
			db.beginTransaction();
			result = db.insert("scene", null, contentValues);
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

	public boolean delScene(int id) {
		boolean b = false;
		db = getWritableDatabase();
		try {
			db.beginTransaction();
			db.execSQL("delete from scene_device where sceneid=" + id);
			db.execSQL("delete from scene where id=" + id);
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

	public boolean modifyScene(ContentValues contentValues) {
		db = getWritableDatabase();
		boolean b = false;
		try {
			db.beginTransaction();
			db.update("scene", contentValues, "id=?",
					new String[] { contentValues.getAsInteger("id") + "" });
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

	public boolean modifyRoom(ContentValues contentValues) {
		db = getWritableDatabase();
		boolean b = false;
		try {
			db.beginTransaction();
			db.update("room", contentValues, "id=?",
					new String[] { contentValues.getAsInteger("id") + "" });
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

	public List<Scene> selectSceneByAreaID(int id) {
		db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM scene WHERE areaid=" + id,
				null);
		List<Scene> list = new ArrayList<Scene>();
		while (cursor.moveToNext()) {
			Scene scene = new Scene();
			scene.setId(cursor.getInt(0));
			scene.setName(cursor.getString(1));
			scene.setAreaid(cursor.getInt(2));
			list.add(scene);
		}
		cursor.close();
		db.close();
		return list;
	}

	public long addScene_Device(ContentValues contentValues) {
		db = getWritableDatabase();
		long result = -1;
		try {
			db.beginTransaction();
			result = db.insert("scene_device", null, contentValues);
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

	public boolean delScene_Device(int id) {
		boolean b = false;
		db = getWritableDatabase();
		try {
			db.beginTransaction();
			db.execSQL("delete from scene_device where id=" + id);
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

	public List<Scene_Device> selectScene_DeviceByRoomID(int roomid) {
		db = this.getReadableDatabase();
		Cursor cursor = db
				.rawQuery(
						"select d.id,d.name,d.channelid,d.type,d.roomid,s.id,s.state,s.sceneid from device d left outer join scene_device s on d.id=s.deviceid where roomid="
								+ roomid, null);
		List<Scene_Device> list = new ArrayList<Scene_Device>();
		while (cursor.moveToNext()) {
			Scene_Device scene_Device = new Scene_Device();
			Device device = new Device();
			device.setId(cursor.getInt(0));
			device.setName(cursor.getString(1));
			device.setChannelid(cursor.getInt(2));
			device.setType(cursor.getInt(3));
			device.setRoomid(cursor.getInt(4));
			scene_Device.setId(cursor.getInt(5));
			scene_Device.setState(cursor.getInt(6));
			scene_Device.setSceneid(cursor.getInt(7));
			scene_Device.setDevice(device);
			list.add(scene_Device);
		}
		cursor.close();
		db.close();
		return list;
	}
}
