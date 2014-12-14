package com.deep_blue.oxygen.synchronisation.handler;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.IntentKey;
import com.deep_blue.oxygen.util.PreferenceKey;

public class SynchThreadHandler extends Handler {

	public static final int CODE_START = 1;
	public static final int CODE_SUCCESS = 2;
	public static final int CODE_ERROR = 3;

	private static final int duration = Toast.LENGTH_SHORT;

	private Context pContext;

	public SynchThreadHandler(Context pContext) {
		super();

		this.pContext = pContext;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);

		String text = "";

		switch (msg.what) {
		case CODE_START:
			text = pContext.getResources().getString(R.string.synch_start);
			break;
		case CODE_SUCCESS:
			text = pContext.getResources().getString(R.string.synch_success);
			// Enregistrement de la date dans les SharedPreferences
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(pContext);
			SharedPreferences.Editor pEditor = preferences.edit();
			
			pEditor.putString(PreferenceKey.LAST_SYNCH.toString(),
					DateStringUtils.dateToString(new Date()));
			pEditor.apply();
			
			break;
		case CODE_ERROR:
			text = pContext.getResources().getString(R.string.synch_error) + msg.getData().getString(IntentKey.SYNCH_ERROR_TEXT.toString());
			break;
		default:
			break;
		}

		Toast toast = Toast.makeText(pContext, text, duration);
		toast.show();
		
	}
}
