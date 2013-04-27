package com.mac.taiyitong.cons;

public enum Heating_Cmd {
	witch((byte) 0x0010), mode((byte) 0x0011);

	private byte val;

	public byte getVal() {
		return val;
	}

	private Heating_Cmd(byte val) {
		this.val = val;
	}
}
