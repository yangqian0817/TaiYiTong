package com.mac.taiyitong.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.mac.taiyitong.R;

public class WriteUtil {
	public static byte[] tyt_b = new byte[] { 0x54, 0x59, 0x54 };
	public static Socket socket = null;
	public static boolean isCheck = false;
	public static boolean b = false;
	public static boolean isConnecting = false;
	public static boolean isChecking = false;
	public static String ip;
	public static int port;

	public static void write(Context context, int areaId_one, int areaId_two,
			int roomId, int channelId, byte cmd) {
		if (isConnecting) {
			Toast.makeText(context, R.string.connecting, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (socket == null) {
			Toast.makeText(context, R.string.setting_ip_port_msg,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (isChecking) {
			Toast.makeText(context, R.string.verifying, Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (!isCheck) {
			showPwdDialog(context, 0);
		}

		ByteBuffer buffer = ByteBuffer.allocate(8);
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
			Log.i("提示" + buffer.position(), new String(buffer.array()));
			socket.getOutputStream().write(buffer.array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// android.os.Process.killProcess(android.os.Process.myPid());
			connection(context);
		}
	}

	public static boolean showPwdDialog(final Context context, final int type) {
		b = false;
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
										|| c_pwd.getBytes().length != 6) {
									Toast.makeText(context,
											R.string.password_error,
											Toast.LENGTH_LONG).show();
									return;
								}
								byte[] pwd_b = new byte[c_pwd.length()];
								for (int i = 0; i < c_pwd.length(); i++) {
									String ss = c_pwd.substring(i, i + 1);
									pwd_b[i] = Byte.parseByte(ss);
								}
								b = checkPassword(context, pwd_b, type);
								isChecking = true;
							}
						}).setNegativeButton(R.string.setting_cancel, null)
				.show();
		return b;
	}

	public static boolean checkPassword(final Context context,
			final byte[] password, final int type) {
		b = false;
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 0) {
					Toast.makeText(context, R.string.verifying_success,
							Toast.LENGTH_SHORT).show();
					isCheck = true;
					b = true;
					isChecking = false;
					context.sendBroadcast(new Intent("0"));
				} else if (msg.what == 1) {
					Toast.makeText(context, R.string.verifying_fail,
							Toast.LENGTH_SHORT).show();
					context.sendBroadcast(new Intent("1"));
					showPwdDialog(context, type);
				} else if (msg.what == 2) {
					Toast.makeText(context, R.string.io_exception,
							Toast.LENGTH_SHORT).show();
					context.sendBroadcast(new Intent("2"));
					connection(context);
				}
			}
		};

		if (socket == null) {
			Toast.makeText(context, R.string.setting_ip_port_msg,
					Toast.LENGTH_SHORT).show();
		} else {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {

						byte[] s_password = new byte[8];
						s_password[0] = 0x2a;
						s_password[7] = 0x2a;
						if (type == 1) {
							s_password[7] = 0x23;
						}
						System.arraycopy(password, 0, s_password, 1,
								password.length);
						socket.getOutputStream().write(s_password);
						byte[] r_b = new byte[1];
						socket.getInputStream().read(r_b);
						Log.i("提示", new String(r_b));
						if (r_b[0] == 0x00) {
							handler.sendEmptyMessage(1);
						} else if (r_b[0] == 0x01) {
							b = true;
							handler.sendEmptyMessage(0);
						} else {
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
		return b;
	}

	public static void connection(final Context context) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 3) {
					Toast.makeText(context, R.string.ip_port_error,
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 4) {
					Toast.makeText(context, R.string.io_exception,
							Toast.LENGTH_SHORT).show();
					connection(context);
				} else if (msg.what == 2) {
					Toast.makeText(context, R.string.setting_ip_port_msg,
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 0) {
					Toast.makeText(context, R.string.connection_ok,
							Toast.LENGTH_SHORT).show();
					WriteUtil.isConnecting = false;
					WriteUtil.showPwdDialog(context, 0);
				} else if (msg.what == 1) {
					Toast.makeText(context, R.string.connecting,
							Toast.LENGTH_SHORT).show();
				}

			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					if (WriteUtil.isConnecting) {
						handler.sendEmptyMessage(1);
						return;
					}
					WriteUtil.isConnecting = true;
					if (ip == null || port == 0) {
						handler.sendEmptyMessage(2);
						return;
					}

					handler.sendEmptyMessage(1);
					WriteUtil.socket = new Socket(ip, port);

					handler.sendEmptyMessage(0);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(3);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(4);
				}

			}
		}).start();
	}
}
