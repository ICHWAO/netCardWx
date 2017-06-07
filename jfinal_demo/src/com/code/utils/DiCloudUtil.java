package com.code.utils;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DiCloudUtil {

	/**
	 * 调用接口查询token
	 * 
	 * @return
	 */
	public static String GetToken() {
		try {
			String hash = MD5.MD5Encode(Conts.DO_MIAN + Conts.USER + Conts.Passward + getUtcTime());
			String param = "domain=" + Conts.DO_MIAN + "&user=" + Conts.USER + "&time=" + getUtcTime() + "&hash="
					+ hash;
			String result = HttpRequest.sendGet(Conts.DiCouldUrl + Conts.Get_Dc_Token, param.replace(" ", "%20"));
			return (String) JSONObject.fromObject(result).get("token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取终端设备信息
	 * @return 设备sn号
	 */
	public static String GetDevBasc(String token,String devId){
		String  res = null;
		try {
			String param = "token=" + token + "&nextId=" + 0 + "&count=" + 100000;
			String result = HttpRequest.sendGet(Conts.DiCouldUrl + Conts.Get_Dec_basic,param);
			JSONArray ja = (JSONArray) JSONObject.fromObject(result).get("data");
			int size = ja.size();
			for (int i = 0; i < size; i++) {
				JSONObject jo =  (JSONObject) ja.get(i);
				if(devId.equals(jo.get("ssid"))){
					res = jo.getString("devSn");
				}
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 获取设备本月使用情况
	 */
	public static String GetDevUesInfo(String token,String devSn){
		try {
			String param = "token=" + token + "&devSn=" + devSn;
			String result = HttpRequest.sendGet(Conts.DiCouldUrl + Conts.Get_Dev_UseInfo,param);
			JSONObject jo = JSONObject.fromObject(result);
			return jo.getString("usedFlow");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取UTC时间
	 * 
	 * @return yyyy-MM-dd%20HH:mm:ss
	 */
	private static String getUtcTime() {
		Date l_datetime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone l_timezone = TimeZone.getTimeZone("GMT-0");
		formatter.setTimeZone(l_timezone);
		return formatter.format(l_datetime);
	}

}
