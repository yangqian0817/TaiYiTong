package com.mac.taiyitong;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
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
		password_Et = (EditText) findViewById(R.id.password_et);
		unlock_Btn = (Button) findViewById(R.id.unlock_btn);
		broadcastReceiver = new PwdConfirmBroadcastReceiver(this);
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
					Toast.makeText(LockActivity.this, "√‹¬Î¥ÌŒÛ",
							Toast.LENGTH_SHORT).show();
					TipHelper.Vibrate(LockActivity.this, 1000);
				} else {
					if (!WriteUtil.checkPassword(LockActivity.this,
							confirm_pwd.getBytes(), 0)) {
						TipHelper.Vibrate(LockActivity.this, 1000);
					} else {
						finish();
					}
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

}
