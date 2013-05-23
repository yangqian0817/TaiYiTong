package com.mac.taiyitong.broadcas;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PwdConfirmBroadcastReceiver extends BroadcastReceiver {

	Activity activity;

	public PwdConfirmBroadcastReceiver(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action.equals("0")) {
			activity.finish();
		} else if (action.equals("1")) {
			Toast.makeText(context, "ÃÜÂë²»ÕýÈ·", Toast.LENGTH_LONG).show();
		}
	}

}
