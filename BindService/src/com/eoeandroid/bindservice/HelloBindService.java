package com.eoeandroid.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class HelloBindService extends Service{

	private final IBinder mBinder = new LocalBinder();
	private String BOOKNAME =  "Android����������ʵ���ڶ���";
	
	public class LocalBinder extends Binder {
		HelloBindService getService() {
			return HelloBindService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "�ɹ���Service", 1000).show();
		return mBinder;
	}
	
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "�ɹ�ȡ����Service", 1000).show();
		return false;
	}

	public String getBookName() {
		return BOOKNAME;
	}
}