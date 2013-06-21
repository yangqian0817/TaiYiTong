package com.mac.taiyitong.cons;

public enum Music_Cmd {
	witch((byte) 0x0020), mode((byte) 0x0021), exit((byte) 0x0022), menu(
			(byte) 0x0023), submit((byte) 0x0024), pause((byte) 0x0025), v_add(
			(byte) 0x0026), v_sub((byte) 0x0027), forward((byte) 0x0028), rewind(
			(byte) 0x0029), stop((byte) 0x0025);

	private byte val;

	public byte getVal() {
		return val;
	}

	private Music_Cmd(byte val) {
		this.val = val;
	}
}
