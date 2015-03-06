package com.fantasyxc.worktime.activity;

import java.io.IOException;

import com.fantasyxc.worktime.activity.adapter.MyAlarmClockAdapter;
import com.fantasyxc.worktimecalendar.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;

public class AlarmAlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.alarmalert_activity);
		Log.d("AlarmAlertActivity", "onCreate");

		// get delivered message
		Intent intent = getIntent();
		String strClockTitle = intent.getStringExtra("strClockTitle");
		String strMessage = intent.getStringExtra("strRemark");
		String strGroup = getGroupString(intent.getIntExtra("iClockGroup", 0));

		// music
		final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound_alarmclock);
		mp.seekTo(0);
		mp.start();

		// alert
		new AlertDialog.Builder(AlarmAlertActivity.this)
				.setTitle(strClockTitle + strGroup)
				.setMessage(strMessage)
				.setPositiveButton("关闭闹钟",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								mp.stop();

								AlarmAlertActivity.this.finish();

								// start AlarmClockActivity
								Intent intentAlarmClock = new Intent(
										AlarmAlertActivity.this,
										AlarmClockActivity.class);
								intentAlarmClock.putExtra("refresh", true);
								startActivity(intentAlarmClock);
								overridePendingTransition(0, 0);
							}
						}).setCancelable(true).show();
	}

	/*
	 * transform group into string
	 */
	private String getGroupString(int group) {
		String strGroupMessage = "";
		switch (group) {
		case 0:
			strGroupMessage = "（A组）";
			break;
		case 1:
			strGroupMessage = "（B组）";
			break;
		case 2:
			strGroupMessage = "（C组）";
			break;
		case 3:
			strGroupMessage = "（D组）";
			break;
		case 4:
			strGroupMessage = "（E组）";
			break;
		default:
			strGroupMessage = "（A组）";
			break;
		}
		return strGroupMessage;
	}
}
