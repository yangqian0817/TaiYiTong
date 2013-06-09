package com.mac.taiyitong.cons;

public enum State_Cmd {
	state_open((byte) 0xd2), state_close((byte) 0xe1), state_request(
			(byte) 0xf0);
	private byte val;

	private State_Cmd(byte val) {
		this.val = val;
	}

	public byte getVal() {
		return val;
	}
}
