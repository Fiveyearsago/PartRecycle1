package com.jy.recycle.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.Spinner;

public class CitySpinner extends Spinner {
	private Paint paint;

	public CitySpinner(Context context) {
		super(context);
	}
	
	public CitySpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CitySpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		inti();
	}

	private void inti() {
		// TODO Auto-generated method stub
		paint=new Paint();
		paint.setColor(Color.parseColor("#000000"));
		paint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int height=getMeasuredHeight();
		int width=getMeasuredWidth();
		Path path=new Path();  
		path.moveTo(width-15, height/2-5);
		path.lineTo(width-5, height/2-5);
		path.lineTo(width-10, height/2+5);
		path.close();
		canvas.drawPath(path, paint);
	}

}
