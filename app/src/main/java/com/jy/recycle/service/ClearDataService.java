package com.jy.recycle.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import com.jy.recycle.util.Loger;

public class ClearDataService extends IntentService {

	public ClearDataService() {
		super("clear-data-service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... parms) {
				try {
					
				} catch (Throwable t) {
					Loger.e("ClearDataService", "��������ʱ�����쳣��", t);
				}
				return null;
			}
		}.execute();
	}

}