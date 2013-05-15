package com.mac.taiyitong.cons;

public enum Heating_Cmd {
	witch((byte) 0x0010), mode((byte) 0x0011), t_add((byte) 0x0012), t_sub(
			(byte) 0x0013);

	private byte val;

	public byte getVal() {
		return val;
	}

	private Heating_Cmd(byte val) {
		this.val = val;
	}
}
