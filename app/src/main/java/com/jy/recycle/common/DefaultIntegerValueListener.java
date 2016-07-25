package com.jy.recycle.common;

import com.jy.recycle.util.MathUtil;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

/**
 * 默认值恢复器
 */
public class DefaultIntegerValueListener implements OnFocusChangeListener {
	private String defaultValue = "";
	public DefaultIntegerValueListener(String defaultValue){
		this.defaultValue = defaultValue;
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		EditText editText = (EditText)v ; 
		String value = editText.getText().toString();
		if(hasFocus){
			if(MathUtil.getInt(value , 0) == 0){ //获得焦点时，如果为0 ，自动清除
				editText.setText("");
			}
		}else{
			if(value!=null && "".equals(value.trim())){ //失去焦点时，如果为空，则默认为默认值
				editText.setText(defaultValue);
			}
		}
	}
}
