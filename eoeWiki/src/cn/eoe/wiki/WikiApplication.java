package cn.eoe.wiki;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Application;
import cn.eoe.wiki.activity.MainActivity;
import cn.eoe.wiki.utils.L;
import cn.eoe.wiki.R;

public class WikiApplication extends Application {
	public static WikiApplication application;
	public static StringBuilder wikiHtml;
	public MainActivity mainActivity;

	public static WikiApplication getApplication() {
		if (null == application) {
			throw new NullPointerException(
					"The application may be not running!");
		}
		return application;
	}

	public MainActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		application = this;
		WikiConfig.initConfig(application);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		L.e("onTerminate");
	}

	public static StringBuilder getWikiHtml() {
		if (null == wikiHtml) {
			try {
				InputStream inputStream = getApplication().getResources()
						.openRawResource(R.raw.html);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						inputStream));
				wikiHtml = new StringBuilder();
				String line;
				while (null != (line = br.readLine())) {
					wikiHtml.append(line);
				}
			} catch (Exception e) {
				L.e("read html file exception:" + e.getMessage());
				throw new NullPointerException("can not read the html file");
			}
		}
		return wikiHtml;
	}
}
