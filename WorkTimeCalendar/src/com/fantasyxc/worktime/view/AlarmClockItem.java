package com.fantasyxc.worktime.view;

import android.util.Log;

public class AlarmClockItem {
	private int id;
	private String clocktime;
	private String lable;
	private int openswitch;
	private String remark;
	private int group;

	public AlarmClockItem() {
		// TODO Auto-generated constructor stub
	}

	public AlarmClockItem(int id, String clocktime, String lable,
			int openswitch, String remark, int group) {
		// TODO Auto-generated constructor stub
		// Log.d("AlarmClockItem", "constructor start");
		this.id = id;
		this.clocktime = clocktime;
		this.lable = lable;
		this.openswitch = openswitch;
		this.remark = remark;
		this.group = group;
		// Log.d("AlarmClockItem", "constructor end");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClocktime() {
		return clocktime;
	}

	public void setClocktime(String clocktime) {
		this.clocktime = clocktime;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public boolean getOpenswitch() {
		if (openswitch == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setOpenswitch(int openswitch) {
		this.openswitch = openswitch;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
}
