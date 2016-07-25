package com.jy.recycle.util;

import android.text.Editable;
import android.text.TextWatcher;
/**
 * 什么也不做的TextWatcher
 * @author zhaowenbin
 *
 */
public class SimpleTextWatcher implements TextWatcher {

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

}
