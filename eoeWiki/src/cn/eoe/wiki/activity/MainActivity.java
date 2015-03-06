package cn.eoe.wiki.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.umeng.analytics.MobclickAgent;
import com.umeng.common.net.l;

import cn.eoe.wiki.WikiApplication;
import cn.eoe.wiki.utils.L;
import cn.eoe.wiki.utils.WikiUtil;
import cn.eoe.wiki.view.SliderEntity;
import cn.eoe.wiki.view.SliderLayer;
import cn.eoe.wiki.R;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends ActivityGroup {
	private static WikiApplication mWikiApplication;
	private MainActivity mMainActivity;
	private LocalActivityManager mActivityManager;
	private SliderLayer mSliderLayers;
	private boolean mReadyExit;
	private Timer mExitTimer;
	private ExitTask mExitTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mWikiApplication = WikiApplication.getApplication();
		mMainActivity = this;
		mWikiApplication.setMainActivity(mMainActivity);
		setContentView(R.layout.main);

		mActivityManager = getLocalActivityManager();
		mExitTimer = new Timer();

		mSliderLayers = (SliderLayer) findViewById(R.id.animation_layout);

		MobclickAgent.onEvent(this, "home", "enter");
		int screenWidth = WikiUtil.getScreenWidth(mMainActivity);
		ViewGroup layerOne = (ViewGroup) findViewById(R.id.animation_layout_one);
		layerOne.setPadding(0, 0, WikiUtil.dip2px(mMainActivity, 20), 0);
		ViewGroup layerTwo = (ViewGroup) findViewById(R.id.animation_layout_two);
		layerOne.setPadding(0, 0, WikiUtil.dip2px(mMainActivity, 15), 0);
		ViewGroup layerThree = (ViewGroup) findViewById(R.id.animation_layout_three);
		layerOne.setPadding(0, 0, 0, 0);
		mSliderLayers.addLayer(new SliderEntity(layerOne, 0, screenWidth, 0));
		mSliderLayers.addLayer(new SliderEntity(layerTwo, 0, screenWidth
				- WikiUtil.dip2px(mMainActivity, 23), 0));
		mSliderLayers.addLayer(new SliderEntity(layerThree, WikiUtil.dip2px(
				mMainActivity, -10), screenWidth
				- WikiUtil.dip2px(mMainActivity, 20), 0));

		Intent intent = new Intent(this, MainCategoryActivity.class);
		showView(0, intent);
	}

	public void showView(final int index, Intent intent) {
		intent.putExtra(SliderActivity.KEY_SLIDER_INDEX, index);
		String id = String.valueOf(System.currentTimeMillis());
		View view = mActivityManager.startActivity(id, intent).getDecorView();
		ViewGroup currentView = mSliderLayers.getLayer(index);
		currentView.removeAllViews();
		currentView.addView(view);

		if (0 == index) {
			return;
		}
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mSliderLayers.openSidebar(index);
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mSliderLayers.isAnimationing()) {
			return true;
		} else {
			return super.dispatchTouchEvent(ev);
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		L.e("MainActivity dispatchKeyEvent:" + event.getKeyCode());
		int keyCode = event.getKeyCode();
		int keyAction = event.getAction();

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			L.e("MainActivity keyAction:" + keyAction);
			if (KeyEvent.ACTION_DOWN == keyAction) {
				int index = mSliderLayers.openingLayerIndex();
				if (0 < index) {
					mSliderLayers.closeSidebar(index);
				} else {
					if (false == mReadyExit) {
						mReadyExit = true;
						Toast.makeText(mMainActivity, R.string.tip_exit,
								Toast.LENGTH_SHORT).show();
						if (null != mExitTask) {
							mExitTask.cancel();
						}
						mExitTask = new ExitTask();
						mExitTimer.schedule(mExitTask, 2000);
					} else {
						sendBroadcast(new Intent(BaseActivity.ACTION_EXIT));
						finish();
					}
				}
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	
	public SliderLayer getSliderLayer() {
		return mSliderLayers;
	}
	
	class ExitTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mReadyExit = false;
		}
		
	}
}
