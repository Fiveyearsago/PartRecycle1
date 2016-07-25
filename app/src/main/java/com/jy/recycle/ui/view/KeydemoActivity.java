package com.jy.recycle.ui.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

import com.jy.recycle.R;
import com.jy.recycle.util.KeyboardUtil;

public class KeydemoActivity extends Activity {
	private Context ctx;
	private Activity act;
	private EditText ed;
	private KeyboardView keyboardView;
	private Keyboard k1;// ��ĸ����
	private Keyboard k2;// ���ּ���
	public boolean isnun = false;// �Ƿ����ݼ���
	public boolean isupper = false;// �Ƿ��д


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyboard_layout);
		ctx = this;
		act = this;
		ed = (EditText) this.findViewById(R.id.edit);


		k1 = new Keyboard(ctx, R.xml.qwerty);
		k2 = new Keyboard(ctx, R.xml.symbols);
		keyboardView = (KeyboardView) act.findViewById(R.id.keyboard_view);
		keyboardView.setKeyboard(k2);
		keyboardView.setEnabled(true);
		keyboardView.setPreviewEnabled(true);
		keyboardView.setOnKeyboardActionListener(listener);


		showKeyboard();



//		edit.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				new KeyboardUtil(act, ctx, edit).showKeyboard();
//				return false;
//			}
//		});
//
//		edit1.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				int inputback = edit1.getInputType();
//				new KeyboardUtil(act, ctx, edit1).showKeyboard();
//				edit1.setInputType(inputback);
//				return false;
//			}
//		});

	}
	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		@Override
		public void swipeUp() {
		}

		@Override
		public void swipeRight() {
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		@Override
		public void onRelease(int primaryCode) {
		}

		@Override
		public void onPress(int primaryCode) {
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			Editable editable = ed.getText();
			int start = ed.getSelectionStart();
			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// ���
				hideKeyboard();
			} else if (primaryCode == Keyboard.KEYCODE_DELETE) {// ��??
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);
					}
				}
			} else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// ��Сд�л�
				changeKey();
				keyboardView.setKeyboard(k1);

			} else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// ���ּ����л�
				if (isnun) {
					isnun = false;
					keyboardView.setKeyboard(k1);
				} else {
					isnun = true;
					keyboardView.setKeyboard(k2);
				}
			} else if (primaryCode == 57419) { // go left
				if (start > 0) {
					ed.setSelection(start - 1);
				}
			} else if (primaryCode == 57421) { // go right
				if (start < ed.length()) {
					ed.setSelection(start + 1);
				}
			} else {
				editable.insert(start, Character.toString((char) primaryCode));
			}
		}
	};

	/**
	 * ���̴�Сд��??
	 */
	private void changeKey() {
		List<Key> keylist = k1.getKeys();
		if (isupper) {//��д�л�Сд
			isupper = false;
			for(Key key:keylist){
				if (key.label!=null && isword(key.label.toString())) {
					key.label = key.label.toString().toLowerCase();
					key.codes[0] = key.codes[0]+32;
				}
			}
		} else {//Сд�л���д
			isupper = true;
			for(Key key:keylist){
				if (key.label!=null && isword(key.label.toString())) {
					key.label = key.label.toString().toUpperCase();
					key.codes[0] = key.codes[0]-32;
				}
			}
		}
	}

	public void showKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			keyboardView.setVisibility(View.VISIBLE);
		}
	}

	public void hideKeyboard() {
		finish();
	}

	private boolean isword(String str){
		String wordstr = "abcdefghijklmnopqrstuvwxyz";
		if (wordstr.indexOf(str.toLowerCase())>-1) {
			return true;
		}
		return false;
	}
}