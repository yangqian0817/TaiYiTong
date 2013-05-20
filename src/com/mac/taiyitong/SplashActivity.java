package com.mac.taiyitong;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.Toast;

import com.mac.taiyitong.util.ConnectionDetector;

public class SplashActivity extends Activity {
	protected int _splashTime = 3000;
	private Thread splashTread;
	private SharedPreferences sharedPreferences;
	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		handler = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 0) {
					Toast.makeText(SplashActivity.this, "Çë¿ªÆôÍøÂç",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		sharedPreferences = SplashActivity.this
				.getSharedPreferences("Login", 0);
		splashTread = new Thread() {
			@Override
			public void run() {
				synchronized (this) {

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (new ConnectionDetector(SplashActivity.this)
							.isConnectingToInternet()) {
						finish();
						Intent intent = new Intent();
						intent.setClass(SplashActivity.this, MainActivity.class);
						startActivity(intent);
					} else {
						handler.sendEmptyMessage(0);
						Intent intent = new Intent(Settings.ACTION_SETTINGS);
						startActivity(intent);
					}
				}

			}
		};
		splashTread.start();
	}

}
