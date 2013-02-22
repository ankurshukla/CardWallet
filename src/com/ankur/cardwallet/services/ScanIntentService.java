package com.ankur.cardwallet.services;

import com.ankur.cardwallet.datasource.Card;
import com.ankur.cardwallet.datasource.CardDAO;
import com.ankur.cardwallet.utilities.ActivitiesBridge;

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
	private static Card card;
	private  CardDAO cardDAO = new CardDAO(this);

	public ScanIntentService(String name) {
		super("ScanIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		card = (Card)ActivitiesBridge.getObject();
		if(card != null){
			cardDAO.open();
			cardDAO.createCard(card);
			cardDAO.close();
			
			RESULT = Activity.RESULT_OK;
		}
		
		Bundle extras = intent.getExtras();
		if(extras != null){
			Messenger messenger = (Messenger) extras.get("MESSENGER");
		    Message msg = Message.obtain();
		    msg.arg1 = RESULT;
		    try{
		    	messenger.send(msg);
		    }catch (RemoteException re) {
		    	Log.w(getClass().getName(), "Exception sending message");
			}
		}
	}

}
