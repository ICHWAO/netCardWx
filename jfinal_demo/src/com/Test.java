package com;

import com.code.model.stop2start;
import com.code.utils.Conts;
import com.code.utils.DiCloudUtil;
import com.code.utils.HttpRequest;

import net.sf.json.JSONObject;

public class Test {
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String array [] = new String [1];
		array[0] = "db02-6715-0100-2044";
		stop2start ss = new stop2start();
		ss.setAppLimit("1");
		ss.setWebLimit("1");
		ss.setDevSn(array);
		ss.setRxSpeed("");
		ss.setTxSpeed("");
		ss.setToken(DiCloudUtil.GetToken());
		String result = HttpRequest.sendPost(Conts.DiCouldUrl+Conts.open2Stop, JSONObject.fromObject(ss).toString());
		System.out.println(result);	
	}
}
