package com.mac.taiyitong.cons;

public enum TV_Cmd {
	witch((byte) 0x0040), video((byte) 0x0041), previous((byte) 0x0042), next(
			(byte) 0x0043), quite((byte) 0x0044), back((byte) 0x0045), v_add(
			(byte) 0x0046), v_sub((byte) 0x0047), choose((byte) 0x0048), submit(
			(byte) 0x0049), v_1((byte) 0x004a), v_2((byte) 0x004b), v_3(
			(byte) 0x004c), v_4((byte) 0x004d), v_5((byte) 0x004e), v_6(
			(byte) 0x004f), v_7((byte) 0x0050), v_8((byte) 0x0051), v_9(
			(byte) 0x0052), v_0((byte) 0x0053);

	private byte val;

	public byte getVal() {
		return val;
	}

	private TV_Cmd(byte val) {
		this.val = val;
	}
}
