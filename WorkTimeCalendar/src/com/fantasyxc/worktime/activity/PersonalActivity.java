package com.fantasyxc.worktime.activity;

import com.fantasyxc.worktimecalendar.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class PersonalActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_activity);

		// test
		MediaPlayer mp = MediaPlayer.create(this, R.raw.sound_alarmclock);
		mp.start();
		// /******////

		// Notification noti = new Notification();
		// // noti.defaults |= Notification.DEFAULT_SOUND;
		// noti.defaults |= Notification.DEFAULT_VIBRATE;
		// noti.sound =
		// Uri.parse(Environment.getExternalStorageDirectory().getPath() +
		// "/music/sound_alarmclock.mp3");
		//
		// NotificationManager notiManage = (NotificationManager)
		// getSystemService(this.NOTIFICATION_SERVICE);
		// notiManage.notify(1, noti);

		// // *****************////

		// set button background

		// change to alarmclockLayout
		LinearLayout overallLayout = (LinearLayout) findViewById(R.id.personal_layout_overall);
		overallLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentOverall = new Intent(PersonalActivity.this,
						MainActivity.class);
				startActivity(intentOverall);
				overridePendingTransition(0, 0);
			}
		});

		// change to personalLayout
		LinearLayout alarmclockLayout = (LinearLayout) findViewById(R.id.personal_layout_alarmclock);
		alarmclockLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAlarmClock = new Intent(PersonalActivity.this,
						AlarmClockActivity.class);
				startActivity(intentAlarmClock);
				overridePendingTransition(0, 0);
			}
		});
	}
}
