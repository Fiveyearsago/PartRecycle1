package com.jy.recycle.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by songran on 16/6/24.
 * Dialog����¼��ص�
 */
public class DialogUtil {
    public interface DialogCallBack{
         void response(int flag);
    }
    public static void showDialog(Context context, String message, final int flag, final DialogCallBack callBack){
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle("��ʾ")
                .setMessage(message)
                .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.response(flag);
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("ȡ��", null)
                .create();
        dialog.show();
    }
    public static void showPosDialog(Context context, String message, final int flag, final DialogCallBack callBack){
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle("��ʾ")
                .setMessage(message)
                .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.response(flag);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
}
