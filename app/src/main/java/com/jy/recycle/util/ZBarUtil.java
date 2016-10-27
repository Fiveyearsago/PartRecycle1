package com.jy.recycle.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.Toast;


import com.jy.recycle.R;

import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.util.ArrayList;

/**
 * Created by songran on 16/8/24.
 */
public class ZBarUtil {
    public interface QRCodeCallBack{
        void response(String recode);
    }
    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
    public static String getZBarString(ArrayList<String> images, final Context context, final QRCodeCallBack qrCodeCallBack) {
        String photo_path = "";
        if (images.size() > 0) {
            photo_path=images.get(0);
        }
//        final String finalPhoto_path = photo_path;
//        if (TextUtils.isEmpty(finalPhoto_path)) {
//            return null;
//        }
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 先获取原大小
//        Bitmap scanBitmap = BitmapFactory.decodeFile(finalPhoto_path, options);
//        options.inJustDecodeBounds = false; // 获取新的大小
//
//        int sampleSize = (int) (options.outHeight / (float) 200);
//
//        if (sampleSize <= 0)
//            sampleSize = 1;
//        options.inSampleSize = sampleSize;
//        scanBitmap = BitmapFactory.decodeFile(finalPhoto_path, options);
////        Bitmap scanBitmap = BitmapFactory.decodeFile(finalPhoto_path);
//        Bitmap barcodeBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.qrcode);
        Bitmap scanBitmap=getSmallBitmap(photo_path);
        int width = scanBitmap.getWidth();
        int height =scanBitmap.getHeight();
        int[] pixels = new int[width*height];
        scanBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        ImageScanner scanner=new ImageScanner();

        Image barcode = new Image(width, height, "RGB4");
        barcode.setData(pixels);

        int result = scanner.scanImage(barcode.convert("Y800"));

        if (result != 0) {

            SymbolSet syms = scanner.getResults();

//            Result rawResult = new Result();
//
            for (Symbol sym : syms) {

                String barcode_data = sym.getData();

                if (!TextUtils.isEmpty(barcode_data)) {
                    qrCodeCallBack.response(barcode_data);
//
//                    rawResult.setContents(barcode_data);
//                    rawResult.setBarcodeFormat(BarcodeFormat.getFormatById(sym.getType()));

//                    break;

                }

            }

        }else {
            Toast.makeText(context, "图片格式有误", Toast.LENGTH_SHORT)
                    .show();
        }
        return null;
    }

}
