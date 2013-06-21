package com.mac.taiyitong.broadcas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mac.taiyitong.R;
import com.mac.taiyitong.SettingActivity;

public class ChangePwdConfirmBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		SettingActivity settingActivity = (SettingActivity) context;
		String action = intent.getAction();
		if (action.equals("10_0")) {
			settingActivity.change_pwd_step++;
			settingActivity.showPwdDialog(11);
			Toast.makeText(context, R.string.enter_new_pwd_msg,
					Toast.LENGTH_SHORT).show();
		}
		// if (action.equals("10_1")) {
		// Toast.makeText(context, R.string.enter_new_pwd_msg,
		// Toast.LENGTH_SHORT).show();
		// }
		if (action.equals("11_0")) {
			settingActivity.change_pwd_step++;
			settingActivity.showPwdDialog(12);
			Toast.makeText(context, R.string.enter_new_pwd_again_msg,
					Toast.LENGTH_SHORT).show();
		}
		// if (action.equals("11_1")) {
		//
		// }
		if (action.equals("12_0")) {
			settingActivity.change_pwd_step = 0;
			Toast.makeText(context, R.string.change_pwd_ok_msg,
					Toast.LENGTH_SHORT).show();

		}
		// if (action.equals("12_1")) {
		//
		// }
	}

}
