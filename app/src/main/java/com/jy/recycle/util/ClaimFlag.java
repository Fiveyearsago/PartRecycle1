package com.jy.recycle.util;


public class ClaimFlag {
	public static final String APKNAME="ds.apk"; //Ӧ�ó�������
	
	public static int PAGE_SIZE = 9;   	//ÿҳ��ʾ����	����
	public static  int PART_PAGE_SIZE = 20;   	//ÿҳ��ʾ����	 �����ʾ���� ���ڴ�����֮�����ʾ��һҳ
	public static  int REPAIR_RATE = 10;   //Ĭ�Ϲ�ʱ����
	public static  int GPS_MOVE_DISTANCE=20;		//GPS�����ƶ����� ����λ:�ף�
	public static  int GPS_FREQUENCY= 60*1000;	//GPS����Ƶ�� ����λ:���룩 
	public static  int ACC_DATA= 2*30*1000;	//���ʱ����հ��� ����λ:���룩
	public static  int UPDATE_DATA= 24*3600*1000;	//���ʱ������� ����λ:���룩
	public static  int REMOVE_DATA= 1*3600*1000;	//���ʱ��������� ����λ:���룩
	public static  int PIC_WIDTH= 640;//	480	//����ϵͳ���պ�Ŀ�ȣ���λ:PX��
	public static  int PIC_HEIGHT=480;//	800	//����ϵͳ���պ�Ŀ�ȣ���λ:PX��
	public static  int LIST_HEIGHT= 28;		//������ҳ��ÿ��listView�ĸ߶ȣ���λ:PX��
	public static  int CON_TIMEOUT= 20000;			//�������ӳ�ʱ����λ:���룩
	public static  int SO_TIMEOUT= 20000;			//��ȡ��ʱ��ʱ����λ:���룩
	public static  int REMOVE_DAY=30;				//���������֮ǰ�����ݣ���λ:�죩
	public static  int REMIND_TIME=10*60*1000;				//ԤԼɨ��ʱ�䣨��λ:���룩
	

	public static final String REPAIR_TYPE_BANJ = "B00";   //�ӽ�
	public static final String REPAIR_TYPE_YOUQ = "Q00";   //����
	public static final String REPAIR_TYPE_JIX = "X00";   //����
	public static final String REPAIR_TYPE_DIANG = "D00";   //�繤
	public static final String REPAIR_TYPE_CHAIZ = "C00";   //��װ
	public static final String REPAIR_TYPE_QIT = "QT";   //����
	public static final String REPAIR_TYPE_FUL = "Z";   //����
	
	public static int GPS_FREQUENCY_EVERY_SECOND = 2000; 
	
	public final static String YES = "1" ; //��
	public final static String NO = "0" ;//��
	
	public final static String REQUEST_REBACK="002";
	
}
