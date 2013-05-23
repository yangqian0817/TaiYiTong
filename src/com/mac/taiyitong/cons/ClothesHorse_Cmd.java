package com.mac.taiyitong.cons;

public enum ClothesHorse_Cmd {
	open((byte) 0x2d), close((byte) 0x3c), light((byte) 0x0079), pause(
			(byte) 0x0078), up((byte) 0x0030), down((byte) 0x0032), lock(
			(byte) 0x0034);

	private byte val;

	public byte getVal() {
		return val;
	}

	private ClothesHorse_Cmd(byte val) {
		this.val = val;
	}
}
