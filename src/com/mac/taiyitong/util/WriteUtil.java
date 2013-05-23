package com.mac.taiyitong.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import com.mac.taiyitong.R;

public class WriteUtil {
	public static byte[] tyt_b = new byte[] { 0x54, 0x59, 0x54 };
	public static OutputStream outputStream = null;
	public static InputStream inputStream = null;
	static ByteBuffer buffer = ByteBuffer.allocate(8);
	public static boolean isCheck = false;

	public static void write(Context context, int areaId_one, int areaId_two,
			int roomId, int channelId, byte cmd) {
		if (!isCheck) {
			showPwdDialog(context);
		}
		if (outputStream == null) {
			Toast.makeText(context, R.string.setting_ip_port_msg,
					Toast.LENGTH_SHORT).show();
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

	public static void showPwdDialog(final Context context) {
		Toast.makeText(context, R.string.verifying, Toast.LENGTH_LONG).show();
		final EditText password_Et = new EditText(context);
		new AlertDialog.Builder(context)
				.setTitle(R.string.password_verifying)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(password_Et)
				.setPositiveButton(R.string.submit,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								String c_pwd = password_Et.getText().toString()
										.trim();
								if (c_pwd == null || "".equals(c_pwd)
										|| c_pwd.getBytes().length != 8) {
									Toast.makeText(context,
											R.string.password_error,
											Toast.LENGTH_LONG).show();
									showPwdDialog(context);
									return;
								}
								checkPassword(context, c_pwd.getBytes());
							}
						}).setNegativeButton(R.string.setting_cancel, null)
				.show();
	}

	public static void checkPassword(final Context context,
			final byte[] password) {
		boolean b = false;
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 0) {
					Toast.makeText(context, R.string.verifying_success,
							Toast.LENGTH_SHORT).show();
					isCheck = true;
					context.sendBroadcast(new Intent("0"));
				} else if (msg.what == 1) {
					Toast.makeText(context, R.string.verifying_fail,
							Toast.LENGTH_SHORT).show();
					context.sendBroadcast(new Intent("1"));
					showPwdDialog(context);
				} else if (msg.what == 2) {
					Toast.makeText(context, R.string.io_exception,
							Toast.LENGTH_SHORT).show();
					context.sendBroadcast(new Intent("1"));
					showPwdDialog(context);
				}
			}
		};

		if (inputStream == null) {
			Toast.makeText(context, R.string.setting_ip_port_msg,
					Toast.LENGTH_SHORT).show();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					byte[] s_password = new byte[10];
					s_password[0] = 0x1a;
					s_password[9] = 0x1a;
					System.arraycopy(password, 0, s_password, 0,
							password.length);
					outputStream.write(s_password);
					byte[] r_b = new byte[1];
					inputStream.read(r_b);
					if (r_b[0] == 0x00) {
						handler.sendEmptyMessage(0);
						isCheck = true;
					} else if (r_b[0] == 0x01) {
						handler.sendEmptyMessage(1);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					handler.sendEmptyMessage(2);
					// android.os.Process.killProcess(android.os.Process.myPid());
				}

			}
		}).start();
	}
}
