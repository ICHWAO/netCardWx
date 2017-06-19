package com.code.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

import com.code.model.stop2start;
import com.code.model.usr_tc;
import com.code.utils.Conts;
import com.code.utils.DiCloudUtil;
import com.code.utils.HttpRequest;
import com.code.utils.IdGen;
import com.code.utils.PayUtil;
import com.code.utils.Responser;
import com.code.utils.XmlUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONObject;

public class TcController extends Controller {

	public void index() {
		render("tc.html");
	}

	public void getTcList() throws JsonProcessingException {
		
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

	public void pay() throws DocumentException, Exception {
		Responser resp = new Responser();
		String openId = getPara("openid");
		String tcId = getPara("tcid");
		Record res = Db.find(" select * from usr_tc where id = ? ", tcId).get(0);
		String oid = PayUtil.getOrderId()+num();
		String preId = PayUtil.getPreyId(oid, res.getDouble("lsjg").toString(), openId);
		System.out.println("=================================>preId：" + preId);
		long timeStamp = new Date().getTime();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("appId", Conts.APP_ID);
		map.put("nonceStr", PayUtil.getOrderId());
		map.put("package", "prepay_id=" + preId);
		map.put("timeStamp", timeStamp + "");
		map.put("signType", "MD5");
		map.put("paySign", PayUtil.getSign(map));
		resp.put("return_code", "SUCCESS");
		resp.putArray("return_msg", map);
		Record re = Db.findFirst(" select * from sys_openid where openId = ? " , openId);
		//统一下单后向购买记录表插入数据
		Record usr_buy = new Record()
				.set("id", IdGen.uuid())
				.set("tcid", tcId)
				.set("gmsj", new Date())
				.set("oId", re.getStr("id"))
				.set("zt", "0")
				.set("order", oid);
		Db.save("usr_buy", usr_buy);
		renderText(resp.GetResponse());
	}
	
	public void callback() throws IOException, DocumentException{
		HttpServletRequest req =  getRequest();
		InputStream inputStream ;  
        StringBuffer sb = new StringBuffer();  
        inputStream = req.getInputStream();  
        String s = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
        while ((s = in.readLine()) != null){  
            sb.append(s);  
        }  
        in.close();  
        inputStream.close();  
        //解析xml成map  
        Map<String, String> map = new HashMap<String, String>();  
        map = XmlUtils.xmlBody2map(sb.toString(),"xml");
    	if("SUCCESS".equals(map.get("return_code"))){
    		//支付成功 修改状态
			int flag = Db.update(" update usr_buy u set u.zt= ? where u.order = ? " , 1, map.get("out_trade_no"));

			String orderId = map.get("out_trade_no");
			Record re = Db.findFirst(" select * from usr_pro where oId = (select u.oId from usr_buy u where u.order = ? ) " , orderId);
			String array [] = new String [1];
			array[0] = re.getStr("devSn");
			stop2start ss = new stop2start();
			ss.setAppLimit("0");
			ss.setWebLimit("0");
			ss.setDevSn(array);
			ss.setRxSpeed("");
			ss.setTxSpeed("");
			ss.setToken(DiCloudUtil.GetToken());
			String result = HttpRequest.sendPost(Conts.DiCouldUrl+Conts.open2Stop, JSONObject.fromObject(ss).toString());
			System.out.println(result);	
        }
        
        
        
        renderNull();
	}
	
	private int num (){
		java.util.Random random=new java.util.Random();// 定义随机类
		int result=random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
		return result+1;              // +1后，[0,10)集合变为[1,11)集合，满足要求
	}

}
