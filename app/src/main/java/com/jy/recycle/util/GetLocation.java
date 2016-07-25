package com.jy.recycle.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class GetLocation {

	private static String address="";
	private static String city="";
	private static String province="";
	public static LocationClient mLocationClient = null;
	public  interface LocationCallBack{
		void locationResponse(String address,String province,String city);
	}
	public static void getLoc(Context context,final LocationCallBack locationCallBack) {

			mLocationClient = new LocationClient(context); // ����LocationClient��
			mLocationClient.registerLocationListener(new BDLocationListener() {
				@Override
				public void onReceiveLocation(BDLocation bdLocation) {
					if(bdLocation.getLocType() == BDLocation.TypeGpsLocation||bdLocation.getLocType() == BDLocation.TypeNetWorkLocation||bdLocation.getLocType() == BDLocation.TypeOffLineLocation)
					{
						address= bdLocation.getAddrStr();
						province=bdLocation.getProvince();
						city=bdLocation.getCity();
						mLocationClient.stop();
						locationCallBack.locationResponse(address,province,city);
					}else{
						mLocationClient.stop();
						locationCallBack.locationResponse("δ��λ��λ��","","");
					}

				}
			}); // ע���������
			initLocation();
			mLocationClient.start();

	}
	// ��ʼ����λ����
	private static void initLocation() {
		LocationClientOption option = new LocationClientOption();
		// option.setLocationMode(LocationMode.Battery_Saving);
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 1000;
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setAddrType("all");
		option.setScanSpan(span);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(false);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}

}
