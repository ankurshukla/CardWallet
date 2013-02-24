package com.ankur.cardwallet.services;

import com.ankur.cardwallet.datasource.Card;
import com.ankur.cardwallet.datasource.CardDAO;
import com.ankur.cardwallet.utilities.ActivitiesBridge;
import com.ankur.cardwallet.utilities.IntentHelper;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class ScanIntentService extends IntentService {
	
	private int RESULT = Activity.RESULT_CANCELED;
	
	public ScanIntentService(String name) {
		super("ScanIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
	}

}
