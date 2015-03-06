package com.fantasyxc.worktime.util;

public class Constants {
	// on duty
	public static final String referenceDate = "2014-10-03 00:00:00";
	public static final String[] strOndutyTeam = {
			"（A组），值班处长助理：刘浩\n值班人员：马凯凯、冯阳、洪涛、祝志刚",
			"（B组），值班处长助理：王勇涛\n值班人员：朱勇、李磊、张欢、樊杨",
			"（C组），值班处长助理：杨淑龙\n值班人员：朱璟、孙佳、周青、杨洋",
			"（D组），值班处长助理：冯纬\n值班人员：张宇、周龙华、吴莹、赵维",
			"（E组），值班处长助理：夏畅\n值班人员：应海波、李泓、金念、杜锐"};

	// database
	public static final String DATABASE_NAME = "worktime.db";
	public static final int DATABASE_VERSION = 12;
	public static final String TABLENAME_CLOCK = "tbclock";
	public static final String[] TABLECOLUMENAME_CLOCK = { "_id", "clocktime",
			"lable", "switch", "remark", "ondutygroup" };

	// Clock
	public static final String[] GROUPNAME_CLOCK = { "A组", "B组", "C组", "D组",
			"E组" };
}
