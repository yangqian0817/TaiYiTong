package com.mac.taiyitong.cons;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket {
	private static Socket socket;
	public static String address;
	public static int port;

	public MySocket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Socket getInstance() throws UnknownHostException, IOException {
		if (socket == null) {
			socket = new Socket(address, port);
		}
		return socket;
	}
}
