package com.fantasyxc.worktime.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.fantasyxc.worktime.activity.adapter.MyCalendarAdapter;
import com.fantasyxc.worktime.util.Constants;
import com.fantasyxc.worktimecalendar.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnGestureListener {

	private GestureDetector gestureDetector = null;
	private MyCalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0;
	private static int jumpYear = 0;
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";

	private Context context;

	private boolean mReadyExit;
	private Timer mExitTimer;
	private ExitTask mExitTask;
	private final static String ACTION_EXIT = "com.fantasyxc.ACTION_EXIT";

	public MainActivity() {
		// TODO Auto-generated constructor stub
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(date);
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// init
		context = this; // context
		gestureDetector = new GestureDetector(this); // touch gesture
		mExitTimer = new Timer();

		// init grid view
		calV = new MyCalendarAdapter(this, getResources(), jumpMonth, jumpYear,
				year_c, month_c, day_c);
		addGridView();
		gridView.setAdapter(calV);

		// set title of calendar
		topText = (TextView) findViewById(R.id.tv_month);
		addTextToTopTextView(topText);

		// click button
		LinearLayout preMonth = (LinearLayout) findViewById(R.id.btn_pre_month);
		LinearLayout nextMonth = (LinearLayout) findViewById(R.id.btn_next_month);
		preMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addGridView();
				jumpMonth--;

				calV = new MyCalendarAdapter(context, getResources(),
						jumpMonth, jumpYear, year_c, month_c, day_c);
				gridView.setAdapter(calV);
				addTextToTopTextView(topText);
			}
		});

		nextMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addGridView();
				jumpMonth++;

				calV = new MyCalendarAdapter(context, getResources(),
						jumpMonth, jumpYear, year_c, month_c, day_c);
				gridView.setAdapter(calV);
				addTextToTopTextView(topText);
			}
		});

		// load today onduty message
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentTime = sdf.format(date);
		ShowOndutyMsgByDate(currentTime + ":00");

		// set back ground
		// LinearLayout overallLayout = (LinearLayout)
		// findViewById(R.id.layout_overall);
		// overallLayout.setBackgroundColor(Color.GRAY);

		// change to alarmclockLayout
		LinearLayout alarmclockLayout = (LinearLayout) findViewById(R.id.layout_alarmclock);
		alarmclockLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAlarmClock = new Intent(MainActivity.this,
						AlarmClockActivity.class);
				startActivity(intentAlarmClock);
				overridePendingTransition(0, 0);
			}
		});

		// change to personalLayout
		LinearLayout personalLayout = (LinearLayout) findViewById(R.id.layout_personal);
		personalLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentPersonal = new Intent(MainActivity.this,
						PersonalActivity.class);
				startActivity(intentPersonal);
				overridePendingTransition(0, 0);
			}
		});

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > 120) {
			addGridView();
			jumpMonth++;

			calV = new MyCalendarAdapter(this, getResources(), jumpMonth,
					jumpYear, year_c, month_c, day_c);
			gridView.setAdapter(calV);
			addTextToTopTextView(topText);

			return true;
		} else if (e1.getX() - e2.getX() < -120) {

			addGridView();
			jumpMonth--;

			calV = new MyCalendarAdapter(this, getResources(), jumpMonth,
					jumpYear, year_c, month_c, day_c);
			gridView.setAdapter(calV);
			addTextToTopTextView(topText);

			return true;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.gestureDetector.onTouchEvent(event);
	}

	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("��")
				.append(calV.getShowMonth()).append("��").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	private void addGridView() {
		gridView = (GridView) findViewById(R.id.gridview);

		gridView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return MainActivity.this.gestureDetector.onTouchEvent(event);
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				// get day
				String clickedDay = calV.getDateByClickItem(position).split(
						"\\.")[0];

				if (position < calV.getDayofweek()) {
					// last month
					addGridView();
					jumpMonth--;

					calV = new MyCalendarAdapter(context, getResources(),
							jumpMonth, jumpYear, year_c, month_c, Integer
									.parseInt(clickedDay));
					gridView.setAdapter(calV);
					addTextToTopTextView(topText);

				} else if (position < calV.getDayofweek()
						+ calV.getDaysofMonth()) {
					// current month
					calV.setCurrentFlag(position);
					calV.notifyDataSetChanged();

				} else {
					// next month
					addGridView();
					jumpMonth++;

					calV = new MyCalendarAdapter(context, getResources(),
							jumpMonth, jumpYear, year_c, month_c, Integer
									.parseInt(clickedDay));
					gridView.setAdapter(calV);
					addTextToTopTextView(topText);
				}

				// show the clicked on duty message
				String clickedTime = calV.getShowYear() + "-"
						+ calV.getShowMonth() + "-" + clickedDay + " 00:00:00";
				ShowOndutyMsgByDate(clickedTime);
			}
		});
	}

	private void ShowOndutyMsgByDate(String chooseDate) {
		// format date to millseconds
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long referenceTime = 0;
		long chooseTime = 0;
		try {
			formater.setLenient(false);
			chooseTime = formater.parse(chooseDate).getTime();
			referenceTime = formater.parse(Constants.referenceDate).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// calculate date sub
		long lDateSub = (chooseTime - referenceTime) / (1000 * 60 * 60 * 24);

		// get team number
		int teamNumber = ((int) lDateSub) % 5;
		if (teamNumber < 0) {
			teamNumber += 5;
		}

		// get on duty group
		int groupDay = (teamNumber * 2) % 5;
		int groupNight = (teamNumber * 2 + 1) % 5;

		// get on duty message
		String strOndutyMsg = "�װ�" + Constants.strOndutyTeam[groupDay] + "\n����"
				+ Constants.strOndutyTeam[groupNight];

		// set on duty message
		TextView tvCurrentOnduty = (TextView) findViewById(R.id.textview_Current_Onduty);
		tvCurrentOnduty.setTextColor(Color.BLACK);
		tvCurrentOnduty.setText(strOndutyMsg);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
//		Log.d("MainActivity", "dispatchKeyEvent:" + event.getKeyCode());
		int keyCode = event.getKeyCode();
		int keyAction = event.getAction();

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
//			Log.d("MainActivity", "keyAction:" + keyAction);
			if (KeyEvent.ACTION_DOWN == keyAction) {
//				Log.d("MainActivity", "keyAction down");
				if (false == mReadyExit) {
					mReadyExit = true;
					Toast.makeText(this, R.string.tip_exit, Toast.LENGTH_SHORT)
							.show();
					if (null != mExitTask) {
						mExitTask.cancel();
					}
					mExitTask = new ExitTask();
					mExitTimer.schedule(mExitTask, 2000);
				} else {
					sendBroadcast(new Intent(ACTION_EXIT));
					finish();
				}
				return true;
			}
		}

		return super.dispatchKeyEvent(event);
	}

	class ExitTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mReadyExit = false;
		}

	}
}
