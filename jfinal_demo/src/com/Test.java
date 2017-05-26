package com;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.code.utils.HttpRequest;
import com.code.utils.MD5;

public class Test {

	private static String domain = "yueshiying";
	private static String user = "ysyapi2";
	private static String password = "123456";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Date l_datetime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone l_timezone = TimeZone.getTimeZone("GMT-0");
		formatter.setTimeZone(l_timezone);
		String time = formatter.format(l_datetime);

		String url = "http://yueshiying.dimiccs.com:3080/api/v1/auth";
		String hash = MD5.MD5Encode(domain + user + password + time);
		String param = "domain=" + domain + "&user=" + user + "&time=" + time + "&hash=" + hash;
		String  result = HttpRequest.sendGet(url, param.replace(" ", "%20"));
		System.out.println(result);
	}
	

}
