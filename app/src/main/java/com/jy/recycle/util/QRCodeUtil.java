package com.jy.recycle.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.jy.recycle.zxing.decoing.RGBLuminanceSource;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by songran on 16/6/29.
 */
public class QRCodeUtil {
    public interface QRCodeCallBack{
        void response(String recode);
    }
    public static void getQRString(ArrayList<String> images, final Context context, final QRCodeCallBack qrCodeCallBack){
        String photo_path="";
        if (images.size() > 0) {
            photo_path=images.get(0);
        }
        final String finalPhoto_path = photo_path;
        new Thread(new Runnable() {

            @Override
            public void run() {

                Result result = scanningImage(finalPhoto_path);
                if (result == null) {
                    Log.i("123", "   -----------");
                    Looper.prepare();
                    Toast.makeText(context, "图片格式有误", Toast.LENGTH_SHORT)
                            .show();
                    Looper.loop();
                } else {
                    Log.i("123result", result.toString());
                    // 数据返回
                    String recode = recode(result.toString());
                    qrCodeCallBack.response(recode);
                }
            }
        }).start();
    }
    public static byte[] rgb2YUV(Bitmap bitmap) {
        // 该方法来自QQ空间
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int len = width * height;
        byte[] yuv = new byte[len * 3 / 2];
        int y, u, v;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = pixels[i * width + j] & 0x00FFFFFF;

                int r = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 16) & 0xFF;

                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

                y = y < 16 ? 16 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                yuv[i * width + j] = (byte) y;
                // yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
                // yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
            }
        }
        return yuv;
    }
    public static Result scanningImage(String path) {

        if (TextUtils.isEmpty(path)) {

            return null;

        }
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
       Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小

        int sampleSize = (int) (options.outHeight / (float) 200);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);

        // --------------测试的解析方法---PlanarYUVLuminanceSource-这几行代码对project没作功----------

//        LuminanceSource source1 = new PlanarYUVLuminanceSource(
//                rgb2YUV(scanBitmap), scanBitmap.getWidth(),
//                scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(),
//                scanBitmap.getHeight(), false);
//        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
//                source1));
//        MultiFormatReader reader1 = new MultiFormatReader();
//        Result result1;
//        try {
//            result1 = reader1.decode(binaryBitmap);
//            String content = result1.getText();
//            Log.i("123content", content);
//        } catch (NotFoundException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }

        // ----------------------------

        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {

            return reader.decode(bitmap1, hints);

        } catch (NotFoundException e) {

            e.printStackTrace();

        } catch (ChecksumException e) {

            e.printStackTrace();

        } catch (FormatException e) {

            e.printStackTrace();

        }

        return null;


    }
    private static String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
                Log.i("1234      ISO8859-1", formart);
            } else {
                formart = str;
                Log.i("1234      stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formart;
    }
}
