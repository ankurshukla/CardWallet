package com.ankur.cardwallet.activity;

import com.ankur.cardwallet.R;

import io.card.payment.CardIOActivity;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddCardActivity extends Activity {
	
	
	
	final String TAG = getClass().getName();
	
	private Button scanButton;
	private TextView resultTextView;
	private Button backButton;
	private EditText setAlias;
	private Button submitButton;
	private TextView aliasLabel;
	private Button back;
	
	private String cardIOToken;
	
	public static final int MY_SCAN_REQUEST_CODE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_card);
		
		cardIOToken = getResources().getString(R.string.MY_CARDIO_APP_TOKEN);
		
		scanButton = (Button)findViewById(R.id.scanButton);
		resultTextView = (TextView)findViewById(R.id.resultTextView);
		
		setAlias = (EditText)findViewById(R.id.alias);
		aliasLabel = (TextView)findViewById(R.id.aliasLabel);
		setAlias.setVisibility(View.INVISIBLE);
		aliasLabel.setVisibility(View.INVISIBLE);
		
		submitButton = (Button)findViewById(R.id.submitButton);
		submitButton.setVisibility(View.INVISIBLE);
		
		backButton = (Button)findViewById(R.id.backButton);
		
	}
	
	public void onScanPress(View view){
		
		Intent scanIntent = new Intent(this, CardIOActivity.class);
		
		// required for authentication with card.io
    	scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, cardIOToken );
    	
    	// customize these values to suit your needs.
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: true
    	scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
    	scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_ZIP, true); // default: false
    	scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // default: false
    	scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // default: false
    	
    	startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		if (CardIOActivity.canReadCardWithCamera(this)) {
			scanButton.setText(this.getResources().getString(R.string.add_card_scan));
		}
		else {
			scanButton.setText(this.getResources().getString(R.string.act_add_card_enter_info));
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == MY_SCAN_REQUEST_CODE && resultCode == CardIOActivity.RESULT_CARD_INFO){
			//TODO: display stuff on scan completion
			aliasLabel.setVisibility(View.VISIBLE);
			submitButton.setVisibility(View.VISIBLE);
			scanButton.setVisibility(View.INVISIBLE);
		}
	}

}
