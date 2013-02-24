package com.ankur.cardwallet.activity;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ankur.cardwallet.R;
import com.ankur.cardwallet.datasource.Card;
import com.ankur.cardwallet.datasource.CardDAO;
import com.ankur.cardwallet.datasource.DBHelper;
import com.ankur.cardwallet.services.ScanIntentService;
import com.ankur.cardwallet.utilities.IntentHelper;

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
	private Card card;
	private CardDAO cardDAO = new CardDAO(this);

	public static final int MY_SCAN_REQUEST_CODE = 100;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_card);

		cardIOToken = getResources().getString(R.string.MY_CARDIO_APP_TOKEN);

		scanButton = (Button) findViewById(R.id.scanButton);
		resultTextView = (TextView) findViewById(R.id.resultTextView);

		setAlias = (EditText) findViewById(R.id.alias);
		aliasLabel = (TextView) findViewById(R.id.aliasLabel);
		setAlias.setVisibility(View.INVISIBLE);
		aliasLabel.setVisibility(View.INVISIBLE);

		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setVisibility(View.INVISIBLE);

		backButton = (Button) findViewById(R.id.backButton);

	}

	public void onScanPress(View view) {

		Intent scanIntent = new Intent(this, CardIOActivity.class);

		// required for authentication with card.io
		scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, cardIOToken);

		// customize these values to suit your needs.
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); 
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true);
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_ZIP, true);
		scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);
		scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);

		startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (CardIOActivity.canReadCardWithCamera(this)) {
			scanButton.setText(this.getResources().getString(
					R.string.act_add_card_scan));
		} else {
			scanButton.setText(this.getResources().getString(
					R.string.act_add_card_enter_info));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String resultStr;
		if (requestCode == MY_SCAN_REQUEST_CODE
				&& resultCode == CardIOActivity.RESULT_CARD_INFO) {

			if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
				CreditCard scanResult = data
						.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

				card = new Card();
				if (!cardDAO.valueExists(DBHelper.COLUMN_CARDNUM,
						scanResult.getFormattedCardNumber())) {
					card.setCardNumber(scanResult.getFormattedCardNumber());
					resultStr = " Card Number: " + card.getCardNumber()+"\n";

					if (scanResult.isExpiryValid()) {
						resultStr += " Expiration Date: "
								+ scanResult.expiryMonth + "/"
								+ scanResult.expiryYear + "\n";
						card.setExpiryDate(scanResult.expiryMonth + "/"
								+ scanResult.expiryYear);
					}

					if (scanResult.cvv != null) {
						// Never log or display a CVV
						resultStr += " CVV has " + scanResult.cvv.length()
								+ " digits.\n";
						card.setCvv(scanResult.cvv);
					}

					if (scanResult.zip != null) {
						resultStr += "Zip: " + scanResult.zip + "\n";
						card.setBillingZip(scanResult.zip);
					}

					aliasLabel.setVisibility(View.VISIBLE);
					setAlias.setVisibility(View.VISIBLE);
					submitButton.setVisibility(View.VISIBLE);
					scanButton.setVisibility(View.INVISIBLE);

				} else {
					resultStr = this.getResources().getString(
							R.string.act_add_card_card_exits_error);
				}

			} else {
				resultStr = this.getResources().getString(
						R.string.act_add_card_scan_cancelled);
			}
			resultTextView.setText(resultStr);
			card.setCardHolderName("ANKUR SHUKLA");
		}
	}
	
	public void onSubmitPress(View view){
		
		InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(setAlias.getWindowToken(), 0);
		aliasLabel.setVisibility(View.INVISIBLE);
		setAlias.setVisibility(View.INVISIBLE);
		submitButton.setVisibility(View.INVISIBLE);
		
		if(card != null){
			
			String alias = setAlias.getText().toString();
			card.setAlias(alias);
			cardDAO.open();
			cardDAO.createCard(card);
			cardDAO.close();
				
			resultTextView.setText(this.getResources().getString(R.string.act_add_card_success));
		}else {
			resultTextView.setText(this.getResources().getString(R.string.act_add_card_toast_error));
		}
	}
	
	public void onBackPress(View view){
		Intent intent = new Intent(this, CardWalletActivity.class);
		startActivity(intent);
	}

}
