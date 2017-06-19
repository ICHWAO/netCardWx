package com.code.controller;


import java.io.UnsupportedEncodingException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.code.model.stop2start;
import com.code.utils.Conts;
import com.code.utils.DiCloudUtil;
import com.code.utils.HttpRequest;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONObject;

public class Task extends Controller implements Job{

	//检测设备流量是否超标
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		/**
		 * 1:获取表里所有devSn不为空的设备
		 * 2:调用接口获取tonken
		 * 3:获取设备的流量使用情况
		 * 4:查询本月续费几个包与(3)返回的结果对比
		 * 5:最终处理停用设备
		 */
		//获取设备Token
		String token = DiCloudUtil.GetToken();
		List<Record> list =  Db.find(" select * from usr_pro where devSn is not NULL ");
		int size = list.size();
		if(size != 0){
			for (int i = 0; i < size; i++) {
				//获取卡流量使用情况
				Record re = list.get(i);
				String devSn =  re.getStr("devSn");
				String useFlow = DiCloudUtil.GetDevUesInfo(token, devSn);
				if(useFlow==null || "".equals(useFlow)){
					useFlow = "0";
				}
				int useFlowRes= Integer.valueOf(useFlow);
				long num = Db.queryLong(" select count(*) from usr_buy where oId = ? ",re.getStr("oId"));
				//计算设备一共有多少流量可以使用 
				int useFlowZ = (int)num * 1024;
				String array [] = new String [1];
				array[0] = devSn;
				if(useFlowRes > useFlowZ){ 
					//流量多于设备流量
					//关停设备
					stop2start ss = new stop2start();
					ss.setAppLimit("1");
					ss.setWebLimit("1");
					ss.setDevSn(array);
					ss.setRxSpeed("");
					ss.setTxSpeed("");
					ss.setToken(token);
					String result = HttpRequest.sendPost(Conts.DiCouldUrl+Conts.open2Stop, JSONObject.fromObject(ss).toString());
					System.out.println(result);	
				} 
			}
		} else {
			System.out.println("数据库没数据!");
		}
			
	}
}
