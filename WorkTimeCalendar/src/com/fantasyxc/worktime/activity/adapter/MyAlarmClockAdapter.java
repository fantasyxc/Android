package com.fantasyxc.worktime.activity.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.fantasyxc.worktime.activity.AlarmClockActivity;
import com.fantasyxc.worktime.broadcastreceiver.CallAlarmBroadcastReceiver;
import com.fantasyxc.worktime.db.DatabaseHelper;
import com.fantasyxc.worktime.util.Constants;
import com.fantasyxc.worktime.util.OndutyDateCalculate;
import com.fantasyxc.worktime.view.AlarmClockItem;
import com.fantasyxc.worktimecalendar.R;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyAlarmClockAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<AlarmClockItem> alarmclock = new ArrayList<AlarmClockItem>();

	public MyAlarmClockAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.alarmclock_list_item, null);
		}

		// show clock
		showClock(position, convertView);

		return convertView;
	}

	public void readFromDatabase() {
		// clear original clock
		alarmclock.clear();
		Log.d("AlarmClockAdapter", "read from database");
		DatabaseHelper dbh = new DatabaseHelper(context);
		SQLiteDatabase mydb = dbh.getReadableDatabase();
		Cursor cursor = mydb.query(Constants.TABLENAME_CLOCK, new String[] {
				Constants.TABLECOLUMENAME_CLOCK[0],
				Constants.TABLECOLUMENAME_CLOCK[1],
				Constants.TABLECOLUMENAME_CLOCK[2],
				Constants.TABLECOLUMENAME_CLOCK[3],
				Constants.TABLECOLUMENAME_CLOCK[4],
				Constants.TABLECOLUMENAME_CLOCK[5] }, null, null, null, null,
				null);

		// init if cursor is empty
		Log.d("AlarmClockAdapter", "init if cursor is empty");
		if (0 == cursor.getCount()) {
			initDatabase();
			cursor = mydb.query(Constants.TABLENAME_CLOCK, new String[] {
					Constants.TABLECOLUMENAME_CLOCK[0],
					Constants.TABLECOLUMENAME_CLOCK[1],
					Constants.TABLECOLUMENAME_CLOCK[2],
					Constants.TABLECOLUMENAME_CLOCK[3],
					Constants.TABLECOLUMENAME_CLOCK[4],
					Constants.TABLECOLUMENAME_CLOCK[5] }, null, null, null,
					null, null);
		}

		while (cursor.moveToNext()) {
			// get element
			Log.d("AlarmClockAdapter", "get element");
			int id = cursor.getInt(cursor
					.getColumnIndex(Constants.TABLECOLUMENAME_CLOCK[0]));
			String clocktime = cursor.getString(cursor
					.getColumnIndex(Constants.TABLECOLUMENAME_CLOCK[1]));
			String lable = cursor.getString(cursor
					.getColumnIndex(Constants.TABLECOLUMENAME_CLOCK[2]));
			int openswitch = cursor.getInt(cursor
					.getColumnIndex(Constants.TABLECOLUMENAME_CLOCK[3]));
			String remark = cursor.getString(cursor
					.getColumnIndex(Constants.TABLECOLUMENAME_CLOCK[4]));
			int group = cursor.getInt(cursor
					.getColumnIndex(Constants.TABLECOLUMENAME_CLOCK[5]));

			AlarmClockItem alarmclockItem = new AlarmClockItem(id, clocktime,
					lable, openswitch, remark, group);

			// update clock time to the nearest date
			Log.d("AlarmClockAdapter", "update clock time to the nearest date");
			int hour = Integer.parseInt(clocktime.substring(11, 13));
			System.out.println("------------------\nhour=" + hour);
			int minute = Integer.parseInt(clocktime.substring(14, 16));
			System.out.println("------------------\nminute=" + minute);
			String strNewClockTime = OndutyDateCalculate.getNextOndutyDate(
					alarmclockItem, hour, minute);
			strNewClockTime = strNewClockTime + " "
					+ clocktime.substring(11, 16);
			System.out.println("------------------\nstrNewClockTime="
					+ strNewClockTime);
			alarmclockItem.setClocktime(strNewClockTime);

			// save to database
			Log.d("AlarmClockAdapter", "save to database");
			if (false == clocktime.equals(strNewClockTime)) {
				saveToDatabase(alarmclockItem);
			}

			// parse clock time from String to Date
			Log.d("AlarmClockAdapter", "parse clock time from String to Date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dAlarmTime = new Date();
			try {
				sdf.setLenient(false);
				dAlarmTime = sdf.parse(strNewClockTime);
				dAlarmTime.setSeconds(0);
			} catch (ParseException e) {
				System.err.println("Date time parse error in readFromDatabase");
				e.printStackTrace();
			}

			System.out
					.println("==============================\nstrNewClockTime = "
							+ strNewClockTime
							+ ", Alarm time = "
							+ dAlarmTime.toGMTString());

			// set clock
			Log.d("AlarmClockAdapter", "set clock");
			Intent intent = new Intent();
			intent.setAction("com.fantasyxc.worktime.action.MYALARMRECEIVER");
			intent.putExtra("strClockTitle", lable);
			intent.putExtra("iClockGroup", group);
			intent.putExtra("strRemark", remark);
			PendingIntent pi = PendingIntent.getBroadcast(
					context.getApplicationContext(), id, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);

			if (0 != openswitch) {
				// add alarm clock
				am.set(AlarmManager.RTC_WAKEUP, dAlarmTime.getTime(), pi);
			} else {
				// cancel original alarm clock
				am.cancel(pi);
			}

			// add to list
			Log.d("AlarmClockAdapter", "add to list");
			alarmclock.add(alarmclockItem);
		}
		mydb.close();
	}

	public void saveToDatabase(AlarmClockItem alarmclockItem) {
		Log.d("saveToDatabase", "\\\\\\\\\\\\\\\\\\\\\\n");
		// save to database
		DatabaseHelper dbh = new DatabaseHelper(context);
		SQLiteDatabase sqldb = dbh.getWritableDatabase();
		ContentValues cvs = new ContentValues();
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[0], alarmclockItem.getId());
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[1],
				alarmclockItem.getClocktime()); // time
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[2], alarmclockItem.getLable()); // lable
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[3],
				alarmclockItem.getOpenswitch()); // swtich
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[4], alarmclockItem.getRemark()); // remarks
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[5], alarmclockItem.getGroup()); // group

		try {
			sqldb.update(Constants.TABLENAME_CLOCK, cvs,
					Constants.TABLECOLUMENAME_CLOCK[0] + "=?",
					new String[] { "" + alarmclockItem.getId() });
		} catch (Exception e) {
			sqldb.close();
			Log.d("ClockDetailDialog", "update failure!");
			Toast.makeText(context, "update failure in clockdetaildialog",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		sqldb.close();
	}

	private void showClock(int position, View convertView) {
		// get message
		AlarmClockItem alarmclockItem = alarmclock.get(position);
		String title = alarmclockItem.getLable();
		int group = alarmclockItem.getGroup();
		boolean openswitch = alarmclockItem.getOpenswitch();
		String clocktime = alarmclockItem.getClocktime();
		// to use
		// String remark = alarmclockItem.getRemark();

		// show
		TextView tvTitle = (TextView) convertView
				.findViewById(R.id.tv_AlarmclockItemLable);
		TextView tvGroup = (TextView) convertView
				.findViewById(R.id.tv_AlarmclockItemGroup);
		TextView tvState = (TextView) convertView
				.findViewById(R.id.tv_AlarmclockItemSwitch);
		TextView tvClockTime = (TextView) convertView
				.findViewById(R.id.tv_AlarmclockItemTime);
		TextView tvClockTimeLable = (TextView) convertView
				.findViewById(R.id.tv_AlarmclockItemTimeText);

		tvTitle.setText(title);
		switch (group) {
		case 0:
			tvGroup.setText("（A组）");
			break;
		case 1:
			tvGroup.setText("（B组）");
			break;
		case 2:
			tvGroup.setText("（C组）");
			break;
		case 3:
			tvGroup.setText("（D组）");
			break;
		case 4:
			tvGroup.setText("（E组）");
			break;
		default:
			tvGroup.setText("（A组）");
			break;
		}

		tvClockTime.setText(clocktime);

		if (openswitch) {
			tvTitle.setTextColor(Color.BLACK);
			tvGroup.setTextColor(Color.BLACK);
			tvState.setTextColor(Color.BLACK);
			tvState.setText("状态：已开启");
			tvClockTime.setTextColor(Color.BLACK);
			tvClockTimeLable.setTextColor(Color.BLACK);
		} else {
			tvTitle.setTextColor(Color.GRAY);
			tvGroup.setTextColor(Color.GRAY);
			tvState.setTextColor(Color.GRAY);
			tvState.setText("状态：已关闭");
			tvClockTime.setTextColor(Color.GRAY);
			tvClockTimeLable.setTextColor(Color.GRAY);
		}

	}

	public void initDatabase() {
		Log.d("AlarmClockAdapter", "init database");
		ContentValues cvs1 = new ContentValues();
		cvs1.put(Constants.TABLECOLUMENAME_CLOCK[0], 0);
		cvs1.put(Constants.TABLECOLUMENAME_CLOCK[1], "2015-01-17 06:00"); // time
		cvs1.put(Constants.TABLECOLUMENAME_CLOCK[2], "白班闹钟"); // lable
		cvs1.put(Constants.TABLECOLUMENAME_CLOCK[3], 1); // swtich
		cvs1.put(Constants.TABLECOLUMENAME_CLOCK[4], "您该起床去上班啦！"); // remarks
		cvs1.put(Constants.TABLECOLUMENAME_CLOCK[5], 0); // group

		ContentValues cvs2 = new ContentValues();
		cvs2.put(Constants.TABLECOLUMENAME_CLOCK[0], 1);
		cvs2.put(Constants.TABLECOLUMENAME_CLOCK[1], "2015-01-16 18:00"); // time
		cvs2.put(Constants.TABLECOLUMENAME_CLOCK[2], "晚班闹钟"); // lable
		cvs2.put(Constants.TABLECOLUMENAME_CLOCK[3], 0); // swtich
		cvs2.put(Constants.TABLECOLUMENAME_CLOCK[4], "您该启程去上班啦！"); // remarks
		cvs2.put(Constants.TABLECOLUMENAME_CLOCK[5], 3); // group

		DatabaseHelper dbh = new DatabaseHelper(context);
		SQLiteDatabase mydb = dbh.getWritableDatabase();
		try {
			mydb.insert(Constants.TABLENAME_CLOCK, null, cvs1);
			mydb.insert(Constants.TABLENAME_CLOCK, null, cvs2);
		} catch (Exception e) {
			Log.d("AlarmClockAdapter", "insert meets exception");
			e.printStackTrace();
		} finally {
			mydb.close();
		}

		mydb.close();
	}

	public void refreshData() {
		readFromDatabase();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alarmclock.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return alarmclock.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public AlarmClockItem getClockByClickedItem(int position) {
		return alarmclock.get(position);
	}
}