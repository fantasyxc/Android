package com.fantasyxc.worktime.view;

import com.fantasyxc.worktimecalendar.R;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class AboutDialog extends Dialog {
	
	private Button btnClose;

	public AboutDialog(Context context) {
		super(context);
		// set layout
		setContentView(R.layout.overall_about_dialog);
		setTitle("关于本软件");
		
		btnClose = (Button) findViewById(R.id.btn_Close);
		btnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (KeyEvent.KEYCODE_BACK == event.getKeyCode()
				&& KeyEvent.ACTION_DOWN == event.getAction()) {
			dismiss();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
