package com.fantasyxc.worktime.activity;

import com.fantasyxc.worktime.activity.adapter.MyAlarmClockAdapter;
import com.fantasyxc.worktime.view.AlarmClockItem;
import com.fantasyxc.worktime.view.ClockDetailDialog;
import com.fantasyxc.worktimecalendar.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class AlarmClockActivity extends Activity {

	private ClockDetailDialog dialogClockDetail;
	private MyAlarmClockAdapter alarmclockAdapter;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		Log.d("AlarmClockActivity", "set content view");
		setContentView(R.layout.alarmclock_activity);
		context = this;

		// init ListView
		ListView listviewAlarmClock = (ListView) findViewById(R.id.listview_alarmclock);
		alarmclockAdapter = new MyAlarmClockAdapter(AlarmClockActivity.this);
		alarmclockAdapter.readFromDatabase();
		listviewAlarmClock.setAdapter(alarmclockAdapter);

		// click listview item
		listviewAlarmClock.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AlarmClockItem alarmclockItem = alarmclockAdapter
						.getClockByClickedItem(position);
				dialogClockDetail = new ClockDetailDialog(context,
						alarmclockItem, alarmclockAdapter);
				dialogClockDetail.setCancelable(false); // set as model dialog
				dialogClockDetail.show();
			}
		});

		// set button background

		// change to alarmclockLayout
		LinearLayout overallLayout = (LinearLayout) findViewById(R.id.alarmclock_layout_overall);
		overallLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentOverall = new Intent(AlarmClockActivity.this,
						MainActivity.class);
				startActivity(intentOverall);
				overridePendingTransition(0, 0);
			}
		});

		// change to personalLayout
		LinearLayout personalLayout = (LinearLayout) findViewById(R.id.alarmclock_layout_personal);
		personalLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentPersonal = new Intent(AlarmClockActivity.this,
						PersonalActivity.class);
				startActivity(intentPersonal);
				overridePendingTransition(0, 0);
			}
		});
	}

}
