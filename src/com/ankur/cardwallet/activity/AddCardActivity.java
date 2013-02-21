package com.ankur.cardwallet.activity;

import io.card.payment.CardIOActivity;
import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddCardActivity extends Activity {
	
	private static final String MY_CARDIO_APP_TOKEN = "c5c21f5e7c9848d284b99beda7baa381";
	
	public static final int MY_SCAN_REQUEST_CODE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);}
	
	public void onScanPress(View view){
		
		Intent scanIntent = new Intent(this, CardIOActivity.class);
		
		// required for authentication with card.io
    	scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, MY_CARDIO_APP_TOKEN);
    	
    	// customize these values to suit your needs.
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: true
    	scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
    	scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_ZIP, true); // default: false
    	scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // default: false
    	scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // default: false
    	
    	startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
		
	}

}
