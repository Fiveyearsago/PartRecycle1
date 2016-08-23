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

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.io.ByteArrayOutputStream;
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
    public static void getZBarString(ArrayList<String> images, final Context context, final QRCodeCallBack qrCodeCallBack) {
        String photo_path = "";
        if (images.size() > 0) {
            photo_path=images.get(0);
        }
        final String finalPhoto_path = photo_path;
        new Thread(new Runnable() {

            @Override
            public void run() {

                String result = scanningZBarImage(finalPhoto_path);
                if (result == null) {
                    Log.i("123", "   -----------");
                    Looper.prepare();
                    Toast.makeText(context, "图片格式有误", Toast.LENGTH_SHORT)
                            .show();
                    Looper.loop();
                } else {
                    Log.i("123result", result.toString());
                    // 数据返回
                    String recode = recode(result);
                    qrCodeCallBack.response(recode);
                }
            }
        }).start();
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
    private static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    public static String scanningZBarImage(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小

        int sampleSize = (int) (options.outHeight / (float) 200);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path,options);
        int width = scanBitmap.getWidth();
        int height = scanBitmap.getHeight();
        int[] pixels = new int[width*height];
        byte[] data=Bitmap2Bytes(scanBitmap);
        scanBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        Image barcode = new Image(width, height, "RGB4");
        barcode.setData(data);
        ImageScanner scanner=new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
        int[] symbols = new int[]{Symbol.QRCODE};
        if (symbols != null) {
            scanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
            for (int symbol : symbols) {
                scanner.setConfig(symbol, Config.ENABLE, 1);
            }
        }
        int result = scanner.scanImage(barcode.convert("Y800"));
        SymbolSet syms = scanner.getResults();
        for (Symbol sym : syms) {
            String symData = sym.getData();
            if (!TextUtils.isEmpty(symData)) {

            }
        }

        return null;
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
