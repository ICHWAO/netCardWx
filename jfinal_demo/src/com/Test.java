package com;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.code.utils.PayUtil;

public class Test {
	private static SecureRandom random = new SecureRandom();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < 30; i++) {
			System.out.println(PayUtil.getOrderId());
		}
	}
}
