package com.code.utils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PayUtil {
	
    /**金额为分的格式 */    
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";    

	/**
	 * 得到本地机器的IP
	 * 
	 */
	public static String getHostIp() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	/**
	    * 得到微信预付单的返回ID
	    * @param orderId  商户自己的订单号
	    * @param totalFee  总金额  （分）
	    * @return
	 * @throws Exception 
	    */
	   public static String getPreyId(String orderId,String totalFee,String openid) throws Exception{
	      Map<String, String> reqMap = new HashMap<String, String>();
	      reqMap.put("appid", Conts.APP_ID);
	      reqMap.put("mch_id", Conts.MCH_ID);
	      reqMap.put("nonce_str", IdGen.getRandomString());
	      reqMap.put("body", Conts.body);
	      //reqMap.put("detail", WeiChartConfig.subject); //非必填
	      //reqMap.put("attach", "附加数据"); //非必填
	      reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
	      reqMap.put("total_fee", changeY2F(Double.parseDouble(totalFee))); //订单总金额，单位为分
	      reqMap.put("spbill_create_ip", getHostIp()); //用户端实际ip
	      // reqMap.put("time_start", "172.16.40.18"); //交易起始时间 非必填
	      // reqMap.put("time_expire", "172.16.40.18"); //交易结束时间  非必填
	      // reqMap.put("goods_tag", "172.16.40.18"); //商品标记 非必填
	      reqMap.put("notify_url", Conts.CALLBACK); //通知地址
	      reqMap.put("trade_type", "JSAPI"); //交易类型
	      //reqMap.put("limit_pay", "no_credit"); //指定支付方式,no_credit 指定不能使用信用卡支  非必填
	      reqMap.put("openid", openid);
	      reqMap.put("sign", getSign(reqMap));
	      
	      String reqStr = XmlUtils.map2xmlBody(reqMap, "xml");
	      System.out.println("==================>send参数:"+reqStr);
	      String retStr = HttpRequest.postHtpps(Conts.PrepayUrl, reqStr);
	      System.out.println("==================>get参数:"+retStr);
	      return (String) XmlUtils.xmlBody2map(retStr,"xml").get("prepay_id");
	   }
	   
	   /**
	    * 得到加密值
	    * @param map
	    * @return
	    */
	   public static String getSign(Map<String, String> map){
	      String[] keys = map.keySet().toArray(new String[0]);
	      Arrays.sort(keys);
	      StringBuffer reqStr = new StringBuffer();
	      for(String key : keys){
	         String v = map.get(key);
	         if(v != null && !v.equals("")){
	            reqStr.append(key).append("=").append(v).append("&");
	         }
	      }
	      reqStr.append("key").append("=").append(Conts.MCH_KEY);
	      System.out.println("签名MD5加密前结果====>"+reqStr.toString());
	      //MD5加密
	      return MD5.MD5Encode(reqStr.toString()).toUpperCase();
	   }
	   
	   /**
	    * 获取订单号
	    */
	   private static long counter = 1000; 
	   public static String getOrderId() throws Exception {
		   DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String result = dateFormat.format(new Date()) + counter++;
	        return result;
	   }
	   
	   /**  
	     * 将分为单位的转换为元 （除100）  
	     *   
	     * @param amount  
	     * @return  
	     * @throws Exception   
	     */    
	    public static String changeF2Y(String amount) throws Exception{    
	        if(!amount.matches(CURRENCY_FEN_REGEX)) {    
	            throw new Exception("金额格式有误");    
	        }    
	        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();    
	    }    
	        
	    /**   
	     * 将元为单位的转换为分 （乘100）  
	     *   
	     * @param amount  
	     * @return  
	     */    
	    public static String changeY2F(double amount){    
	    	String number = BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
	    	String intNumber = number.substring(0,number.indexOf("."));
	        return intNumber;    
	    }    
	        
	   
	   
}
