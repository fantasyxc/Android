package com.example.helloinput;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class MainActivity extends ActionBarActivity {
	
	private ImageView imageView1;
	private EditText editText1;
	private Spinner spinner1;
	private RadioGroup radioGroup1;
	private CheckBox checkBox1, checkBox2;
	private ToggleButton toggleButton1;
	private ArrayAdapter<String> adapter;
	private DatePicker datePicker1;
	private Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findwidget();
	}
	
	private void findwidget() {
		
		// image
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		imageView1.setImageResource(R.drawable.ic_launcher);
		
		// edittext
		editText1 = (EditText)findViewById(R.id.editText1);
		editText1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// radiogroup
		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
		radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radioButton1:
					Log.d("Input:", "select RadionButton1");
					break;
				case R.id.radioButton2:
					Log.d("Input:", "select RadionButton2");
					break;
				default:
					break;
				}
			}
		});
		
		// checkBox
		checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
		checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
		CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				switch (buttonView.getId()) {
				case R.id.checkBox1:
					Log.d("Input", "checkbox1:"+isChecked+"="+buttonView.getText());
					break;
				case R.id.checkBox2:
					Log.d("Input", "checkbox2:"+isChecked+"="+buttonView.getText());
					break;
				case R.id.toggleButton1:
					Log.d("Input", "toggleButon1:"+isChecked+"="+buttonView.getText());
					break;
				default:
					break;
				}
			}
		};
		checkBox1.setOnCheckedChangeListener(listener);
		checkBox2.setOnCheckedChangeListener(listener);
		
		// toggle button
		toggleButton1 = (ToggleButton)findViewById(R.id.toggleButton1);
		toggleButton1.setOnCheckedChangeListener(listener);
		
		// date picker
		datePicker1 = (DatePicker)findViewById(R.id.dataPicker1);
		datePicker1.init(2014, 12, 16, new DatePicker.OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				Log.d("Input", "您选择的日期是：" + year + "年" + monthOfYear + "月" + (dayOfMonth + 1) + "日");
			}
		});
		
		// spinner
		final String[] from = {"中国", "美国", "俄罗斯", "Canada"};
		spinner1 = (Spinner)findViewById(R.id.spinner1);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, from);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("Input: ", "我单击的是spinner1: " + from[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		// button
		button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Input: ", "我单击的是：button");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
