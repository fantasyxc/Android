package com.fantasyxc.worktime.activity.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fantasyxc.worktime.util.Constants;
import com.fantasyxc.worktime.util.LunarCalendar;
import com.fantasyxc.worktime.util.OndutyDateCalculate;
import com.fantasyxc.worktime.util.SpecialCalendar;
import com.fantasyxc.worktimecalendar.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorktimeCalendarAdapter extends BaseAdapter {
	private boolean isLeapyear = false;
	private int daysofMonth = 0;
	private int dayofWeek = 0;
	private int lastDaysofMonth = 0; // days in last month

	private Context context;
	private String[] dayNumber = new String[42]; // date
	private int[] ondutyNumber = new int[42]; // on duty flag, 0 is null, 1 is
												// day and 2 is night

	private SpecialCalendar sc = null;
	private LunarCalendar lc = null;

	private Resources res = null;
	private Drawable drawable = null;

	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private int currentFlag = -1; // flag of picked

	private String showYear = "";
	private String showMonth = "";

	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";

	private SharedPreferences spPersonalSetting;
	private int ondutygroup = 0;
	private int isDisplayDayNight = 0;
	private int isDisplayMaintain = 0;
	private ForegroundColorSpan maintainColor = new ForegroundColorSpan(Color.RED);

	public WorktimeCalendarAdapter() {
		// TODO Auto-generated constructor stub
		Date date = new Date();
		sysDate = sdf.format(date);
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];
	}

	public WorktimeCalendarAdapter(Context context, Resources rs, int jumpMonth,
			int jumpYear, int year_c, int month_c, int day_c) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		this.res = rs;

		// step
		int stepYear = year_c + jumpYear;
		int stepMonth = month_c + jumpMonth;
		if (stepMonth > 0) {
			if (0 == stepMonth % 12) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (0 == stepMonth % 12) {

			}
		}

		currentYear = String.valueOf(stepYear);
		currentMonth = String.valueOf(stepMonth);
		currentDay = String.valueOf(day_c);

		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth), day_c);
	}

	public WorktimeCalendarAdapter(Context context, Resources rs, int year,
			int month, int day) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		this.res = rs;
		currentYear = String.valueOf(year);
		currentMonth = String.valueOf(month);
		currentDay = String.valueOf(day);

		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth), day);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// load layout
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.overall_calendar_item, null);
		}

		TextView calenarTextView = (TextView) convertView
				.findViewById(R.id.tvCalendarText);
		// calendar
		String d = dayNumber[position].split("\\.")[0];
		// luna calendar
		String dv = dayNumber[position].split("\\.")[1];
		// set maintain day string
		if (6 == position && 1 == isDisplayMaintain) {
			dv = "Œ¨ª§»’";
		}

		// set text style
		SpannableString sp = new SpannableString(d + "\n" + dv);
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(0.75f), 0, d.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (null != dv || "" != dv) {

			sp.setSpan(new RelativeSizeSpan(0.75f), d.length() + 1,
					d.length() + 1 + dv.length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			// set maintain day style
			if (6 == position && 1 == isDisplayMaintain) {
				sp.setSpan(maintainColor, d.length() + 1,
						d.length() + 1 + dv.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), d.length() + 1,
						d.length() + 1 + dv.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

		}
		calenarTextView.setText(sp);

		// set text color
		if (position < daysofMonth + dayofWeek && position >= dayofWeek) {
			calenarTextView.setTextColor(Color.BLACK);
		} else {
			// last month day and next month day
			calenarTextView.setTextColor(Color.GRAY);
		}

		// set background
		if (currentFlag == position) {
			// current day
			if (1 == ondutyNumber[position] && 1 == isDisplayDayNight) {
				drawable = res.getDrawable(R.drawable.current_day_daytime_bg);
			} else if (2 == ondutyNumber[position] && 1 == isDisplayDayNight) {
				drawable = res.getDrawable(R.drawable.current_day_nighttime_bg);
			} else {
				drawable = res.getDrawable(R.drawable.current_day_bg);
			}
			calenarTextView.setTextColor(Color.WHITE);
		} else if (sys_year.equals(String.valueOf(showYear))
				&& sys_month.equals(String.valueOf(showMonth))
				&& sys_day.equals(String.valueOf(position - dayofWeek + 1))) {
			// today
			if (1 == ondutyNumber[position] && 1 == isDisplayDayNight) {
				drawable = res.getDrawable(R.drawable.today_daytime_bg);
			} else if (2 == ondutyNumber[position] && 1 == isDisplayDayNight) {
				drawable = res.getDrawable(R.drawable.today_nighttime_bg);
			} else {
				drawable = res.getDrawable(R.drawable.today_bg);
			}
		} else {
			// normal day
			if (1 == ondutyNumber[position] && 1 == isDisplayDayNight) {
				drawable = res.getDrawable(R.drawable.normal_day_daytime_bg);
			} else if (2 == ondutyNumber[position] && 1 == isDisplayDayNight) {
				drawable = res.getDrawable(R.drawable.normal_day_nighttime_bg);
			} else {
				drawable = res.getDrawable(R.drawable.normal_day_bg);
			}
		}
		calenarTextView.setBackgroundDrawable(drawable);

		return convertView;
	}

	public void getCalendar(int year, int month, int day) {
		isLeapyear = sc.isLeapYear(year);
		daysofMonth = sc.getDaysOfMonth(isLeapyear, month);
		dayofWeek = sc.getWeekdayOfMonth(year, month);
		lastDaysofMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
		getWeek(year, month, day);
	}

	private void getWeek(int year, int month, int day) {

		spPersonalSetting = context.getSharedPreferences(
				Constants.PERSONALSETTING_SPNAME, context.MODE_PRIVATE);
		ondutygroup = spPersonalSetting.getInt(
				Constants.PERSONALSETTING_SELECTEDGROUP, 0);
		isDisplayDayNight = spPersonalSetting.getInt(
				Constants.PERSONALSETTING_ISDISPLAY_DAYANDNIGHT, 0);
		isDisplayMaintain = spPersonalSetting.getInt(
				Constants.PERSONALSETTING_ISDISPLAY_MAINTAINDAY, 0);

		int j = 1;
		String lunarDay = "";

		for (int i = 0; i < dayNumber.length; i++) {

			if (i < dayofWeek) {
				// last month
				int tmp = lastDaysofMonth - dayofWeek + 1;
				lunarDay = lc.getLunarDate(year, month - 1, tmp + i, false);
				dayNumber[i] = (tmp + i) + "." + lunarDay;
				ondutyNumber[i] = OndutyDateCalculate.getDutyType(year, month,
						dayofWeek - i, ondutygroup, -1, daysofMonth);
			} else if (i < daysofMonth + dayofWeek) {
				// current month
				lunarDay = lc.getLunarDate(year, month, i - dayofWeek + 1,
						false);
				dayNumber[i] = i - dayofWeek + 1 + "." + lunarDay;

				// set picked day
				if (day == (i - dayofWeek + 1)) {
					currentFlag = i;
				}

				ondutyNumber[i] = OndutyDateCalculate.getDutyType(year, month,
						i - dayofWeek + 1, ondutygroup, 0, daysofMonth);

				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
			} else {
				// next month
				lunarDay = lc.getLunarDate(year, month + 1, j, false);
				dayNumber[i] = j + "." + lunarDay;
				ondutyNumber[i] = OndutyDateCalculate.getDutyType(year, month,
						j, ondutygroup, 1, daysofMonth);
				j++;
			}
		}
	}

	public String getDateByClickItem(int position) {
		return dayNumber[position];
	}

	public String getShowYear() {
		return showYear;
	}

	public int getDayofweek() {
		return dayofWeek;
	}

	public int getDaysofMonth() {
		return daysofMonth;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}

	public void setCurrentFlag(int currentFlag) {
		if (currentFlag >= daysofMonth + dayofWeek) {
			this.currentFlag = daysofMonth + dayofWeek - 1;
		} else {
			this.currentFlag = currentFlag;
		}
	}

	public int getCurrentFlag() {
		return currentFlag;
	}

	public void refreshGridList() {
		getWeek(Integer.parseInt(currentYear), Integer.parseInt(currentMonth),
				Integer.parseInt(currentDay));
		this.notifyDataSetInvalidated();
	}
}
