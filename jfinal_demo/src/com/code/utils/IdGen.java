/**
 */
package com.code.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author ICH
 * @version 2017-01-15
 */
public class IdGen  {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
	
	/**
    * 得到随机字符串
    * @param length
    * @return
    */
   public static String getRandomString(){
      int length = 32;
      String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
      Random random = new Random();
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < length; ++i){
         int number = random.nextInt(62);//[0,62)  
         sb.append(str.charAt(number));
      }
      return sb.toString();
   }

}
