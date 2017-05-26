package com.code.controller;

import java.util.ArrayList;
import java.util.List;

import com.code.model.usr_tc;
import com.code.utils.Responser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class TcController extends Controller{

	public void index() {
		render("tc.html");
	}
	
	public void getTcList() throws JsonProcessingException{
		Responser res = new Responser();
		List<Record> list = Db.find(" select id,tcmc,lsjg from usr_tc  ");
		List<usr_tc> result = new ArrayList<usr_tc>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			usr_tc UsrTcTemp = new usr_tc();
			UsrTcTemp.setId(list.get(i).get("id").toString());
			UsrTcTemp.setTcmc(list.get(i).get("tcmc").toString());
			UsrTcTemp.setLsjg(Double.valueOf(list.get(i).get("lsjg").toString()));
			result.add(UsrTcTemp);
		}
		res.put("return_code", "SUCCESS");
		res.putArray("return_msg", result);
		renderText(res.GetResponse());
	}
	/**
	 * 获取使用的流量
	 */
	public void getUseLl(){
		
	}
	
	/*public void pay(){
		String token = getPara("token");
	}*/
}
