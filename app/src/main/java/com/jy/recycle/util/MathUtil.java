package com.jy.recycle.util;

import java.math.BigDecimal;

public class MathUtil {
	/**
	 * ��ȡvalue��˫���ȸ����������ʧ�ܣ�����defaultValue
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Double getDouble(String value, double defaultValue){
		try{
			return Double.valueOf(value);
		}catch(Exception ex){
			
		}
		return defaultValue ; 
	}
	/**
	 * ��ȡvalue������ֵ�����ʧ�ܣ�����defaultValue
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Integer getInt(String value ,int defaultValue){
		try{
			return Integer.valueOf(value);
		}catch(Exception ex){
			
		}
		return defaultValue ; 
	}
	
	public static double setScale(double d){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
		String r;
		int intPart1 = new Double(d*1000).intValue();
		//��ֹ0.005������
		if (intPart1%5 == 0) {
			r = df.format(d + 0.001);
		}else{
			r = df.format(d);
		}
			return Double.parseDouble(r);
	}
	public static double setScale2(double d){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
		java.text.DecimalFormat   df2   =new   java.text.DecimalFormat("#.000");//��ֹfr1�Ľ����0.005000000000000000000768 �����
		String r;
		int intPart1 = new Double(d*1000).intValue();
		//��ֹ0.005������
		if (intPart1%5 == 0) {
			r = df.format(d + 0.001);
		}else{
			r = df.format(d);
		}
//		int intPart1 = new Double(d).intValue();
//		double fr1 = d - intPart1;
//		//��ֹ0.005������
//		if (Double.parseDouble(df2.format(fr1)) == 0.005) {
//			r = df.format(d + 0.001);
//		}else{
//			r = df.format(d);
//		}
//		if(Double.parseDouble(r)<0){
//			return Double.parseDouble("0"+r);
//		}else{
			return Double.parseDouble(r);
//		}
	}
}
