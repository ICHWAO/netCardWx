package com.code.controller;

import java.util.ArrayList;
import java.util.List;

import com.code.model.usr_buy;
import com.code.model.usr_tc;
import com.code.utils.Responser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class GmjlController extends Controller {

	public void index() {
		render("gmjl.html");
	}

	public void getList() throws Exception {
		Responser res = new Responser();
		String return_code = "SUCCESS";
		String openId = getPara("openid");
		List<usr_buy> rl = new ArrayList<usr_buy>();
			// 查询购买记录
			List<Record> result = Db.find(
					" select ub.*,ut.tcmc,ut.lsjg  from usr_buy ub RIGHT JOIN usr_tc ut on ub.tcid = ut.id where ub.oId = ? and ub.zt = 1 ORDER BY ub.gmsj desc ",
					openId);
			int size = result.size();
			if (result.size() != 0) {
				for (int i = 0; i < size; i++) {
					usr_buy utr = new usr_buy();
					Record r = result.get(i);
					utr.setId(r.getStr("id"));
					utr.setTcid(r.getStr("tcid"));
					utr.setTcmc(r.getStr("tcmc"));
					utr.setGmjg(r.getDouble("lsjg"));
					utr.setoId(r.getStr("oId"));
					utr.setGmsj(r.getDate("gmsj"));
					rl.add(utr);
				}
			}
		res.putArray("return_msg", rl);
		res.put("return_code", return_code);
		renderText(res.GetResponse().toString());
	}
}
