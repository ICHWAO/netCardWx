package com.code.utils;

public class Conts {
	
	/**
    * 商户号
    */
	public static final String  MCH_ID = "1481075612";
	
	/**
    * 商户号
    */
	public static final String  MCH_KEY = "lc990f1345a5f432ab4331982e412bxc";

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
	 * 微信回调url	
	 */
    public static final String CALLBACK = "http://www.mlbwifi.cn/tc/callback";
    
    /**
     * 商品描述
     */
    public static String body = "套餐购买";
    
	
    
    
    /***********************************我是一个可爱的分割线*********************************************/
   
    /**
	 * diCould_Url
	 */
    public static final String DiCouldUrl="http://yueshiying.dimiccs.com:3080/";
    
    /**
	 * 账号
	 */
    public static final String DO_MIAN = "yueshiying";
	
	/**
	 * 账号
	 */
    public static final String USER = "ysyapi2";
	
	/**
	 * 账号
	 */
    public static final String Passward = "123456";
    
    /**
     * 调用token接口
     */
    public static final String Get_Dc_Token="api/v1/auth";
    
    /**
     * 调用dev所有的设备信息
     */
    public static final String Get_Dec_basic="api/v1/devices/basic";
    
    /**
     * 获取设备使用情况
     */
    public static final String Get_Dev_UseInfo = "api/v1/devices/account";
    
    /**
     * 设备流量关停
     */
    public static final String open2Stop = "api/v1/devices/net";
}
