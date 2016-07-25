package com.jy.recycle.util.jm;

import java.io.IOException;

public class EncryptUtil {
	private static String KEY = "YZRkJbWU3+DOLAEcXv5ADYyAVK2nnXVS";
	private static String ENCODING = "GBK";
	/**
	 * 加密数据
	 * @param data
	 * @return
	 */
	public static String encrypt(String data){
		try{
			byte[] keyData = DESede.convertFromBase64(KEY);
			byte[] encData = DESede.encrypt(keyData, data.getBytes(ENCODING));
			String encDataStr = DESede.convertToBase64(encData);
			return encDataStr;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null ; 
	}
	/**
	 * 解密数据
	 * @param data
	 * @return
	 */
	public static String decrypt(String data) throws IOException{
		try{
			byte[] keyData = DESede.convertFromBase64(KEY);
			byte[] decData = DESede.decrypt(keyData,  DESede.convertFromBase64(data));
			String decDataStr = new String(decData , ENCODING);
			return decDataStr;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null ; 
	}
	
    public static void main (String args[]) throws IOException{
    	String locPrice = "402880ac1167b1fa01116ad3b8dd043b";
    	String encData = encrypt(locPrice) ; 
    	System.out.println(encData + "," + encData.length());
    	String decData = decrypt(encData);
    	System.out.println(decData);
    }

}
