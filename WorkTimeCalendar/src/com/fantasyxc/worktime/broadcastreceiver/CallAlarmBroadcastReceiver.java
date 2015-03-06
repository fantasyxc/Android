package com.fantasyxc.worktime.broadcastreceiver;

import com.fantasyxc.worktime.activity.AlarmAlertActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CallAlarmBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("CallAlarmBroadcastReceiver", "onReceive");
		
		// get intent message
		String strClockTitle = intent.getStringExtra("strClockTitle");
		int iClockGroup = intent.getIntExtra("iClockGroup", 0);
		String strRemark = intent.getStringExtra("strRemark");
		Log.d("CallAlarmBroadcastReceiver", "Received: clocktitle=" + strClockTitle + ", group=" + iClockGroup + ", strRemark=" + strRemark);
		
		// start new intent
		Intent it = new Intent(context, AlarmAlertActivity.class);
		it.putExtra("strClockTitle", strClockTitle);
		it.putExtra("iClockGroup", iClockGroup);
		it.putExtra("strRemark", strRemark);
		
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
	}
	
	
}
