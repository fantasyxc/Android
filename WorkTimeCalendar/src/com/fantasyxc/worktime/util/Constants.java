package com.fantasyxc.worktime.util;

public class Constants {
	// on duty
	public static final String referenceDate = "2014-10-03 00:00:00";
	public static final String[] strOndutyTeam = {
			"��A�飩��ֵ�ദ����������\nֵ����Ա�����������������Ρ�ף־��",
			"��B�飩��ֵ�ദ������������\nֵ����Ա�����¡����ڡ��Ż�������",
			"��C�飩��ֵ�ദ������������\nֵ����Ա����Z����ѡ����ࡢ����",
			"��D�飩��ֵ�ദ��������γ\nֵ����Ա���������������Ө����ά",
			"��E�飩��ֵ�ദ�������ĳ�\nֵ����Ա��Ӧ�������������������"};

	// database
	public static final String DATABASE_NAME = "worktime.db";
	public static final int DATABASE_VERSION = 12;
	public static final String TABLENAME_CLOCK = "tbclock";
	public static final String[] TABLECOLUMENAME_CLOCK = { "_id", "clocktime",
			"lable", "switch", "remark", "ondutygroup" };

	// Clock
	public static final String[] GROUPNAME_CLOCK = { "A��", "B��", "C��", "D��",
			"E��" };
}
