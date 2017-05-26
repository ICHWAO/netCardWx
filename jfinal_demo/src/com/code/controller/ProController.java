package com.code.controller;

import java.util.List;

import com.code.utils.DiCloudUtil;
import com.code.utils.Responser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ProController extends Controller{
	
	public void index() {
		render("pros.html");
	}
	
	public void update() throws JsonProcessingException{
		Responser res = new Responser();
		String return_code = "ERROR";
		String return_msg = "绑定失败";
		String SSID = getPara("ssid");
		String openId = getPara("openid");
		//验证设备是否存在
		String Dc_token = DiCloudUtil.GetToken();
		if(DiCloudUtil.GetDevBasc(Dc_token,SSID)){
			//存在的话
			List<Record> list= Db.find(" select id from sys_openid where openId = ? ", openId);
			if(list.size()!=0){
				String id = list.get(0).get("id");
				int flag = Db.update(" update usr_pro set ssId = ? where oId = ? ", SSID, id);
				if(flag==1){
					return_code="SUCCESS";
					return_msg="绑定成功";
				}
			}
		}else{
			//不存在的话
			return_msg="设备不存在,或者未开机(请开机后稍微尝试)";
		}
		
		res.put("return_msg", return_msg);
		res.put("return_code", return_code);
		renderText(res.GetResponse().toString());
	}
	
}
