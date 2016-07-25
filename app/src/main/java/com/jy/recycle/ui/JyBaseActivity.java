package com.jy.recycle.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.recycle.R;
import com.umeng.analytics.MobclickAgent;

/**
 * �ڴ�ʵ�����������˳�
 * 
 */
public abstract class JyBaseActivity extends AppCompatActivity implements
		OnClickListener {
	// public static List<Activity> activityList = new ArrayList<Activity>();
	public static boolean active = false;
	public double lat, lng; // ���� γ��
	private static final int DECIMAL_DIGITS = 2;    
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.forward_enter_activity_anim,
				R.anim.forward_exit_activity_anim);
	}
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent,requestCode);
		overridePendingTransition(R.anim.forward_enter_activity_anim,
				R.anim.forward_exit_activity_anim);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.back_enter_activity_anim,
				R.anim.back_exit_activity_anim);
	}

	public void toast(String text) {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.toast_layout, null);
		   TextView title = (TextView) layout.findViewById(R.id.textView_toast);
		   title.setText(text);
		   Toast toast = new Toast(getApplicationContext());
		   toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
		   toast.setDuration(Toast.LENGTH_SHORT);
		   toast.setView(layout);
		   toast.show();
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ������ʾ�Ի���
	 * 
	 * @param msg
	 */
	public void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				}).show();
	}

	/**
	 * 
	 * @param lv
	 */
	public void setListViewHeight(ListView lv) {
		ListAdapter la = lv.getAdapter();
		if (null == la) {
			return;
		}
		// calculate height of all items.
		int h = 0;
		final int cnt = la.getCount();
		for (int i = 0; i < cnt; i++) {
			View item = la.getView(i, null, lv);
			item.measure(0, 0);
			h += item.getMeasuredHeight();
		}
		// reset ListView height
		ViewGroup.LayoutParams lp = lv.getLayoutParams();
		lp.height = h + (lv.getDividerHeight() * (cnt - 1));
		lv.setLayoutParams(lp);
	}

	/**
	 * ����eventViewId��ťʱ�����ػ���ʾtoggleViewId�����ͬʱ��eventViewId��ť��״̬�ı�
	 * 
	 * @param eventViewId
	 * @param toggleViewId
	 *            ��2��������Ҫչ�������view��id
	 * @param expendDrawableId
	 *            ��3�������Ǵ�ʱ��ť��ʾ��ͼƬ
	 * @param hideDrawableId
	 *            ��4����ť�������ǰ�ť��ʾ��ͼƬ
	 */
	protected void toggle(int eventViewId, int toggleViewId,
			int expendDrawableId, int hideDrawableId) {
		View eventView = findViewById(eventViewId);
		if (eventView != null) {
			eventView.setOnClickListener(new ToggleOnClickListener(
					toggleViewId, expendDrawableId, hideDrawableId));
		}
	}

	/**
	 * չ��/����ť�ļ����¼�
	 * 
	 */
	protected class ToggleOnClickListener implements OnClickListener {
		int toggleViewId;
		int expandDrawableId;
		int hideDrawableId;

		ToggleOnClickListener(int toggleViewId, int expandDrawableId,
				int hideDrawableId) {
			this.toggleViewId = toggleViewId;
			this.expandDrawableId = expandDrawableId;
			this.hideDrawableId = hideDrawableId;
		}

		@Override
		public void onClick(View v) {
			ImageView button = (ImageView) v;
			View view = findViewById(toggleViewId);

			if (view.getVisibility() == View.VISIBLE) {
				// ����ɼ�������ɲ��ɼ�
				view.setVisibility(View.GONE);
				button.setBackgroundResource(hideDrawableId);
			} else {
				// ������ɼ�������ɿɼ�
				view.setVisibility(View.VISIBLE);
				button.setBackgroundResource(expandDrawableId);
			}
		}

	};
	
	protected void dismissProgressDialog(ProgressDialog progressDialog) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	/**
	 *  ����С��λ������   
	 */
    public InputFilter lengthfilter = new InputFilter() {   
        public CharSequence filter(CharSequence source, int start, int end,   
                Spanned dest, int dstart, int dend) {   
            // ɾ���������ַ���ֱ�ӷ���   
            if ("".equals(source.toString())) {   
                return null;   
            }   
            String dValue = dest.toString();   
            String[] splitArray = dValue.split("\\.");   
            if (splitArray.length > 1) {   
                String dotValue = splitArray[1];   
                int diff = dotValue.length() + 1 - DECIMAL_DIGITS;   
                if (diff > 0) {   
                    return source.subSequence(start, end - diff);   
                }   
            }   
            return null;   
        }   
    };   
    public void toast(String text, int color) {

		View toastRoot = getLayoutInflater().inflate(R.layout.toast_layout,
				null);
		TextView message = (TextView) toastRoot.findViewById(R.id.textView_toast);

		// TextView textView=new TextView(this);
		// textView.setTextSize(22);
		message.setTextColor(color);
		message.setText(text);
		Toast toast = new Toast(this);
		toast.setView(toastRoot);
		toast.setDuration(1000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		// Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}