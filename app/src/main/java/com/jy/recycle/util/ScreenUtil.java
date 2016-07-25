package com.jy.recycle.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by songran on 16/6/23.
 */
public class ScreenUtil {
    public static int getScreenWdith(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
}
