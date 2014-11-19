package com.deep_blue.oxygen.thread.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class SynchThreadHandler extends Handler {

	public static final int CODE_START = 1;
	public static final int CODE_SUCCESS = 2;
	public static final int CODE_ERROR = 3;

	private static final int duration = Toast.LENGTH_SHORT;

	private Context pContext;
	
	public SynchThreadHandler(Context pContext){
		super();
		
		this.pContext = pContext;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);

		String text = "";

		switch (msg.what) {
		case CODE_START:
			text = "Début de la synchronisation";
			break;
		case CODE_SUCCESS:
			text = "Fin de la synchronisation";
			break;
		case CODE_ERROR:
			text = "Erreur de la synchronisation";
			break;
		default:
			break;
		}

		Toast toast = Toast.makeText(pContext, text, duration);
		toast.show();
	}
}
