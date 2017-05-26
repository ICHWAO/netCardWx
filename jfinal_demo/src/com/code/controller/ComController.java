package com.code.controller;

import com.code.utils.Conts;
import com.code.utils.HttpRequest;
import com.code.utils.IdGen;
import com.code.utils.Responser;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONObject;

public class ComController extends Controller{
	
	public void getOpenId() throws Exception {
		String code = getPara("code");
		Responser responser = new Responser();
		/*String url = Conts.getOpenIdByWxUrl;
		String param = "appid=wxb8bda7d9ab4c5fb3&secret=3380d24c323817953b6d621f6fc49f62&code=" + code
				+ "&grant_type=authorization_code";
		String ret = HttpRequest.sendGet(url, param);
		JSONObject oj =  JSONObject.fromObject(ret);
		String openid = oj.getString("openid");*/
		String openid = "123123";
		//对OpenId进行处理
		long flag =  Db.queryLong(" select count(1) from sys_openid where openId = ? ",openid);
		 //新的openID
		if(flag==0){
			//openID表处理
			Record sysoPenId = new Record()
					.set("id", IdGen.uuid())
					.set("openId", openid);
			Db.save("sys_openid", sysoPenId);
			//设备表处理
			String id = sysoPenId.get("id");
			Record usrPro = new Record()
					.set("id", IdGen.uuid())
					.set("oId", id)
					.set("ssId", "");
			Db.save("usr_pro", usrPro);
			
		}
		
		responser.put("return_code", "SUCCESS");
		//responser.put("return_msg", ret);
		responser.put("return_msg", "{\"access_token\":\"CNdeKFbGip_Iocjmxl7Ly-1uX4RGjOTi5O_pGmBY_LV2TAXbbJzoYb7Em7EfZxySdj8H7Wwxb8J8-PIWS525ic8i4rrDVOaclDVagAIY0G4\",\"expires_in\":7200,\"refresh_token\":\"-ncb2YFS4UOEXLIlH2pXearkOp78ypsMaIJXYj_2ybitkNbntdCgfeTGmgBiDLOlElBzQSxzMfSWZlnel1x3q96ebYf_rMIelJ7UBPohw_w\",\"openid\":\"123123\",\"scope\":\"snsapi_base\"}");
		renderText(responser.GetResponse());
		//System.out.println("---------------------------------------获取open_id" + ret);
	}
}
