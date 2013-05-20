package com.mac.taiyitong.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.content.Context;
import android.widget.Toast;

public class WriteUtil {
	public static byte[] tyt_b = new byte[] { 0x54, 0x59, 0x54 };
	public static OutputStream outputStream = null;
	static ByteBuffer buffer = ByteBuffer.allocate(8);

	public static void write(Context context, int areaId_one, int areaId_two,
			int roomId, int channelId, byte cmd) {
		if (outputStream == null) {
			Toast.makeText(context, "请先连接设置IP和端口，再进行操作", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		buffer.clear();
		buffer.put(tyt_b);
		byte areaId_one_b = (byte) areaId_one;
		byte areaId_two_b = (byte) areaId_two;
		byte roomId_b = (byte) roomId;
		byte channelId_b = (byte) channelId;

		// byte areaId_one_b = Byte.parseByte(Integer.toHexString(areaId_one -
		// 1));
		// byte areaId_two_b = Byte.parseByte(Integer.toHexString(areaId_two -
		// 1));
		// byte roomId_b = Byte.parseByte(Integer.toHexString(roomId - 1));
		// byte channelId_b = Byte.parseByte(Integer.toHexString(channelId -
		// 1));

		buffer.put(areaId_one_b);
		buffer.put(areaId_two_b);
		buffer.put(roomId_b);
		buffer.put(channelId_b);
		buffer.put(cmd);
		try {
			outputStream.write(buffer.array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
}
