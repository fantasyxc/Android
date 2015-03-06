package com.fantasyxc.worktime.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fantasyxc.worktime.view.AlarmClockItem;

public class OndutyDateCalculate {
	public static String getNextOndutyDate(AlarmClockItem alarmclockItem, int hour, int minute) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date todayDate = new Date();
		String currentDateandTime = sdf.format(todayDate);

		long referenceTime = 0;
		long currentTime = 0;
		try {
			sdf.setLenient(false);
			referenceTime = sdf.parse(Constants.referenceDate).getTime();
			currentTime = sdf.parse(currentDateandTime).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// calculate date sub
		long lDateSub = (currentTime - referenceTime) / (1000 * 60 * 60 * 24);

		int standardifference = 0;
		switch (alarmclockItem.getGroup()) {
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
