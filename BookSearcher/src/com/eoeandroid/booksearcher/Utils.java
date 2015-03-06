package com.eoeandroid.booksearcher;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Utils {

	public final static String TAG = "Utils tag: ";

	public static NetResponse download(String url) {
		Log.v(TAG, "download from: " + url);
		NetResponse ret = downloadFromDouban(url);
		Log.v(TAG, " -- downloaded from douban!!!");
		JSONObject message = null;
		try {
			message = new JSONObject(String.valueOf(ret.getMessage()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		int errorCode = 0;
		switch (ret.getCode()) {
		case BookAPI.RESPONSE_CODE_SUCCEED:
			Log.v(TAG, "-- Succeed.");
			ret.setMessage(parseBookInfo(message));
			break;
		default:
			Log.v(TAG, "-- Get error message!");
			errorCode = parseErrorCode(message);
			ret.setCode(errorCode);
			ret.setMessage(getErrorMessage(errorCode));
			break;
		}

		return ret;
	}

	private static NetResponse downloadFromDouban(String url) {
		NetResponse ret = new NetResponse(
				BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION, null);
		BufferedReader reader = null;
		HttpClient client = null;
		HttpResponse response = null;

		BasicHttpParams httpParams = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
				httpParams, schemeRegistry);

		client = new DefaultHttpClient(cm, httpParams);

		try {
			response = client.execute(new HttpGet(url));
			ret.setCode(response.getStatusLine().getStatusCode());

			StringBuffer sb = new StringBuffer();
			String line;
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			Log.v(TAG, "The content of Book: " + sb.toString());
			ret.setMessage(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != client) {
				client.getConnectionManager().shutdown();
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return ret;
	}

	private static BookInfo parseBookInfo(JSONObject json) {
		if (null == json) {
			return null;
		}
		BookInfo bookInfo = null;

		try {
			bookInfo = new BookInfo();
			Log.v(TAG, "-- parse title");	
			bookInfo.setTitle(json.getString(BookAPI.TAG_TITLE));
			Log.v(TAG, "-- parse cover");
			bookInfo.setCover(downloadBitmap((json.getString(BookAPI.TAG_COVER))));
			Log.v(TAG, "-- parse author");
			bookInfo.setAuthor(parseJSONArray2String(
					json.getJSONArray(BookAPI.TAG_AUTHOR), " "));
			Log.v(TAG, "-- parse publisher");
			bookInfo.setPublisher(json.getString(BookAPI.TAG_PUBLISHER));
			Log.v(TAG, "-- parse publishdate");
			bookInfo.setPublishDate(json.getString(BookAPI.TAG_PUBLISHDATE));
			Log.v(TAG, "-- parse isbn");
			bookInfo.setISBN(json.getString(BookAPI.TAG_ISBN));
			Log.v(TAG, "-- parse summary");
			bookInfo.setSummary(json.getString(BookAPI.TAG_SUMMARY).replace(
					"\n", "\n\n"));
			Log.v(TAG, "-- all content parsed!");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return bookInfo;
	}

	private static Bitmap downloadBitmap(String url) {
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		Bitmap bm = null;

		Log.v(TAG, "-- download Bitmap from: " + url);

		try {
			Log.v(TAG, "-- connect.");
			conn = (HttpURLConnection) (new URL(url)).openConnection();
			Log.v(TAG, "-- input stream.");
			is = conn.getInputStream();
			Log.v(TAG, "-- buffer.");
			bis = new BufferedInputStream(is);
			Log.v(TAG, "-- decode.");
			bm = BitmapFactory.decodeStream(bis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bis) {
					bis.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;
	}

	private static String parseJSONArray2String(JSONArray json, String split) {
		if ((null == json) || (json.length() < 1)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < json.length(); i++) {
			try {
				sb = sb.append(json.getString(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			sb = sb.append(split);
		}
		sb.deleteCharAt(sb.length() - 1);
		Log.v(TAG,
				"parseJSONArray2String(" + json.toString() + "): "
						+ sb.toString());
		return sb.toString();
	}

	private static int parseErrorCode(JSONObject json) {
		int ret = BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION;
		if (null == json) {
			return ret;
		}
		try {
			ret = json.getInt(BookAPI.TAG_ERROR_CODE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static int getErrorMessage(int errorCode) {
		int ret = R.string.error_message_default;
		switch (errorCode) {
		case BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION:
			ret = R.string.error_message_net_exception;
			break;
		case BookAPI.RESPONSE_CODE_ERROR_BOOK_NOT_FOUND:
			ret = R.string.error_message_book_not_found;
			break;
		default:
			break;
		}
		return ret;
	}
}
