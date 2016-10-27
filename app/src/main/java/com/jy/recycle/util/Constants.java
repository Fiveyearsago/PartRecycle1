package com.jy.recycle.util;

import java.io.File;

import com.jy.recycle.R;

import android.os.Environment;

public class Constants {
    public final static boolean DEBUG = true;

    public final static String DIR_SDCARD = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public final static String DIR_LOG = DIR_SDCARD + File.separator
            + "/jyds/jylog/";
    public final static String RES_LOG = DIR_SDCARD + File.separator
            + "/jyds/jylog/";

    public final static String ENCODING = "GBK";
    //        public final static String URL_BASE = "http://192.168.110.183:8081/mobileService-lioneye/";
    public final static String URL_BASE = "http://mobile.suny123.com/mobileService-suny/";
//    public final static String URL_UPLOAD = "http://192.168.120.122:8080/suny/";// ��������
    public final static String URL_UPLOAD = "http://www.suny123.com/suny/";// ��������192.168.8.109
//    public final static String URL_UPLOAD = "http://192.168.8.109:8082/suny/";// ��������

    public final static String MOBILE_STAT_SERVER = "http://claim.acgw.com.cn:8090/calcInsMobile";

    public final static String LOGIN_SESSION_ID = "LOGIN_SESSION_ID";

    public static String getVersionInfoUrl() {
        return URL_BASE + "update?queryFlag=1";
    }

    public static String getVersionUpdateUrl() {
        return URL_BASE + "update?queryFlag=2";
    }

    /**
     * ����ɹ�
     */
    public static final String QUEST_SUCCESS = "0100";
    public static final String LOGIN_SUCCESS = "0101";
    public static final String REQUEST_SENDTYPE_CHECK = "0100";
    public static final String REQUEST_SUBMIT = "0400";
    /**
     * ���û��������
     */
    public static final String PASSWORD_ERROR = "0199";
    public static final String SUBMIT_SUCCESS = "0310";
    public final static String GPS_KEY = "0lWNdOKW4M1M7Vl8UkxsIadn";// ����
    public final static String GPS_MCODE = "95:B5:4E:D0:18:27:F4:B3:37:08:6D:F3:42:B2:98:15:8E:DD:13:49";// ��Ƚ
    public final static String GPS_BASE = "http://api.map.baidu.com/geocoder/v2/?";
    // ��̨����code����˵����
    // 0101 У��ͨ�����н�� ���Ե�¼
    // 0105 �û������������
    // 0106 �û������е�¼Ȩ��
    // 0199 �����XML��ʽ�쳣
}
