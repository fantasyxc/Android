package cn.eoe.wiki.activity;

import org.codehaus.jackson.map.ObjectMapper;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import cn.eoe.wiki.WikiApplication;
import android.content.BroadcastReceiver;

/**
 * 所有activity的基类，在这里可以处理一些所有页面都需要初始化的代码
 * 
 * @author <a href="mailto:fantasyxc@vip.qq.com">Chang Xia</a>
 * @data 2015-01-07
 * @version 1.0.1
 */
public class BaseActivity extends Activity {
	public static final String ACTION_EXIT = "cn.eoe.wiki.ACTION_EXIT";
	protected WikiApplication mWikiApplication = null;
	protected BaseActivity mContext = null;
	public ObjectMapper mObjectMapper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MobclickAgent.updateOnlineConfig(this);
		MobclickAgent.onError(this);

		mContext = this;
		mWikiApplication = WikiApplication.getApplication();
		mObjectMapper = new ObjectMapper();
		registerReceiver(exitReceiver, new IntentFilter(ACTION_EXIT));
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(exitReceiver);
	}

	public WikiApplication getWikiApplication() {
		return mWikiApplication;
	}

	private BroadcastReceiver exitReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (ACTION_EXIT.equals(intent.getAction())) {
				mContext.finish();
			}
		}
	};
}
