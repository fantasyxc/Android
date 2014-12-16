package com.example.hellolayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	TextView showinfo;
	String[] titles={"张三", "李四", "王五", "赵六", "吴敏"};
	String[] texts={"15500000000", "15500000011", "15500000022", "15500000033", "15527990722"};
	int buf = R.drawable.ic_launcher;
	int[] resIds = {buf, buf, buf, buf, buf};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		listView = (ListView) this.findViewById(R.id.list);
		showinfo = (TextView) this.findViewById(R.id.T1);
		listView.setAdapter(new MyAdapter(titles, texts, resIds));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView title = (TextView)view.findViewById(R.id.itemTitle);
				String info = "单击的联系人是：" + title.getText();
				TextView text = (TextView)view.findViewById(R.id.itemText);
				info = info + "\n联系人电话：" + text.getText();
				showinfo.setText(info);
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
	
	public class MyAdapter extends BaseAdapter {
		
		String[] itemTitles, itemTexts;
		int[] itemImageRes;
		
		public MyAdapter (String[] itemTitles, String[] itemTexts, int[] itemImageRes){
			System.out.println("================== myadpater constructor ==================");
			this.itemTexts = itemTexts;
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
			System.out.println("================== get view ==================");
			if (null == convertView) {
				LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View itemView = inflater.inflate(R.layout.item, null);
				TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
				title.setText(itemTitles[position]);
				TextView text = (TextView) itemView.findViewById(R.id.itemText);
				text.setText(itemTexts[position]);
				ImageView image = (ImageView)itemView.findViewById(R.id.itemImage);
				image.setImageResource(itemImageRes[position]);
				return itemView;
			}
			else {
				TextView title = (TextView) convertView.findViewById(R.id.itemTitle);
				title.setText(itemTitles[position]);
				TextView text = (TextView) convertView.findViewById(R.id.itemText);
				text.setText(itemTexts[position]);
				ImageView image = (ImageView) convertView.findViewById(R.id.itemImage);
				image.setImageResource(itemImageRes[position]);
				return convertView;
			}
		}
	}
}

