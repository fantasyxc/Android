package cn.eoe.wiki;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.eoe.wiki.utils.L;
import cn.eoe.wiki.R;
import android.content.Context;

public class WikiConfig {
	private static boolean debug = false;
	private static boolean persistLog = false;
	private static String mainCategoruUrl = null;
	private static boolean init = false;
	
	public static void initConfig(Context context) {
		if (init) {
			return;
		}
		try {
			InputStream inputStream = context.getResources().openRawResource(R.raw.config);
			Properties properties = new Properties();
			properties.load(inputStream);
			debug = Boolean.valueOf(properties.getProperty("debug"));
			L.e("Debug: " + debug);
			persistLog = Boolean.valueOf(properties.getProperty("persistLog"));
			L.e("persistLog: " + persistLog);
			
			mainCategoruUrl = properties.getProperty("mainCategoruUrl");
			L.e("mainCategoruUrl: " + mainCategoruUrl);
			init = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isDebug() {
		return debug;
	}
	
	public static boolean isPersistLog() {
		return persistLog;
	}
	
	public static String getMainCategoruUrl() {
		return mainCategoruUrl;
	}
}
