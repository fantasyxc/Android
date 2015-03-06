package cn.eoe.wiki;

import java.io.File;

import cn.eoe.wiki.utils.FileUtil;

public class Constants {
	public static final String APP_NAME = "eoeWiki";
	public static String EXTERNAL_DIR = FileUtil.getExternalStoragePath()
			+ File.separator + APP_NAME;
	public static String CACHE_DIR = EXTERNAL_DIR + File.separator + "cache";
	public static String LOGS_DIR = EXTERNAL_DIR + File.separator + "logs";

	public static String API_ACTION_RAW = "raw";
	public static String API_ACTION_PARSE = "parse";
	public static String API_ACITON_QUERY = "query";

	public static String API_HOST = "http://wiki.eoeandroid.com";

	public static String API_PATH_INDEX = "index.php";
	public static String API_PATH_API = "api.php";
}
