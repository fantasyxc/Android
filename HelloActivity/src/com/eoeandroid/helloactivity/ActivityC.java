package com.eoeandroid.helloactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityC extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityc);
		
		Button button1 = (Button)findViewById(R.id.buttonc);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				EditText editText = (EditText)findViewById(R.id.etActivityc);
				String val = editText.getText().toString();
				data.putExtra("getdata1", val);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
			
		});
	}
}
