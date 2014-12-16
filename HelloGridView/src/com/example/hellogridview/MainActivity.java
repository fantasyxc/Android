package com.example.hellogridview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridView gridview;
		String[] titles = {"张三", "李四", "王五", "赵六", "吴敏"};
		int buf = R.drawable.ic_launcher;
		int[] resIds = {buf, buf, buf, buf, buf};
		gridview = (GridView)findViewById(R.id.gridview);
		gridview.setAdapter(new MyAdapter(titles, resIds));
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView title = (TextView) view.findViewById(R.id.itemText);
				Log.d("Mygridview: ", "我单击的是：" + title.getText() + "的照片");
			}
		});
	}
	
	public class MyAdapter extends BaseAdapter {

		String[] itemTitles;
		int[] itemImageRes;
		
		public MyAdapter(String[] itemTitles, int[] itemImageRes) {
			// TODO Auto-generated constructor stub
			this.itemTitles = itemTitles;
			this.itemImageRes = itemImageRes;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemTitles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemTitles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (null == convertView) {
				LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View itemView = inflater.inflate(R.layout.griditem, null);
				TextView title = (TextView) itemView.findViewById(R.id.itemText);
				title.setText(itemTitles[position]);
				ImageView image = (ImageView) itemView.findViewById(R.id.itemImage);
				image.setImageResource(itemImageRes[position]);
				return itemView;
			}
			else {
				TextView title = (TextView) convertView.findViewById(R.id.itemText);
				title.setText(itemTitles[position]);
				ImageView image = (ImageView) convertView.findViewById(R.id.itemImage);
				image.setImageResource(itemImageRes[position]);
				return convertView;
			}
		}
		
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
