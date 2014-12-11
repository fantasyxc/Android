package com.eoeandroid.helloactivity;

import android.app.Activity;
import android.os.Bundle;

public class ActivityB extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
		super.onCreate(savedInstanceState);
		System.out.println("BBBBBBBBBBBBBB111111111111");
		setContentView(R.layout.activityb);
	}
}
