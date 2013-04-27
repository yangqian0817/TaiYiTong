package com.mac.taiyitong.cons;

public enum Curtain_Cmd {
	up((byte) 0x0030), pause((byte) 0x0031), down((byte) 0x0032), light(
			(byte) 0x0033), lock((byte) 0x0034);

	private byte val;

	public byte getVal() {
		return val;
	}

	private Curtain_Cmd(byte val) {
		this.val = val;
	}
}
