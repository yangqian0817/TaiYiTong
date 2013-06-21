package com.mac.taiyitong;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mac.taiyitong.broadcas.PwdConfirmBroadcastReceiver;
import com.mac.taiyitong.util.TipHelper;
import com.mac.taiyitong.util.WriteUtil;

public class LockActivity extends Activity {
	EditText password_Et;
	Button unlock_Btn;
	PwdConfirmBroadcastReceiver broadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock);
		Log.i("提示", "lock_onCreate");
		password_Et = (EditText) findViewById(R.id.password_et);
		unlock_Btn = (Button) findViewById(R.id.unlock_btn);
		broadcastReceiver = new PwdConfirmBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("0");
		filter.addAction("1");
		registerReceiver(broadcastReceiver, filter);
		unlock_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String confirm_pwd = password_Et.getText().toString().trim();
				if (confirm_pwd.getBytes().length != 6) {
					Toast.makeText(LockActivity.this, "密码错误",
							Toast.LENGTH_SHORT).show();
					TipHelper.Vibrate(LockActivity.this, 1000);
				} else {
					byte[] pwd_b = new byte[confirm_pwd.length()];
					for (int i = 0; i < confirm_pwd.length(); i++) {
						String ss = confirm_pwd.substring(i, i + 1);
						pwd_b[i] = Byte.parseByte(ss);
					}
					WriteUtil.checkPassword(LockActivity.this, pwd_b, 2);
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		Log.i("提示", "lock_finish");
		unregisterReceiver(broadcastReceiver);
		WriteUtil.isHome = false;
	}
}
