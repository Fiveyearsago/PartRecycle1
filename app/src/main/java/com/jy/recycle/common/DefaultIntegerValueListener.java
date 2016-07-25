package com.jy.recycle.common;

import com.jy.recycle.util.MathUtil;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

/**
 * Ĭ��ֵ�ָ���
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
			if(MathUtil.getInt(value , 0) == 0){ //��ý���ʱ�����Ϊ0 ���Զ����
				editText.setText("");
			}
		}else{
			if(value!=null && "".equals(value.trim())){ //ʧȥ����ʱ�����Ϊ�գ���Ĭ��ΪĬ��ֵ
				editText.setText(defaultValue);
			}
		}
	}
}
