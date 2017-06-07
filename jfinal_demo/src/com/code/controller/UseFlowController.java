package com.code.controller;

import java.util.List;

import com.code.utils.DiCloudUtil;
import com.code.utils.Responser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class UseFlowController extends Controller{

	
	public void index() {
		render("usrll.html");
	}
	
	/**
	 * 获取使用的流量
	 * @throws JsonProcessingException 
	 */
	public void getUseLl() throws JsonProcessingException {
		
		Responser res = new Responser();
		String return_code = "SUCCESS";
		String return_msg = "获取成功";
		
		String openId = getPara("openid");
		//获取openid对应的ID
		List<Record> result= Db.find(" select id from sys_openid where openId = ? ", openId);
		if(result.size()!=0){
			String ID = result.get(0).getStr("id");
			//s设备信息
			Record list = Db
					.find(" select u.* from usr_pro u RIGHT JOIN sys_openid s on u.oId=s.id WHERE s.id=?  ", ID)
					.get(0);
			//获取用户这个月续费多少次
			long num = Db.queryLong(
					" SELECT count(u.id) FROM usr_buy u RIGHT JOIN sys_openid s ON s.id = u.oId WHERE date_format(u.gmsj, '%Y-%m') = date_format(now(), '%Y-%m') AND u.oId=? ",
					ID);
			//看用户设备本月用了多少流量
			String Dc_token = DiCloudUtil.GetToken();
			String useFlow = DiCloudUtil.GetDevUesInfo(Dc_token ,list.getStr("devSn"));
			if(useFlow==null || "".equals(useFlow)){
				useFlow = "0";
			}
			int useFlowRes= Integer.valueOf(useFlow);
			int syll = (int) (num * 1024 - useFlowRes);
			String dev =  list.getStr("ssId");
			return_msg=syll+"&"+dev;
		}else{
			return_msg="用户不存在";
		}
		res.put("return_msg", return_msg);
		res.put("return_code", return_code);
		renderText(res.GetResponse().toString());
	}
}
