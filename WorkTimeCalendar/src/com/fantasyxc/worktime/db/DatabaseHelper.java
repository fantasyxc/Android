package com.fantasyxc.worktime.db;

import com.fantasyxc.worktime.util.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null,
				Constants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		Log.d("DatabaseHelper", "onCreate");
		String strCreateDatabaseTable = "create table "
				+ Constants.TABLENAME_CLOCK + "("
				+ Constants.TABLECOLUMENAME_CLOCK[0]
				+ " integer primary key autoincrement, "
				+ Constants.TABLECOLUMENAME_CLOCK[1] + " text, "
				+ Constants.TABLECOLUMENAME_CLOCK[2] + " text, "
				+ Constants.TABLECOLUMENAME_CLOCK[3] + " integer, "
				+ Constants.TABLECOLUMENAME_CLOCK[4] + " text, "
				+ Constants.TABLECOLUMENAME_CLOCK[5] + " integer)";
		Log.d("DatabaseHelper", "create table:" + strCreateDatabaseTable);
		try {
			db.execSQL(strCreateDatabaseTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("DatabaseHelper", "onUpgrate");
		if (oldVersion == newVersion) {
			return;
		}
		db.execSQL("DROP TABLE IF EXIST " + Constants.TABLENAME_CLOCK);
		onCreate(db);
	}

}
