package com.mac.taiyitong.cons;

public enum AC_Cmd {
	witch((byte) 0x0000), mode((byte) 0x0001), wind_speed((byte) 0x0002), sleep(
			(byte) 0x0003), t_add((byte) 0x0004), t_sub((byte) 0x0005), time(
			(byte) 0x0006), hour((byte) 0x0007), minute((byte) 0x0008), clear(
			(byte) 0x0009), wind_direction((byte) 0x000a), lock((byte) 0x000b), light(
			(byte) 0x000c), ac_22((byte) 0x000d), ac_27((byte) 0x000e);

	private byte val;

	public byte getVal() {
		return val;
	}

	private AC_Cmd(byte val) {
		this.val = val;
	}
}
