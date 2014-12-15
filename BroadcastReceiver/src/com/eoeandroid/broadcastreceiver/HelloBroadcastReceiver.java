package com.eoeandroid.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HelloBroadcastReceiver extends BroadcastReceiver {

	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		showNotification(intent);
	}

	@SuppressWarnings("deprecation")
	private void showNotification(Intent intent) {
		// TODO Auto-generated method stub
		NotificationManager nfManager = 
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification nf = new Notification(
				R.drawable.ic_launcher, 
				intent.getExtras().getString("content"), 
				System.currentTimeMillis());
		PendingIntent pi = 
				PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
		nf.setLatestEventInfo(context, intent.getExtras().getString("content"), null, pi);
		nfManager.notify(R.layout.activity_main, nf);
	}

}