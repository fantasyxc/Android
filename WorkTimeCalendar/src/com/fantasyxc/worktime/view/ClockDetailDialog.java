package com.fantasyxc.worktime.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fantasyxc.worktime.activity.adapter.MyAlarmClockAdapter;
import com.fantasyxc.worktime.broadcastreceiver.CallAlarmBroadcastReceiver;
import com.fantasyxc.worktime.db.DatabaseHelper;
import com.fantasyxc.worktime.util.Constants;
import com.fantasyxc.worktime.util.OndutyDateCalculate;
import com.fantasyxc.worktimecalendar.R;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ClockDetailDialog extends Dialog {
	private AlarmClockItem alarmclockItem;
	private Spinner spGroup;
	private ArrayAdapter<String> spGrouAdpater;
	private EditText etTimeHour, etTimeMinute;
	private CheckBox cbSwitch;
	private Button btnSave;
	private Button btnCancel;

	private Context context;
	MyAlarmClockAdapter alarmclockAdapter;

	public ClockDetailDialog(Context context, AlarmClockItem aci,
			MyAlarmClockAdapter alarmclockAdapter) {
		super(context);
		this.context = context;
		alarmclockItem = aci;
		this.alarmclockAdapter = alarmclockAdapter;

		// set layout
		setContentView(R.layout.alarmclock_dialog_itemdetail);

		// set Title
		setTitle(alarmclockItem.getLable());

		// init group
		spGroup = (Spinner) findViewById(R.id.sp_ClockDetailGroup);
		spGrouAdpater = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, Constants.GROUPNAME_CLOCK);
		spGrouAdpater
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spGroup.setAdapter(spGrouAdpater);
		// set default group selection
		spGroup.setSelection(alarmclockItem.getGroup());

		// init clock time
		etTimeHour = (EditText) findViewById(R.id.et_ClockDetailTimeHour);
		etTimeMinute = (EditText) findViewById(R.id.et_ClockDetailTimeMinute);
		String strClockTime = alarmclockItem.getClocktime();
		etTimeHour.setText(strClockTime.substring(11, 13));
		etTimeMinute.setText(strClockTime.substring(14, 16));

		// init switch
		cbSwitch = (CheckBox) findViewById(R.id.cb_ClockDetailSwitch);
		cbSwitch.setChecked(alarmclockItem.getOpenswitch());

		// init button
		btnSave = (Button) findViewById(R.id.btn_ClockDetailSave);
		btnSave.setOnClickListener(saveListener);

		btnCancel = (Button) findViewById(R.id.btn_ClockDetailCancel);
		btnCancel.setOnClickListener(cancelListener);

	}

	private View.OnClickListener saveListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (0 == ClockDetailSave()) {
				dismiss();
				// update view
				alarmclockAdapter.refreshData();
			}
		}
	};

	private View.OnClickListener cancelListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			dismiss();
		}
	};

	private int ClockDetailSave() {
		Log.d("ClockDetailDialog", " ---- ClockDetailSave ----");

		// get value
		int iSwitch = cbSwitch.isChecked() ? 1 : 0;
		int iGroup = (int) spGroup.getSelectedItemId();

		// judge the input
		String strTimeHour = etTimeHour.getText().toString();
		String strTimeMinute = etTimeMinute.getText().toString();
		if (false == OndutyDateCalculate.isNumeric(strTimeHour)
				|| false == OndutyDateCalculate.isNumeric(strTimeMinute)) {
			Toast.makeText(context, "您输入的时间格式不正确！\n请输入合法的数字再保存",
					Toast.LENGTH_SHORT).show();
			return -1;
		}
		if (0 > Integer.parseInt(strTimeHour)
				&& 23 < Integer.parseInt(strTimeHour)) {
			Toast.makeText(context, "您输入的时钟格式不正确！\n请输入0~23之间的任意整数再保存！",
					Toast.LENGTH_SHORT).show();
			return -2;
		}
		if (0 > Integer.parseInt(strTimeMinute)
				&& 59 < Integer.parseInt(strTimeMinute)) {
			Toast.makeText(context, "您输入的分钟格式不正确！\n请输入0~59之间的任意整数再保存！",
					Toast.LENGTH_SHORT).show();
			return -3;
		}
		if (strTimeHour.length() < 2) {
			strTimeHour = "0" + strTimeHour;
		}
		if (strTimeMinute.length() < 2) {
			strTimeMinute = "0" + strTimeMinute;
		}

		String strClockTime = strTimeHour + ":" + strTimeMinute;
		String strClockDate = OndutyDateCalculate.getNextOndutyDate(
				alarmclockItem, Integer.parseInt(strTimeHour),
				Integer.parseInt(strTimeMinute));

		String strClockDateTime = strClockDate + " " + strClockTime;

		// save to database
		DatabaseHelper dbh = new DatabaseHelper(context);
		SQLiteDatabase sqldb = dbh.getWritableDatabase();
		ContentValues cvs = new ContentValues();
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[0], alarmclockItem.getId());
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[1], strClockDateTime); // time
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[2], alarmclockItem.getLable()); // label
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[3], iSwitch); // switch
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[4], alarmclockItem.getRemark()); // remarks
		cvs.put(Constants.TABLECOLUMENAME_CLOCK[5], iGroup); // group

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

		Toast.makeText(context, "闹钟保存成功 :)", Toast.LENGTH_SHORT).show();

		return 0;
	}
}
