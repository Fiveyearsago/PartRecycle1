package com.jy.recycle.util;


import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class TimestampTool {

	// ��ǰʱ��
	public static Timestamp crunttime() {

		return new Timestamp(System.currentTimeMillis());
	}

	//��ȡ��ǰʱ����ַ���  2006-07-07
	public static String getCurrentDate() {
		Timestamp d=crunttime();
		return d.toString().substring(0,10);
	}
	//	��ȡ��ǰʱ����ַ���  2006-07-07 22:10:10
	public static String getCurrentDateTime() {
		Timestamp d=crunttime();
		return d.toString().substring(0,19);
	}
//	��ȡ��ǰʱ����ַ���(û����)   2006-07-07 22:10
	public static String getCurrentDateHm() {
		Timestamp d=crunttime();
		return d.toString().substring(0,16);
	}
	//	��ȡ����ʱ����ַ���,ֻ������  2006-07-07
	public static String getStrDate(Timestamp t) {
		return t.toString().substring(0,10);
	}
	//	��ȡ����ʱ����ַ���  2006-07-07 22:10:10
	public static String getStrDateTime(Timestamp t) {
		return t.toString().substring(0,19);
	}
//	��ȡ����ʱ����ַ���(û����)  2006-07-07 22:10
	public static String getStrDateHm(Timestamp t) {
		return t.toString().substring(0,16);
	}
	// �������� ��ʽ:2006-07-05
	public static Timestamp date(String str) {
		Timestamp tp = null;
		if (str.length() <= 10) {
			String[] string = str.trim().split("-");
			int one = Integer.parseInt(string[0]) - 1900;
			int two = Integer.parseInt(string[1]) - 1;
			int three = Integer.parseInt(string[2]);
			tp = new Timestamp(one, two, three, 0, 0, 0, 0);
		}
		return tp;
	}
	public static Timestamp addDate(int day) {		
		return new Timestamp(crunttime().getTime()+day*24*60*60*1000);   
	}
	// ����ʱ�������  ��ʽ:2006-07-05 22:10:10
	public static Timestamp datetime(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			int time3 = Integer.parseInt(time[2]);
			tp = new Timestamp(date1, date2, date3, time1, time2, time3, 0);
		}
		return tp;
	}


	// �������ں�ʱ��(û����)  ��ʽ:2006-07-05 22:10
	public static Timestamp datetimeHm(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
		}
		return tp;
	}
	//	��ȡ��ǰ����֮ǰ�������ַ��� �� 2007-04-15  ǰ5�� ���� 2006-11-15 
	public static String getPreviousMonth(int month){
		Calendar   cal1   =   Calendar.getInstance();   
        cal1.setTime(new Date());   
        cal1.add(Calendar.MONTH,-month);               
        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");   
        return formatter.format(cal1.getTime());
    }
	//	��ȡ��ǰ����֮��������ַ��� �� 2007-04-15  ��һ�� ���� 2007-04-16 
	public static String getNextDay(int day){
		Calendar   cal1   =   Calendar.getInstance();   
        cal1.setTime(new Date());   
        cal1.add(Calendar.DAY_OF_MONTH,day);               
        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");   
        return formatter.format(cal1.getTime());
    }
//	��ȡָ������֮��������ַ��� �� 2007-04-15  ��һ�� ���� 2007-04-16 
	public static String getNextDay(String strDate,int day){
		Calendar   cal1   =   Calendar.getInstance();   
		String[] string = strDate.trim().split("-");
		int one = Integer.parseInt(string[0]) - 1900;
		int two = Integer.parseInt(string[1]) - 1;
		int three = Integer.parseInt(string[2]);
        cal1.setTime(new Date(one,two,three));   
        cal1.add(Calendar.DAY_OF_MONTH,day);               
        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");   
        return formatter.format(cal1.getTime());
    }
//	��ȡָ������֮ǰ�����ַ��� �� 2007-04-15  ��һ�� ���� 2007-05-15 
	public static String getNextMonth(String strDate,int month){
		Calendar   cal1   =   Calendar.getInstance();   
		String[] string = strDate.trim().split("-");
		int one = Integer.parseInt(string[0]) - 1900;
		int two = Integer.parseInt(string[1]) - 1;
		int three = Integer.parseInt(string[2]);
        cal1.setTime(new Date(one,two,three));    
        cal1.add(Calendar.MONTH,month);               
        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");   
        return formatter.format(cal1.getTime());
    }
	//����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd HH:mm:ss
	public static Date strToDateLong(String strDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = (Date) formatter.parse(strDate, pos);
	  if(strtodate==null){
		  formatter = new SimpleDateFormat("yyyy-MM-dd");
		  strtodate = (Date) formatter.parse(strDate, pos);
	  }
	  return strtodate;
	 }
	//����������ַ������������ַ��� 2006-07-07 22:10   2006-07-07
	public static String getStrDate(String str){
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			return string[0];
		}else{
			return getCurrentDate();	
		}
    }
//	��ȡ��ǰʱ����ַ���  2006-07-07 22:10:10 2006-07-07_221010   
	public static String getStrDateTime() {
		Timestamp d=crunttime();
//		Log.i("time", d.toString().substring(0,25).replace(":", "").replace(" ", "_"));
		return d.toString().substring(0,19).replace(":", "").replace(" ", "_");
	}
//	��ȡ��ǰʱ����ַ���  2006-07-07 22:10:10��000000 2006-07-07_221010000000  
	public static String getStrDateTime1() {
		Timestamp d=crunttime();
//		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ssssss");
//		 formatter.format(cal1.getTime())
		return d.toString().substring(0,25).replace(":", "").replace(" ", "_");
	}
	//���������ַ��������ؽ��죬���������
	public static String getDayOrDate(String str){
		if(str!=null && !str.equals("")){
			if(getNextDay(str,0).equals(getCurrentDate())){
				str="����";
			}else if(getNextDay(str,1).equals(getCurrentDate())){
				str="����";
			}
		}
		return str;
	}
	
//	���ص�ǰ�����������ڣ�2��Ӧ����һ
	public static int getMonOfWeek(){
		Calendar   cal1   =   Calendar.getInstance();   
        cal1.setTime(new Date());        
        return cal1.get(Calendar.DAY_OF_WEEK);
    }
	
	public static void main(String[] args){
//		System.out.println(TimestampTool.getStrDateHm(TimestampTool.addDate(1)));
		System.out.println(TimestampTool.strToDateLong("2008-9-10 13:52:"));
		System.out.println(TimestampTool.getStrDate("2011-3-26 10:23:35"));
		//System.out.println(new java.sql.Date((new Date()).getTime())); 
	}
}
