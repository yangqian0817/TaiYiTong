package com.mac.taiyitong.broadcas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mac.taiyitong.LockActivity;

public class PwdConfirmBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action.equals("0")) {
			((LockActivity) context).finish();
		} else if (action.equals("1")) {
			Toast.makeText(context, "ÃÜÂë²»ÕýÈ·", Toast.LENGTH_LONG).show();
		}
	}

}
