package com.mac.taiyitong.broadcas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mac.taiyitong.LockActivity;
import com.mac.taiyitong.util.WriteUtil;

public class HomePressBroadcastReceiver extends BroadcastReceiver {
	final String SYSTEM_DIALOG_REASON_KEY = "reason";
	final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
	final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
	final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
			if (reason != null) {
				if (WriteUtil.isCheck) {
					Intent it = new Intent();
					it.setClass(context, LockActivity.class);
					context.startActivity(it);
				}
			}
		}
	}
}
