package cn.eoe.wiki.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import cn.eoe.wiki.utils.WikiUtil;
import cn.eoe.wiki.view.SliderLayer;
import cn.eoe.wiki.view.SliderLayer.SliderListener;

public abstract class SliderActivity extends BaseActivity implements SliderListener{
	public static final 		String	 KEY_SLIDER_INDEX = "slider_index";
	protected MainActivity 		mMainActivity;
	protected  int 				mSliderIndex ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMainActivity = getWikiApplication().getMainActivity();
		if(mMainActivity==null) {
			throw new NullPointerException("You should start the MainActivity firstly");
		}
		mSliderIndex = getIntent().getIntExtra(KEY_SLIDER_INDEX, 0);
		//add the slider listener
		getmMainActivity().getSliderLayer().addSliderListener(this);
	}

	@Override
	protected void onDestroy() {
		getmMainActivity().getSliderLayer().removeSliderListener(this);
		super.onDestroy();
	}

	public MainActivity getmMainActivity() {
		return mMainActivity;
	}
	/**
	 * �رյ�ǰ��slider
	 */
	public void closeSlider() {
		SliderLayer layer = getmMainActivity().getSliderLayer();
		layer.closeSidebar(layer.openingLayerIndex());
	}
	

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(mSliderIndex!=getmMainActivity().getSliderLayer().openingLayerIndex()) {
			//����Ѿ��ر��ˣ��򲻽����κεĴ����¼�
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void slidebarOpened() {
		WikiUtil.hideSoftInput(getWindow().getDecorView());
		onSlidebarOpened();
		getmMainActivity().getSliderLayer().removeSliderListener(this);
	}

	@Override
	public void slidebarClosed() {
		onSlidebarClosed();
		getmMainActivity().getSliderLayer().removeSliderListener(this);
	}

	@Override
	public boolean contentTouchedWhenOpening() {
		return false;
	}

	public abstract void onSlidebarOpened();
	public abstract void onSlidebarClosed();
}
