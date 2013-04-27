package com.mac.taiyitong.entity;

public class Room {
	public int id;
	public String name;
	public int roomnum;
	public int areaid;

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getRoomnum() {
		return roomnum;
	}

	public void setRoomnum(int roomnum) {
		this.roomnum = roomnum;
	}

	public Room(int id, String name, int roomnum, int areaid) {
		super();
		this.id = id;
		this.name = name;
		this.roomnum = roomnum;
		this.areaid = areaid;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
