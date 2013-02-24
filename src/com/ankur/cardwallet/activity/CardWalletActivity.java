package com.ankur.cardwallet.activity;

import com.ankur.cardwallet.R;
import com.ankur.cardwallet.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CardWalletActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_wallet);
    }
    
    public void addCard(View view){
    	
    	Intent intent = new Intent(this, AddCardActivity.class);
    	startActivity(intent);
    }
    
	public void viewCards(View view){
    	
    	Intent intent = new Intent(this,ViewCardsActivity.class);
    	startActivity(intent);
    }
}