package com.eoeandroid.booksearcher;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.support.v7.app.ActionBarActivity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private Button btnScan;
	private ProgressDialog mProgressDialog;
	private DownloadHandler mHandler = new DownloadHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnScan = (Button) findViewById(R.id.main_start_scan);
		btnScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentIntegrator integrator = new IntentIntegrator(
						MainActivity.this);
				integrator.initiateScan();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
				resultCode, data);
		if ((null == result) || (null == result.getContents())) {
			Log.v(Utils.TAG, "User cancel scan by pressing back hardkey.");
			return;
		}

		// progress dialog
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage(getString(R.string.communicating));
		mProgressDialog.show();

		// download thread
		DownloadThread thread = new DownloadThread(BookAPI.URL_ISBN_BASE
				+ result.getContents());
		thread.start();
	};

	private class DownloadThread extends Thread {
		private String mURL;

		public DownloadThread(String url) {
			super();
			mURL = url;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = Message.obtain();
			msg.obj = Utils.download(mURL);
			Log.v("Main Activity: ", "** send message.");
			mHandler.sendMessage(msg);
		}
	}

	private static class DownloadHandler extends Handler {
		private MainActivity mActivity;

		public DownloadHandler(MainActivity activity) {
			super();
			mActivity = activity;
		}

		@Override
		public void handleMessage(Message msg) {
			if ((null == msg.obj) || (null == mActivity.mProgressDialog)) {
				return;
			}
			Log.v("Main Activity: ", "** handle message.");
			mActivity.mProgressDialog.dismiss();
			NetResponse response = (NetResponse) msg.obj;
			if (response.getCode() != BookAPI.RESPONSE_CODE_SUCCEED) {
				Toast.makeText(
						mActivity,
						"["	+ response.getCode() + "]" + mActivity.getString((Integer) response.getMessage()), 
						Toast.LENGTH_SHORT)
						.show();
			} else {
				mActivity.startBookInfoDetailActivity((BookInfo) response.getMessage());
			}
		}
	}
	
	private void startBookInfoDetailActivity(BookInfo bookInfo) {
		if (null == bookInfo) {
			return;
		}
		Intent intent = new Intent(this, BookInfoDetailActivity.class);
		intent.putExtra(BookInfo.class.getName(), bookInfo);
		startActivity(intent);
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
