package com.fantasyxc.worktime.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fantasyxc.worktime.view.AlarmClockItem;

public class OndutyDateCalculate {
	public static int getDutyType(int year, int month, int day, int groupid,
			int flag, int lastDaysofMonth) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String calculateDateandTime = "";

		long referenceTime = 0;
		long calculateTime = 0;

		int tmp = 0;
		try {
			
			if (-1 == flag) {
				// last month
				calculateDateandTime = year + "-" + month + "-01 00:00:00";
				tmp = 0 - day;
			} else if (0 == flag) {
				// current month
				calculateDateandTime = year + "-" + month + "-" + day
						+ " 00:00:00";
				calculateTime = sdf.parse(calculateDateandTime).getTime();
				tmp = 0;
			} else {
				// next month
				calculateDateandTime = year + "-" + month + "-"
						+ lastDaysofMonth + " 00:00:00";
				tmp = day;
			}
			
			sdf.setLenient(false);
			referenceTime = sdf.parse(Constants.REFERENCEDATE).getTime();
			calculateTime = sdf.parse(calculateDateandTime).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// calculate date sub
		long lDateSub = (calculateTime - referenceTime) / (1000 * 60 * 60 * 24);

		int standardifference = getStandardDifference(groupid);

		// get team number
		int teamNumber = ((int) lDateSub + tmp - standardifference) % 5;
		if (teamNumber < 0) {
			teamNumber += 5;
		}

		// return
		if (0 == teamNumber) {
			// day
			return 1;
		} else if (2 == teamNumber) {
			// night
			return 2;
		} else {
			// not on duty
			return 0;
		}
	}

	private static int getStandardDifference(int group) {
		int standardifference = 0;
		switch (group) {
		case 0:
			standardifference = 0;
			break;
		case 1:
			standardifference = 3;
			break;
		case 2:
			standardifference = 1;
			break;
		case 3:
			standardifference = 4;
			break;
		case 4:
			standardifference = 2;
			break;
		default:
			standardifference = 0;
			break;
		}
		return standardifference;
	}

	public static String getNextOndutyDate(AlarmClockItem alarmclockItem,
			int hour, int minute) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date todayDate = new Date();
		String currentDateandTime = sdf.format(todayDate);

		long referenceTime = 0;
		long currentTime = 0;
		try {
			sdf.setLenient(false);
			referenceTime = sdf.parse(Constants.REFERENCEDATE).getTime();
			currentTime = sdf.parse(currentDateandTime).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// calculate date sub
		long lDateSub = (currentTime - referenceTime) / (1000 * 60 * 60 * 24);

		int standardifference = getStandardDifference(alarmclockItem.getGroup());

		// get team number
		int teamNumber = ((int) lDateSub - standardifference) % 5;
		if (teamNumber < 0) {
			teamNumber += 5;
		}

		int id = alarmclockItem.getId();
		if (0 == id) {
			// day
			switch (teamNumber) {
			case 0:
				if ((hour < todayDate.getHours())
						|| (hour == todayDate.getHours() && minute < todayDate
								.getMinutes())) {
					todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60
							* 24 * 5);
				}
				break;
			case 1:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 4);
				break;
			case 2:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 3);
				break;
			case 3:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 2);
				break;
			case 4:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 1);
				break;
			default:
				break;
			}
		} else {
			// night
			switch (teamNumber) {
			case 0:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 2);
				break;
			case 1:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 1);
				break;
			case 2:
				if ((hour < todayDate.getHours())
						|| (hour == todayDate.getHours() && minute < todayDate
								.getMinutes())) {
					todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60
							* 24 * 5);
				}
				break;
			case 3:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 4);
				break;
			case 4:
				todayDate = new Date(todayDate.getTime() + 1000 * 60 * 60 * 24
						* 3);
				break;
			default:
				break;
			}
		}

		String strOndutyDate = sdf.format(todayDate);
		strOndutyDate = strOndutyDate.substring(0, 10);
		return strOndutyDate;
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
