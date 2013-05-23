package com.mac.taiyitong.cons;

public enum Curtain_Cmd {
	open((byte) 0x2d), close((byte) 0x3c), pause((byte) 0x78);

	private byte val;

	public byte getVal() {
		return val;
	}

	private Curtain_Cmd(byte val) {
		this.val = val;
	}
}
