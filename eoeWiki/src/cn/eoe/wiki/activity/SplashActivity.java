package cn.eoe.wiki.activity;

import android.content.Intent;
import android.os.Bundle;
import cn.eoe.wiki.R;
import cn.eoe.wiki.utils.FileUtil;
import cn.eoe.wiki.utils.L;
import cn.eoe.wiki.utils.ThreadPoolUtil;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		L.p("---SplashActivity super onCreate.");
		super.onCreate(savedInstanceState);
		L.p("---SplashActivity onCreate.");
		setContentView(R.layout.splash);
		initComponent();
		initData();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	void initComponent() {
		
	}

	void initData() {
		ThreadPoolUtil.execute(new Runnable() {
			@Override
			public void run() {
				FileUtil.initExternalDir(true);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(mContext, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
