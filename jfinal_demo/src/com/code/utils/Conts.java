package com.code.utils;


import java.util.Properties;


import net.sf.json.JSONObject;

public class Conts {
	
	/**
    * 商户号
    */
	public static final String  MCH_ID = "13000000000001";

	/**
	 * 应用ID
	 */
	public static final String APP_ID =  "wx47ba0968685f7f15";
	
	/**
	 * 应用密钥
	 */
	public static final String APPSECRET =  "df990f1345a5f432ab4331982e412bc6";
	
	/**
	 * 获取open_id（微信接口）
	 */
	public static final String getOpenIdByWxUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**
	 * 向指定用户发送消息（微信接口）
	 */
	public static final String sendMsgByWxUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
	
	/**
	 * token（微信接口）
	 */
	public static final String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
	
	/**
	 * 获取用户列表（微信接口）
	 */
	public static final String getUserListUrl = "https://api.weixin.qq.com/cgi-bin/user/get";
	
	/**
	 * 获取用户信息（微信接口）
	 */
	public static final String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/**
	 * 上传图文消息内的图片获取URL【订阅号与服务号认证后均可用】
	 */
	public static final String upMediaImg = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
	
	/**
	 * 上传图文消息素材【订阅号与服务号认证后均可用】
	 */
	public static final String upNews = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	
	/**
    * 预支付请求地址
    */
    public static final String  PrepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
		
    /**
     * 商品描述
     */
    public static String body = "套餐购买";
    
	
	
}
