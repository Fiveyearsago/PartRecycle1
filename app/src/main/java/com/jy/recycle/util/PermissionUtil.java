package com.jy.recycle.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by songran on 16/6/22.
 */
public class PermissionUtil {
    private static int REQUEST_CODE_READ_PHONE_STATE = 10;
    private static int REQUEST_CODE_ACCESS_WIFI_STATE=11;
    private static  int REQUEST_CODE_WRITE_SETTINGS=12;
    private static int REQUEST_CODE_ACCESS_FINE_LOCATION=13;
    public static  void getPermission(Context mContext){
        if (Build.VERSION.SDK_INT >= 23) {
            getReadStatePermission(mContext);
            getWifiPermission(mContext);
            getWriteSettingPermission(mContext);
            getAccessFineLocationPermission(mContext);
        }
    }

    public static void getReadStatePermission(Context mContext) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.WRITE_SETTINGS,Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_READ_PHONE_STATE);
                return;
            }
        }
    }
    public static void getWifiPermission(Context mContext) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_WIFI_STATE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_CODE_ACCESS_WIFI_STATE);
                return;
            }
        }
    }
    public static void getWriteSettingPermission(Context mContext) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_SETTINGS);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_SETTINGS}, REQUEST_CODE_WRITE_SETTINGS);
                return;
            }
        }
    }
    public static void getAccessFineLocationPermission(Context mContext) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_FINE_LOCATION);
                return;
            }
        }
    }
}
