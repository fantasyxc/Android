package com.fantasyxc.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class GetWebMessage implements Runnable {

	/**
	 * get all contents in web
	 */
	public String getHtmlContent(String htmlurl) {
		URL url;
		String temp;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(htmlurl);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream(), "gbk"));// 读取网页全部内容
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}
			in.close();
		} catch (final MalformedURLException me) {
			System.out.println("你输入的URL格式有问题!");
			me.getMessage();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(date);
		GetWebMessage t = new GetWebMessage();
		
		System.out.println("Searching at " + nowTime + " ...");
		
		String[] websiteName = { "http://www.poxiao.com" };
		String[] movieName = { "匆匆那年" };

		String alertMessage;
		for (int j = 0; j < websiteName.length; j++) {
			String content = t.getHtmlContent(websiteName[j]);
			for (int i = 0; i < movieName.length; i++) {
				// get selected movie
				if (-1 < content.indexOf(movieName[i])) {
					alertMessage = "《" + movieName[i] + "》在" + websiteName[j]
							+ "有更新啦！";
					// alert
					System.out.println(alertMessage);
					JOptionPane.showMessageDialog(null, alertMessage);
				}
			}
		}
	}

	public static void main(String[] args) {
		// Thread pool
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		long peroid = 60;
		service.scheduleAtFixedRate(new GetWebMessage(), 0, peroid,
				TimeUnit.MINUTES);
		
		
	}

}
