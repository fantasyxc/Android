package cn.eoe.wiki.utils;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class WikiUtil {
	public static int VERSION_CODE = 0;
	public static String VERSION_NAME = null;
	public static String PACKAGE_NAME = null;

	public static Set<String> SUFFIXSET = new HashSet<String>();
	static {
		SUFFIXSET.add("png");
		SUFFIXSET.add("jpg");
		SUFFIXSET.add("bmp");
		SUFFIXSET.add("gif");
		SUFFIXSET.add("jpeg");
	}

	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int getVerCode(Context context) {
		if (VERSION_CODE <= 0) {
			try {
				VERSION_CODE = context.getPackageManager().getPackageInfo(
						getPackageName(context), 0).versionCode;
			} catch (NameNotFoundException e) {
				L.e("not find the name package", e);
			}
		}
		return VERSION_CODE;
	}

	public static String getVerName(Context context) {
		if (TextUtils.isEmpty(VERSION_NAME)) {
			try {
				VERSION_NAME = context.getPackageManager().getPackageInfo(
						getPackageName(context), 0).versionName;
			} catch (NameNotFoundException e) {
				L.e("not find the name package", e);
			}
		}
		return VERSION_NAME;
	}

	public static String getPackageName(Context context) {
		if (TextUtils.isEmpty(PACKAGE_NAME)) {
			PACKAGE_NAME = context.getApplicationInfo().packageName;
		}
		return PACKAGE_NAME;
	}

	public static void hideSoftInput(View view) {
		if (null == view) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
		}
	}

	public static void showSoftInput(View view) {
		if (null == view) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	public static boolean getNetworkStatus(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isAvailable()
				|| !networkInfo.isConnected()) {
			return false;
		} else {
			return true;
		}
	}

	public static int getSystemVersionCode() {
		return android.os.Build.VERSION.SDK_INT;
	}

	public static int getResourceColor(int resId, Context context) {
		return context.getResources().getColor(resId);
	}

	public static boolean isImageUrl(String url) {
		if (TextUtils.isEmpty(url))
			return false;
		String suffix = null;
		int index = url.lastIndexOf(".");
		int length = url.length();
		if (index > 0 && index < (length - 1)) {
			suffix = url.substring(index + 1, url.length());
		}
		if (!TextUtils.isEmpty(suffix)) {
			L.d("suffix is " + suffix);
			if (SUFFIXSET.contains(suffix.toLowerCase()))
				return true;
		}
		return false;
	}
}
