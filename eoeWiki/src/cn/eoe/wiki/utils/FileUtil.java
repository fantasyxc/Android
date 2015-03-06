package cn.eoe.wiki.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.eoe.wiki.Constants;
import android.os.Environment;
import android.text.TextUtils;

public class FileUtil {
	public static File getExternalStorageDir() {
		return Environment.getExternalStorageDirectory();
	}

	public static String getExternalStoragePath() {
		return getExternalStorageDir().getAbsolutePath();
	}

	public static String getExternalStorageState() {
		return Environment.getExternalStorageState();
	}

	public static boolean isExternalStorageEnable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public static void initExternalDir(boolean cleanFile) {
		if (isExternalStorageEnable()) {
			File external = new File(Constants.EXTERNAL_DIR);
			if (!external.exists()) {
				external.mkdirs();
			}
			File cache = new File(Constants.CACHE_DIR);
			if (!cache.exists()) {
				cache.mkdirs();
			} else {
				if (cleanFile) {
					cleanFile(cache, DateUtil.WEEK_MILLTS);
				}
			}
			File logs = new File(Constants.LOGS_DIR);
			if (!logs.exists()) {
				logs.mkdirs();
			} else {
				if (cleanFile) {
					cleanFile(logs, DateUtil.HALF_MONTH_MILLTS);
				}
			}
		}
	}

	public static int cleanFile(File dir, long maxInterval) {
		File[] files = dir.listFiles();
		if (null == files) {
			return 0;
		}
		int beforeNum = 0;
		long current = System.currentTimeMillis();
		for (File file : files) {
			long lastModifiedTime = file.lastModified();
			if (maxInterval < (current - lastModifiedTime)) {
				file.delete();
				beforeNum++;
			}
		}
		return beforeNum;
	}

	public static String getFileContent(String path) {
		File file = new File(path);
		return getFileContent(file);
	}

	public static String getFileContent(File file) {
		if (!file.exists()) {
			return null;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			byte buffer[] = new byte[2048];
			int len = 0;
			while (-1 != (len = fis.read(buffer))) {
				sb.append(new String(buffer, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			L.e("get file exception: " + file.getAbsolutePath(), e);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {

				}
			}
		}
		return null;
	}

	public static boolean saveFile(String content, String path) {
		if (isExternalStorageEnable()) {
			File cacheFile = new File(path);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(cacheFile);
				fos.write(content.getBytes());
				fos.flush();
				return true;
			} catch (IOException e) {
				L.e("save file exception: " + path, e);
			} finally {
				if (null != fos) {
					try {
						fos.close();
					} catch (IOException e) {

					}
				}
			}
		}
		return false;
	}

	public static boolean deleteFile(String path) {
		if (TextUtils.isEmpty(path)) {
			return true;
		}
		File file = new File(path);
		if (file.exists()) {
			return file.delete();
		}
		return true;
	}

	public static boolean isFileExist(String path) {
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		File file = new File(path);
		return file.exists();
	}
}
