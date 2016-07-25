package com.jy.recycle.util;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class ImageUtil {

	public static Bitmap makeTextBitMap1(int flag,int w, int h,String title, String addr, String date,
			Bitmap b, String userid,String carNo) {
		if(addr==null||"".equals(addr)){
			addr="δ��λ����ַ";
		}
		
		String[] stringFormat = StringFormat(addr, w, 18);

		Bitmap icon = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); // ����һ���յ�BItMap
		Canvas canvas = new Canvas(icon);// ��ʼ������ ���Ƶ�ͼ��icon��

		Paint photoPaint = new Paint(); // ��������
		photoPaint.setDither(true); // ��ȡ��������ͼ�����
		photoPaint.setFilterBitmap(true);// ����һЩ

		Rect src = new Rect(0, 0, b.getWidth(), b.getHeight());// ����һ��ָ�����¾��ε�����
		Rect dst = new Rect(0, 0, w, h);// ����һ��ָ�����¾��ε�����
		canvas.drawBitmap(b, src, dst, photoPaint);// ��photo ���Ż�������
													// dstʹ�õ������photoPaint

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// ���û���
		textPaint.setTextSize(13.0f);// �����С
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// ����Ĭ�ϵĿ��
		textPaint.setColor(Color.BLUE);// ���õ���ɫ
		
		int j = 20;
//		String title ="";
//		if(2==flag){
//        	title = "�����ֳ�";
//        }else if(3==flag){
//        	title = "��ʼ�ϳ�";
//        }else if(4==flag){
//        	title = "����Ŀ�ĵ�";
//        }else if(5==flag){
//        	title = "������";
//        }
		canvas.drawText(title, 0, j, textPaint);
		j = j + 20;
		for (int i = 0; i < stringFormat.length; i++) {
			canvas.drawText(stringFormat[i], 0, j, textPaint);
			j = j + 20;
		}
		canvas.drawText(date, 0, j + 20, textPaint);
		canvas.drawText(carNo, 0, j, textPaint);

		return icon;

	}

	public static Bitmap makeTextBitMap(int flag,int w, int h,String title, String addr, String date,
			Bitmap b, String userid,String carNo) {
		if(addr==null||"".equals(addr)){
			addr="δ��λ����ַ";
		}
		
		String[] stringFormat = StringFormat(addr, w, 22);

		Bitmap icon = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); // ����һ���յ�BItMap
		Canvas canvas = new Canvas(icon);// ��ʼ������ ���Ƶ�ͼ��icon��

		Paint photoPaint = new Paint(); // ��������
		photoPaint.setDither(true); // ��ȡ��������ͼ�����
		photoPaint.setFilterBitmap(true);// ����һЩ

		Rect src = new Rect(0, 0, b.getWidth(), b.getHeight());// ����һ��ָ�����¾��ε�����
		Rect dst = new Rect(0, 0, w, h);// ����һ��ָ�����¾��ε�����
		canvas.drawBitmap(b, src, dst, photoPaint);// ��photo ���Ż�������
													// dstʹ�õ������photoPaint

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// ���û���
		textPaint.setTextSize(18.0f);// �����С
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// ����Ĭ�ϵĿ��
		textPaint.setColor(Color.BLUE);// ���õ���ɫ
		
		int j = 20;
//		String title ="";
//		if(2==flag){
//        	title = "�����ֳ�";
//        }else if(3==flag){
//        	title = "��ʼ�ϳ�";
//        }else if(4==flag){
//        	title = "����Ŀ�ĵ�";
//        }else if(5==flag){
//        	title = "������";
//        }
		canvas.drawText(title, 0, j, textPaint);
		j = j + 20;
		for (int i = 0; i < stringFormat.length; i++) {
			canvas.drawText(stringFormat[i], 0, j, textPaint);
			j = j + 20;
		}
		canvas.drawText(date, 0, j + 20, textPaint);
		canvas.drawText(carNo, 0, j, textPaint);

		return icon;

	}

	public static String[] StringFormat(String text, int maxWidth, int fontSize) {

		String[] result = null;

		Vector<String> tempR = new Vector<String>();

		int lines = 0;

		int len = text.length();

		int index0 = 0;

		int index1 = 0;

		boolean wrap;

		while (true) {

			int widthes = 0;

			wrap = false;

			for (index0 = index1; index1 < len; index1++) {

				if (text.charAt(index1) == '\n') {

					index1++;

					wrap = true;

					break;

				}

				widthes = fontSize + widthes;

				if (widthes > maxWidth) {

					break;

				}

			}

			lines++;

			if (wrap) {

				tempR.addElement(text.substring(index0, index1 - 1));

			} else {

				tempR.addElement(text.substring(index0, index1));

			}

			if (index1 >= len) {

				break;

			}

		}

		result = new String[lines];

		tempR.copyInto(result);

		return result;

	}

}
