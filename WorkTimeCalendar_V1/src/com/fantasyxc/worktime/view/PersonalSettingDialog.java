package com.fantasyxc.worktime.view;

import com.fantasyxc.worktime.util.Constants;
import com.fantasyxc.worktimecalendar.R;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PersonalSettingDialog extends Dialog {

	private Context context;

	private RadioGroup rgOndutyGroup;
	private RadioButton rbOndutyGroupA;
	private RadioButton rbOndutyGroupB;
	private RadioButton rbOndutyGroupC;
	private RadioButton rbOndutyGroupD;
	private RadioButton rbOndutyGroupE;

	private ToggleButton tbDayAndNightSwitch;
	private ToggleButton tbMaintainDaySwitch;

	private Button btnSave;
	private Button btnCancel;

	private SharedPreferences spPersonalSetting;

	public PersonalSettingDialog(Context context) {
		super(context);
		this.context = context;

		// set layout
		setContentView(R.layout.overall_personalsetting);

		spPersonalSetting = context.getSharedPreferences(
				Constants.PERSONALSETTING_SPNAME, context.MODE_PRIVATE);
		int savedgroup = spPersonalSetting.getInt(
				Constants.PERSONALSETTING_SELECTEDGROUP, 0);
		int saveddanswitch = spPersonalSetting.getInt(
				Constants.PERSONALSETTING_ISDISPLAY_DAYANDNIGHT, 0);
		int savedmtswitch = spPersonalSetting.getInt(
				Constants.PERSONALSETTING_ISDISPLAY_MAINTAINDAY, 0);
//		System.out.println("PersonalSettingDialog: savedgroup=" + savedgroup);

		// initialize radio button
		rgOndutyGroup = (RadioGroup) findViewById(R.id.rg_ondutygroup);
		rbOndutyGroupA = (RadioButton) findViewById(R.id.rb_ondutygroupa);
		rbOndutyGroupB = (RadioButton) findViewById(R.id.rb_ondutygroupb);
		rbOndutyGroupC = (RadioButton) findViewById(R.id.rb_ondutygroupc);
		rbOndutyGroupD = (RadioButton) findViewById(R.id.rb_ondutygroupd);
		rbOndutyGroupE = (RadioButton) findViewById(R.id.rb_ondutygroupe);

		// set selection
		switch (savedgroup) {
		case 0:
			rbOndutyGroupA.setChecked(true);
			break;
		case 1:
			rbOndutyGroupB.setChecked(true);
			break;
		case 2:
			rbOndutyGroupC.setChecked(true);
			break;
		case 3:
			rbOndutyGroupD.setChecked(true);
			break;
		case 4:
			rbOndutyGroupE.setChecked(true);
			break;
		default:
			rbOndutyGroupA.setChecked(true);
			break;
		}

		// initialize toggle button
		tbDayAndNightSwitch = (ToggleButton) findViewById(R.id.tb_dayandnightswitch);
		tbMaintainDaySwitch = (ToggleButton) findViewById(R.id.tb_maintaindayswitch);

		// set selection
		if (1 == saveddanswitch) {
			tbDayAndNightSwitch.setChecked(true);
		} else {
			tbDayAndNightSwitch.setChecked(false);
		}

		if (1 == savedmtswitch) {
			tbMaintainDaySwitch.setChecked(true);
		} else {
			tbMaintainDaySwitch.setChecked(false);
		}

		// set dialog title
		setTitle("个人设置");

		// initialize button
		btnSave = (Button) findViewById(R.id.btn_ClockDetailSave);
		btnSave.setOnClickListener(saveListener);

		btnCancel = (Button) findViewById(R.id.btn_ClockDetailCancel);
		btnCancel.setOnClickListener(cancelListener);
	}

	private View.OnClickListener saveListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// get the selected group
			int iSelectedGroup = 0;
			switch (rgOndutyGroup.getCheckedRadioButtonId()) {
			case R.id.rb_ondutygroupa:
				iSelectedGroup = 0;
				break;
			case R.id.rb_ondutygroupb:
				iSelectedGroup = 1;
				break;
			case R.id.rb_ondutygroupc:
				iSelectedGroup = 2;
				break;
			case R.id.rb_ondutygroupd:
				iSelectedGroup = 3;
				break;
			case R.id.rb_ondutygroupe:
				iSelectedGroup = 4;
				break;

			default:
				break;
			}

			// get the day and night switch
			int iDisplayDayAndNight = tbDayAndNightSwitch.isChecked() ? 1 : 0;
			int iDisplayMaintainDay = tbMaintainDaySwitch.isChecked() ? 1 : 0;

			// save to shared preferences
			SharedPreferences.Editor editorPersonalSetting = spPersonalSetting
					.edit();
			editorPersonalSetting.putInt(
					Constants.PERSONALSETTING_SELECTEDGROUP, iSelectedGroup);
			editorPersonalSetting.putInt(
					Constants.PERSONALSETTING_ISDISPLAY_DAYANDNIGHT,
					iDisplayDayAndNight);
			editorPersonalSetting.putInt(
					Constants.PERSONALSETTING_ISDISPLAY_MAINTAINDAY,
					iDisplayMaintainDay);
			editorPersonalSetting.commit();

			Toast.makeText(context, "保存成功！", Toast.LENGTH_SHORT).show();
			dismiss();
		}
	};

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

	private View.OnClickListener cancelListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			dismiss();
		}
	};
}
